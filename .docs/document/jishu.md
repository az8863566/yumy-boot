在明确使用 PostgreSQL 且不引入任何缓存的前提下，下面给出一套可以直接落地的表设计与立即下线实现方案。核心思路是：用一张表集中管理所有 Token，并将“状态管理”和“过期管理”全部交给数据库，每次请求直接查表，任何修改（下线、注销）实时生效。

一、Token 表设计 (DDL)
sql
CREATE TABLE user_token (
    token_value   VARCHAR(64)  NOT NULL,         -- 不透明 Token 值，使用足够长的安全随机字符串
    user_id       BIGINT       NOT NULL,         -- 关联的用户 ID
    authorities   JSONB        NOT NULL DEFAULT '[]', -- 登录瞬间的用户权限快照
    status        VARCHAR(20)  NOT NULL DEFAULT 'ACTIVE', -- ACTIVE / REVOKED / EXPIRED
    issued_at     TIMESTAMPTZ  NOT NULL DEFAULT now(),   -- 签发时间
    expires_at    TIMESTAMPTZ  NOT NULL,                 -- 过期时间
    last_used_at  TIMESTAMPTZ,                           -- 最近一次使用时间（可选，用于分析/踢除不活跃会话）
    metadata      JSONB,                                 -- 可选：设备、IP 等扩展信息
    
    CONSTRAINT pk_user_token PRIMARY KEY (token_value)
);

-- 为按用户查询活跃 Token（如下线某用户全部会话）创建索引
CREATE INDEX idx_user_token_user_status ON user_token (user_id, status, expires_at);
关键字段解释
字段	设计意图
token_value	64 字符长随机字符串（生成方式见下文），作为主键，查询直接命中，无需范围扫描。
authorities JSONB	登录时的权限快照。鉴权时直接从 Token 行取出，零外部依赖，实现权限透传。
status	ACTIVE 正常使用；REVOKED 被管理员手动下线；EXPIRED 可由定时任务标记。
expires_at	绝对过期时间，即使 status='ACTIVE' 但过期也不能使用。
last_used_at	用于管理界面展示“最后活动时间”，并可被策略踢除长时间不活跃的 Token。
二、Token 值的安全生成
使用 java.security.SecureRandom 生成 32 字节随机数，编码为 URL 安全的 Base64（不包含 /+=）或转换为 Hex。示例代码：

java
SecureRandom secureRandom = new SecureRandom();
byte[] raw = new byte[32];
secureRandom.nextBytes(raw);
String token = Base64.getUrlEncoder().withoutPadding().encodeToString(raw);
// 结果示例： "a7f3Ck9pQ2vX5zLm8N1bYx4eR6gH0wIo"
长度固定 64 字符内，可安全作为主键且不暴露任何信息。

三、实现立即下线（强制失效）
所有下线操作的核心本质是：让下一次携带该 Token 的请求无法通过数据库检查。有三种策略，对应不同场景：

1. 单个 Token 下线（用户主动注销或管理员踢除单个会话）
sql
-- 方案A：直接删除
DELETE FROM user_token WHERE token_value = ?;

-- 方案B：标记为 REVOKED（推荐，可保留下线记录用于审计）
UPDATE user_token SET status = 'REVOKED' WHERE token_value = ?;
在 Spring Security 过滤器中，同时检查 status = 'ACTIVE' 和 expires_at > now()，如果被标记为 REVOKED，自然拒绝。

2. 下线某个用户的所有 Token（管理员封禁账户、强制全部会话下线）
sql
UPDATE user_token SET status = 'REVOKED' 
WHERE user_id = ? AND status = 'ACTIVE';
该操作执行后，该用户所有现有 Token 立即作废，下次请求全部 401。

3. 基于策略的自动下线（例如修改密码后使旧 Token 失效）
可以在用户表中增加一个 token_version 字段，每次修改密码/权限时递增。同时在 user_token 表中增加 token_version 字段，签发给新 Token 时写入当时的版本号。鉴权时校验 user_token.token_version = app_user.token_version，不一致则自动失效。适合权限变更后无需手动改状态，只要让用户重登即可。

四、Spring Security 7 中的鉴权过滤器集成
鉴于没有任何缓存，每次请求都直接查库。对后台管理系统来说，PostgreSQL 处理此类主键点查完全无压力（通常 < 1ms）。过滤器实现骨架：

java
public class DbTokenAuthenticationFilter extends OncePerRequestFilter {

    private final TokenRepository tokenRepository; // 你的 DAO/Mapper

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) {
        String tokenValue = extractBearerToken(request);
        if (tokenValue == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 查询有效的 Token
        Optional<TokenEntity> optToken = tokenRepository.findActiveToken(tokenValue);
        if (optToken.isPresent()) {
            TokenEntity token = optToken.get();
            // 检查是否过期（可交给 SQL 过滤，或此处双重检查）
            if (token.getExpiresAt().isBefore(LocalDateTime.now())) {
                // 可选：异步标记为 EXPIRED
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            }
            // 构造 Authentication 对象
            List<GrantedAuthority> authorities = token.getAuthorities().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
            Authentication auth = new UsernamePasswordAuthenticationToken(
                token.getUserId(), null, authorities);
            SecurityContextHolder.getContext().setAuthentication(auth);
            // 可选：更新 last_used_at
            tokenRepository.updateLastUsed(tokenValue, LocalDateTime.now());
        }
        filterChain.doFilter(request, response);
    }
}
对应的 Repository 查询方法：

java
@Query("SELECT t FROM UserToken t WHERE t.tokenValue = :token AND t.status = 'ACTIVE' AND t.expiresAt > CURRENT_TIMESTAMP")
Optional<TokenEntity> findActiveToken(@Param("token") String token);
勾选： @Modifying 更新状态、@Transactional 保证一致性。

五、无需缓存的可靠性说明
为什么后台系统可以不使用任何缓存：单表主键查询，MySQL/PostgreSQL 完全可以支撑几百甚至几千 QPS。后台管理系统日常并发远低于此。

如何防止数据库成为瓶颈：使用连接池（HikariCP），其性能开销极低；并执行定期清理任务：

sql
-- 每天凌晨清理过期或已撤销的 Token
DELETE FROM user_token WHERE expires_at < now() - INTERVAL '1 day' OR status != 'ACTIVE';
容灾性：PostgreSQL 本身支持流复制、自动故障转移。即使应用重启，所有 Token 状态完全不丢失，这是 Redis 无法天然保证的。

六、权限透传的最终选择
在 authorities JSONB 字段中存储登录时刻的用户权限数组（如 ["user:read","user:write","admin:access"]），鉴权时直接从 Token 行恢复。如果需要权限变更后立即生效，则执行：

sql
UPDATE user_token SET status = 'REVOKED' WHERE user_id = ?;
强制该用户所有现有会话重新登录。这是后台系统最常见、最简单的实时权限控制方式。

综上，这张 user_token 表完全实现了：

不透明 Token 存储

PostgreSQL 保证数据不丢失

通过 status + expires_at 实现毫秒级的下线控制

无缓存设计依然稳定高效

这个设计可以直接用于你的后台管理系统。