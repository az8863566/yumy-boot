# AI Agent 开发规范指令 (AGENTS.md) - 后端篇

你好，AI 开发助手。当你在本项目中编写、重构或审查代码时，请严格遵循以下七个章节的开发规范。本项目是一个基于现代 Java 生态（JDK 21 + Spring Boot 4）构建的企业级后端系统。

---

## 一、技术架构 (Technical Architecture)

本项目采用最新一代 Java 现代化后端技术栈，请严格基于以下环境和框架特性生成代码：

| 类别 | 技术 | 说明 |
|------|------|------|
| **核心框架** | Spring Boot 4.x | 默认集成 Jackson 3.x，开启虚拟线程优化高并发 I/O |
| **开发语言** | Java 21 | 积极使用 `Pattern Matching`、`Switch Expressions`、`String Templates` |
| **持久层框架** | MyBatis-Plus | 简化单表 CRUD，复杂查询手写 XML |
| **数据库** | PostgreSQL | 使用 `JSONB` 存储动态字段，`ARRAY` 存储简单列表 |
| **缓存与中间件** | Redis | 结合 `@Cacheable` 注解进行数据缓存与分布式锁 |
| **对象转换** | MapStruct | 编译期生成高性能 DTO/Entity 转换代码 |
| **接口文档** | SpringDoc OpenAPI 3 | 替代老旧 Swagger2 |
| **代码简化** | Lombok | 用于 Entity，减少样板代码 |

---

## 二、工具类索引 (Utility Classes)

| 工具类 | 路径 | 功能说明 |
|--------|------|----------|
| `JacksonUtils` | `com.de.food.common.util.JacksonUtils` | JSON 序列化/反序列化（业务代码统一使用） |
| `SpringUtils` | `com.de.food.common.util.SpringUtils` | Spring 上下文工具，获取 Bean、环境信息等 |
| `JwtUtil` | `com.de.food.framework.util.JwtUtil` | JWT Token 生成、解析、验证 |
| `SecurityUtils` | `com.de.food.framework.util.SecurityUtils` | 安全上下文，获取当前登录用户 ID/用户名/昵称 |

> **使用原则**：工具类必须放置在对应模块的 `util` 包下，禁止散落在 controller/service 等包中

---

## 三、项目结构与模块边界 (Project Structure)

### 模块划分

```
yumy-boot (根项目，负责依赖版本管理)
│
├── yumy-common        # 公共模块 (无具体业务逻辑)
│   └── 工具类、全局异常定义、统一返回结果(Result)、基础DTO等
│
├── yumy-framework     # 核心框架模块 (依赖 common)
│   └── Spring Security配置、Redis缓存配置、MyBatis-Plus配置、全局异常拦截器、JWT工具等
│
├── yumy-business      # 核心业务与数据层 (依赖 framework)
│   └── 存放所有的 Entity、Mapper、以及基础的 Service (MyBatis-Plus 的 IService/ServiceImpl)
│
├── yumy-admin         # 后台管理业务模块 (依赖 yumy-business, framework)
│   └── Controller/业务编排 Service/DTO/VO/Converter，处理 /admin/** 路由
│
├── yumy-toc           # ToC端数据管理模块 (依赖 yumy-business, framework)
│   └── Controller/业务编排 Service/DTO/VO/Converter，处理 /api/toc/** 路由
│
└── yumy-bootstrap     # 启动与组装模块 (依赖 admin, toc)
    └── 主启动类(Application.java)、application.yml 配置文件
```

### 模块边界与禁止事项

| 模块 | 职责 | 禁止 |
|------|------|------|
| `yumy-common` | 与业务无关的基础设施代码 | 写入业务逻辑 |
| `yumy-framework` | 通用能力封装（安全、缓存、ORM、文档等） | 写入业务逻辑 |
| `yumy-business` | 数据层（Entity/Mapper/基础Service） | 编写 Controller、DTO、VO、Converter、权限校验、JWT生成 |
| `yumy-admin` | 后台接口（/admin/**），业务编排 | — |
| `yumy-toc` | ToC接口（/api/toc/**），业务编排 | — |
| `yumy-bootstrap` | 模块组装、配置管理、应用启动 | 编写 Controller/Service |

> **依赖方向**：bootstrap → admin/toc → business → framework → common，**禁止循环依赖**

---

## 三、代码规范 (Coding Standards)

### 3.1 数据模型规范

#### 3.1.1 实体类与审计字段

- 所有业务表实体类必须继承 `BaseEntity`（`com.de.food.common.entity.BaseEntity`），添加 `@EqualsAndHashCode(callSuper = true)`
- `BaseEntity` 已包含审计字段：`deleted`（0正常/1删除）、`create_time`、`update_time`、`create_by`、`update_by`，通过 MyBatis-Plus 自动注入，**禁止手动维护**
- 建表脚本（DDL）必须与实体类保持一致，包含相同的审计字段
- **例外**（可不继承 BaseEntity）：关联表（如 `SysUserRole`、`SysRoleMenu`）、日志表（如 `SysOperLog`）

#### 3.1.2 DTO/VO 分层

| 类型 | 用途 | 规范 |
|------|------|------|
| **ENTITY** | 数据库实体类，仅用于持久层映射 | 使用 Lombok `@Data`, `@TableName` |
| **DTO** | 接收前端请求参数，Controller 层入参 | 优先使用 JDK 21 的 `record` 关键字 |
| **VO** | 封装返回前端的数据，Controller 层出参 | 优先使用 JDK 21 的 `record` 关键字 |

> **禁止将 ENTITY 直接作为 API 接口的入参或出参**

#### 3.1.3 对象转换

- 统一使用 MapStruct 框架进行 DTO ↔ ENTITY、ENTITY ↔ VO 转换
- **禁止使用 `BeanUtils.copyProperties` 或手动 getter/setter**
- MapStruct Mapper 接口定义在对应业务模块的 `converter` 包下
- 定义 Mapper 接口时必须加上 `@Mapper(componentModel = "spring")`

#### 3.1.4 数据库命名

- 表命名：小写+下划线，模块前缀（`sys_`/`toc_`），单数形式
- 字段命名：小写+下划线，主键命名 `表名_id`
- 索引命名：唯一索引 `uk_表名_字段名`，普通索引 `idx_表名_字段名`
- 所有表和字段必须添加 COMMENT 注释

#### 3.1.5 ID 生成

- 全局唯一 ID 统一使用雪花算法生成

### 3.2 MyBatis-Plus 使用规范

- 单表增删改查：优先继承 `IService` 和 `BaseMapper`，使用 `LambdaQueryWrapper` 防止字段名硬编码错漏
- 联表查询：尽量避免在代码里写长篇的 `for` 循环查库，复杂的连表统计应当编写 SQL 放入 `Mapper.xml` 中
- **分页插件需单独引入 `mybatis-plus-jsqlparser` 依赖**

### 3.3 时间与日期

- **严禁使用老的 `java.util.Date`**，一律使用 Java 8 时间 API
- 推荐使用：`LocalDateTime`（日期时间）、`LocalDate`（日期）、`LocalTime`（时间）

### 3.4 导入规范

- **优先使用 `import` 导入类，避免使用全限定名**，仅在类名冲突时例外

### 3.5 JSON 序列化规范

- 全局统一使用 Jackson 3.x，**禁止 Fastjson、Gson 等**
- **业务代码统一使用 `JacksonUtils` 工具类**（`com.de.food.common.util.JacksonUtils`），禁止 `new ObjectMapper()` 或 `JsonMapper.builder()`
- **框架层集成**（如 SecurityConfig、RedisConfig）注入 Spring 自动配置的 `ObjectMapper` Bean，不要自行创建实例
- 如需特殊序列化策略，应在 `JacksonUtils` 中扩展方法，而非另建 ObjectMapper
- JSR310 时间模块已内置到 databind，**禁止手动注册 `JavaTimeModule`**
- 日期时间格式注解：`@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")`
- **导入规则**：
  - databind/core 类（`ObjectMapper`、`JsonMapper`、`TypeReference`、`JacksonException` 等）→ `tools.jackson.*`
  - 注解类（`@JsonFormat`、`@JsonIgnore`、`@JsonInclude`、`@JsonProperty` 等）→ `com.fasterxml.jackson.annotation.*`

### 3.6 文件编码

- 项目所有上下文相关文件（`.md`、`.java`、`.yml`、`.properties`、`.xml` 等）**必须使用 UTF-8 编码格式**
- **禁止使用 GBK、GB2312 等其他编码**，避免中文乱码问题

---

## 四、需要做 (Dos)

### 4.1 全局统一响应与异常处理

- Controller 层的方法必须返回封装好的 `Result<T>` 对象
- 业务抛出异常必须使用自定义的 `BizException`（`com.de.food.common.exception.BizException`）
- 错误码统一定义在 `ErrorCode` 枚举（`com.de.food.common.result.ErrorCode`），**禁止硬编码**
- 由 `GlobalExceptionHandler` 统一拦截处理并转换为 JSON 返回前端
- **禁止在 Controller 中 try-catch 吞掉异常**
- **禁止返回 `null` 给前端**

### 4.2 接口文档全覆盖

- 所有 Controller 类必须加 `@Tag(name="模块名称")`
- 所有请求方法必须加 `@Operation(summary="接口描述")`
- 实体类字段必须加 `@Schema(description="字段说明")`
- 废弃接口使用 `@Deprecated` 注解

### 4.3 字段自动填充 (Auto Fill)

- 利用 MyBatis-Plus 的 `MetaObjectHandler`，在插入和更新时自动填充审计字段
- 自动填充字段：`create_time`, `update_time`, `create_by`, `update_by`
- **不要在业务逻辑中手动 set**

### 4.4 逻辑删除

- 所有涉及资源删除的表，必须采用逻辑删除机制
- 在实体类字段加 `@TableLogic` 注解
- 避免物理删除导致数据无法追溯

### 4.5 RESTful URI 设计

- 接口路径必须包含版本号：`/api/v1/资源名` 或 `/admin/v1/资源名`
- 使用名词复数表示资源（如 `/api/users`）
- HTTP 方法规范：GET（查询）、POST（新增）、PUT（全量修改）、DELETE（删除）
- 向后兼容不升级版本号，不兼容修改升级版本号（`v1` → `v2`）

### 4.6 权限控制（admin 模块）

---

## 五、不能做 (Don'ts)

### 5.1 禁止 Controller 裸奔

严禁将数据库实体（Entity/PO）直接返回给 Controller 作为 HTTP 响应！必须经过 MapStruct 转换为 VO，防止数据库表结构敏感信息（如密码、盐值）泄露。

### 5.2 禁止在 Controller 层写业务逻辑

Controller 只能做三件事：
1. 接收参数校验
2. 调用 Service
3. 包装 Result 返回

**所有的核心业务运算、判空、事务处理必须在 Service 层完成**

### 5.3 禁止在循环中操作数据库或 Redis

严禁在 `for` 或 `while` 循环里写 `mapper.insert()` 或 `mapper.selectById()`。必须将数据整合到集合中，使用 MyBatis-Plus 的 `saveBatch` 批量插入或 `in` 批量查询。

### 5.4 禁止使用隐式或宽泛的异常

- 不要直接 `throw new RuntimeException("error")`
- 更不能吃掉异常（`catch (Exception e) {}` 为空）
- 必须抛出业务异常，并让外层统一处理

### 5.5 禁止乱用事务

- `@Transactional` 注解只能打在 Service 层方法上
- **禁止滥用在只有查询（SELECT）操作的方法上**
- 长事务应尽量拆分，以避免锁表和连接池耗尽

### 5.6 禁止返回 null 给前端

- **禁止在 Controller 层返回 `null`**
- 空数据应返回空集合、空对象或明确的错误码
- 由 `GlobalExceptionHandler` 统一处理异常情况

---

## 六、日志规范 (Logging Standards)

### 6.1 日志使用规范

- 使用 `@Slf4j` 注解，**禁止使用 `System.out.println()` 或 `e.printStackTrace()`**
- 使用占位符而非字符串拼接：`log.info("用户登录成功, userId={}", userId)`

### 6.2 日志级别规范

| 级别 | 使用场景 | 示例 |
|------|----------|------|
| `debug` | 调试信息，开发环境详细排查 | `log.debug("SQL执行参数: {}", params)` |
| `info` | 关键业务节点，如用户登录、订单创建 | `log.info("用户登录成功, userId={}", userId)` |
| `warn` | 业务异常（如数据不存在、权限不足），不影响核心流程 | `log.warn("用户尝试访问无权限资源, userId={}", userId)` |
| `error` | 系统异常，**必须包含异常堆栈** | `log.error("数据库连接失败", e)` |

### 6.3 日志内容规范

- **禁止打印敏感信息**：密码、Token、密钥等
- **脱敏要求**：手机号、身份证号等需脱敏后打印
- 异常日志必须包含异常堆栈：`log.error("操作失败", e)`

---

## 七、安全规范 (Security Standards)

### 7.1 权限控制（admin 模块）

- 所有 Controller 接口**必须添加 `@PreAuthorize` 注解**
- 权限标识符命名：`模块:资源:操作`（如 `system:user:list`）
- 常用操作：`list`、`detail`、`add`、`edit`、`delete`、`export`、`import`
- Controller 类级别**不要添加**全局权限注解，方法级别单独声明
- 登录/登出等公开接口无需权限注解（已在 SecurityConfig 中放行）

### 7.2 数据安全

- 敏感数据入库前必须加密或脱敏，**禁止明文存储**
- 密码字段需加密存储，使用单向哈希算法（如 BCrypt）
- 配置文件中的密码使用环境变量或密文配置

### 7.3 异常消息安全

- 异常消息需对用户友好，技术细节不得直接返回前端
- 敏感信息（如 SQL 错误、堆栈信息、数据库结构）**禁止暴露给前端**
- 由 `GlobalExceptionHandler` 统一拦截处理并脱敏

---

## 八、环境与部署规范 (Environment & Deployment)

### 8.1 环境配置

#### JDK 配置

**本项目统一使用 JDK 21**

- **JDK 路径**：根据实际安装位置配置（常见路径如 `C:\Program Files\Java\jdk-21`、`D:\dev\jdk21` 等）
- **打包前确认**：`JAVA_HOME` 必须指向 JDK 21
- **临时切换命令**（PowerShell）：
  ```powershell
  $env:JAVA_HOME = "<你的JDK21实际安装路径>"
  $env:PATH = "$env:JAVA_HOME\bin;$env:PATH"
  java -version  # 验证版本
  ```

> **注意**：系统默认 JDK 可能不是 21，打包/运行本项目前务必切换至 JDK 21

#### YAML 配置注意

- 密码字段需用单引号包裹特殊字符：`password: 'your@password#123'`
- 避免特殊字符（如 `@`、`:`）导致 YAML 解析错误

### 8.2 打包规范

| 项目 | 说明 |
|------|------|
| **打包环境** | Windows 系统 |
| **打包命令** | 在根目录执行 `mvn clean package "-Dmaven.test.skip=true"` |
| **打包产物** | 可执行 JAR 文件位于 `yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar` |
| **运行命令** | `java -jar yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar` |

**注意事项**：
- Windows 环境下确保 JDK 21 已正确配置
- Maven 版本建议 3.8+
- 打包前确保所有模块编译通过，特别是 MapStruct 的注解处理器已生效

---

## 附录：常见问题与规范速查

### A. 常见问题排障

| 问题 | 解决方案 |
|------|----------|
| YAML 密码字段解析错误 | 使用单引号包裹特殊字符：`password: 'your@password#123'` |
| MyBatis-Plus 分页不生效 | 需单独引入 `mybatis-plus-jsqlparser` 依赖 |
| MapStruct 编译失败 | 检查注解处理器版本，MapStruct 1.6.3 移除了 `NullValuePropertyCheckStrategy` 参数 |
| Jackson 序列化问题 | 统一使用 `JacksonUtils`，JSR310 已内置禁止手动注册 `JavaTimeModule` |
| Spring Security Bean 冲突 | 检查是否有同名 Bean，使用 `@Qualifier` 或 `@Primary` 解决 |

### B. 规范速查表

| 类别 | 核心规则 | 参考章节 |
|------|----------|----------|
| 数据模型 | Entity 继承 BaseEntity，DTO/VO 使用 record | 3.1 |
| 对象转换 | 必须使用 MapStruct，禁止 BeanUtils | 3.1.3 |
| 异常处理 | 使用 BizException + ErrorCode，禁止返回 null | 4.1 |
| 日志记录 | 使用 @Slf4j，禁止 System.out | 第六章 |
| 权限控制 | 必须添加 @PreAuthorize，格式：模块:资源:操作 | 7.1 |
| JSON 处理 | 业务代码使用 JacksonUtils，禁止 new ObjectMapper | 3.5 |
| 事务管理 | @Transactional 仅用于 Service 层，禁止用于查询 | 5.5 |

---

> **最后更新**：2026-04-23  
> **适用版本**：JDK 21 + Spring Boot 4.x + Jackson 3.x
