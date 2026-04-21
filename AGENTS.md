## 技术架构

- Spring Boot 4.x
- Java 21（启用虚拟线程优化并发处理）
- PostgreSQL（关系型数据库）
- Redis（缓存与分布式会话）
- MyBatis-Plus（持久层框架）
- SpringDoc OpenAPI（API 文档生成）

## 工具类索引

### yumy-common 模块
| 工具类 | 路径 | 功能说明 |
|--------|------|----------|
| `JacksonUtils` | `com.de.food.common.util.JacksonUtils` | JSON 序列化/反序列化工具，优先使用 |
| `SpringUtils` | `com.de.food.common.util.SpringUtils` | Spring 上下文工具，获取 Bean、环境信息等 |

### yumy-framework 模块
| 工具类 | 路径 | 功能说明 |
|--------|------|----------|
| `JwtUtil` | `com.de.food.framework.util.JwtUtil` | JWT Token 生成、解析、验证工具 |
| `SecurityUtils` | `com.de.food.framework.util.SecurityUtils` | 安全上下文工具，获取当前登录用户 ID/用户名/昵称，兼容 Session 和 JWT 两套认证链 |

**使用规范：**
- JSON 处理必须优先使用 `JacksonUtils`
- 获取 Spring Bean 使用 `SpringUtils.getBean()`
- JWT 相关操作使用 `JwtUtil`
- 获取当前登录用户信息使用 `SecurityUtils`（后台和 API 端通用）

## 打包

- **打包环境**：Windows 系统
- **打包命令**：在根目录执行 `mvn clean package -Dmaven.test.skip=true`
- **打包产物**：可执行 JAR 文件位于 `yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar`
- **运行命令**：`java -jar yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar`
- **注意事项**：
  - Windows 环境下确保 JDK 21 已正确配置
  - Maven 版本建议 3.8+ 
  - 打包前确保所有模块编译通过，特别是 MapStruct 的注解处理器已生效

## 代码结构

yumy-boot (根项目，负责依赖版本管理)
│
├── yumy-common        # 公共模块 (无具体业务逻辑)
│   └── 工具类、全局异常定义、统一返回结果(Result)、基础DTO等
│
├── yumy-framework     # 核心框架模块 (依赖 common)
│   └── Spring Security配置、Redis缓存配置、MyBatis-Plus配置、全局异常拦截器、JWT工具等
│
├── yumy-admin         # 后台管理业务模块 (依赖 framework)
│   └── Controller/Service/Mapper，处理 /admin/** 路由，包含RBAC权限管理、系统配置等
│
├── yumy-toc           # ToC端数据管理模块 (依赖 framework)
│   └── Controller/Service/Mapper，处理 /api/toc/** 路由，包含C端用户注册登录、业务数据等
│
└── yumy-bootstrap     # 启动与组装模块 (依赖 admin, toc)
    └── 主启动类(Application.java)、application.yml 配置文件

## 项目边界

- `yumy-common`：仅包含与具体业务无关的基础设施代码
- `yumy-framework`：封装通用能力（安全、缓存、ORM 配置、文档等）
- `yumy-admin`：面向运营/管理的后台接口，处理 `/admin/**` 路由
- `yumy-toc`：面向终端用户的开放接口，处理 `/api/toc/**` 路由
- `yumy-bootstrap`：仅负责模块组装、配置管理与应用启动，不写业务逻辑

## 一定不能做

- 不要将业务逻辑写入 `common` 或 `framework` 模块
- 不要在 `bootstrap` 模块中编写 Controller/Service
- 不要绕过 MyBatis-Plus 的自动注入机制手动维护 `create_time/update_time` 等字段
- 不要在不同模块之间产生循环依赖（依赖方向必须：bootstrap → admin/toc → framework → common）

## 一定要做

- 全局唯一 ID 统一使用雪花算法生成
- 所有业务表必须包含以下审计字段，并通过 MyBatis-Plus 自动注入维护：
  - `deleted`：逻辑删除标志（0 正常，1 删除）
  - `create_time`：创建时间
  - `update_time`：更新时间
  - `create_by`：创建人标识
  - `update_by`：更新人标识
- 实体类继承公共基类，复用上述字段与公共方法
- API 文档使用 `@Operation`/`@Schema` 注解完善描述，便于 SpringDoc 生成
- 敏感数据入库前必须加密或脱敏，禁止明文存储
- 前端和后端传递参数必须严格分离，使用 DTO、VO、ENTITY 进行独立分层：
  - **ENTITY**：数据库实体类，仅用于持久层映射，不直接暴露给前端
  - **DTO**（Data Transfer Object）：接收前端请求参数，用于 Controller 层入参
  - **VO**（View Object）：封装返回给前端的数据结构，用于 Controller 层出参
  - 禁止将 ENTITY 直接作为 API 接口的入参或出参
- 对象转换统一使用 MapStruct 框架：
  - 所有 DTO ↔ ENTITY、ENTITY ↔ VO 的转换必须通过 MapStruct 接口实现
  - 禁止使用 BeanUtils.copyProperties 或手动 getter/setter 进行对象转换
  - MapStruct Mapper 接口应定义在对应业务模块的 `converter` 包下
- 全局 JSON 序列化与反序列化必须使用 Jackson 框架：
  - 禁止使用 Fastjson、Fastjson2、Gson 等其他 JSON 库
  - Redis 序列化、HTTP 请求/响应、Spring Cache 等场景统一使用 Jackson
  - 日期时间格式统一使用 `@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")` 注解
- 工具类必须放置在对应模块的 `util` 包下：
  - `yumy-common` → `com.de.food.common.util`（通用工具类，如 JacksonUtils、SpringUtils）
  - `yumy-framework` → `com.de.food.framework.util`（框架级工具类，如 JwtUtil）
  - `yumy-admin` → `com.de.food.admin.util`（后台管理业务工具类）
  - `yumy-toc` → `com.de.food.toc.util`（ToC端业务工具类）
  - 禁止在非 util 包中定义工具类，禁止将工具类散落在 controller/service 等包中
- 接口权限控制规范（admin 模块）：
  - 所有 admin 模块的 Controller 接口**必须添加权限注解**，使用 Spring Security 提供的 `@PreAuthorize` 注解
  - 注解使用方式：`@PreAuthorize("hasAuthority('system:user:list')")` 或 `@PreAuthorize("hasRole('admin')")`
  - 权限标识符命名规范：`模块:资源:操作`，例如 `system:user:add`、`system:user:edit`、`system:user:delete`、`system:user:list`
  - 常用操作标识：`list`（查询列表）、`detail`（查询详情）、`add`（新增）、`edit`（修改）、`delete`（删除）、`export`（导出）、`import`（导入）
  - 角色标识使用 `hasRole('角色编码')` 格式，例如 `@PreAuthorize("hasRole('admin')")`
  - 支持组合权限：`@PreAuthorize("hasAuthority('system:user:add') or hasRole('admin')")`
  - Controller 类级别**不要添加**全局权限注解，应在每个方法上单独声明，便于细粒度控制
  - 登录/登出等公开接口无需添加权限注解（已在 SecurityConfig 中放行）
  - 使用 `@PreAuthorize` 前需确保已启用全局方法级安全注解（在 SecurityConfig 中添加 `@EnableMethodSecurity`）
