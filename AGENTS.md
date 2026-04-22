## 技术架构

- Spring Boot 4.x（默认集成 Jackson 3.x）
- Java 21（启用虚拟线程优化并发处理）
- PostgreSQL（关系型数据库）
- Redis（缓存与分布式会话）
- MyBatis-Plus（持久层框架）
- SpringDoc OpenAPI（API 文档生成）

## 工具类索引

| 工具类 | 路径 | 功能说明 |
|--------|------|----------|
| `JacksonUtils` | `com.de.food.common.util.JacksonUtils` | JSON 序列化/反序列化 |
| `SpringUtils` | `com.de.food.common.util.SpringUtils` | Spring 上下文工具，获取 Bean、环境信息等 |
| `JwtUtil` | `com.de.food.framework.util.JwtUtil` | JWT Token 生成、解析、验证 |
| `SecurityUtils` | `com.de.food.framework.util.SecurityUtils` | 安全上下文，获取当前登录用户 ID/用户名/昵称 |

## 打包

- **打包环境**：Windows 系统
- **打包命令**：在根目录执行 `mvn clean package "-Dmaven.test.skip=true"`
- **打包产物**：可执行 JAR 文件位于 `yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar`
- **运行命令**：`java -jar yumy-bootstrap/target/yumy-bootstrap-1.0.0-SNAPSHOT.jar`
- **注意事项**：
  - Windows 环境下确保 JDK 21 已正确配置
  - Maven 版本建议 3.8+ 
  - 打包前确保所有模块编译通过，特别是 MapStruct 的注解处理器已生效

## 环境配置

### JDK 配置

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

## 项目结构与边界

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

**模块边界与禁止事项：**

| 模块 | 职责 | 禁止 |
|------|------|------|
| `yumy-common` | 与业务无关的基础设施代码 | 写入业务逻辑 |
| `yumy-framework` | 通用能力封装（安全、缓存、ORM、文档等） | 写入业务逻辑 |
| `yumy-business` | 数据层（Entity/Mapper/基础Service） | 编写 Controller、DTO、VO、Converter、权限校验、JWT生成 |
| `yumy-admin` | 后台接口（/admin/**），业务编排 | — |
| `yumy-toc` | ToC接口（/api/toc/**），业务编排 | — |
| `yumy-bootstrap` | 模块组装、配置管理、应用启动 | 编写 Controller/Service |

- **依赖方向**：bootstrap → admin/toc → business → framework → common，禁止循环依赖

## 开发规范

### 1. 数据模型规范

#### 1.1 实体类与审计字段

- 所有业务表实体类必须继承 `BaseEntity`（`com.de.food.common.entity.BaseEntity`），添加 `@EqualsAndHashCode(callSuper = true)`
- `BaseEntity` 已包含审计字段：`deleted`（0正常/1删除）、`create_time`、`update_time`、`create_by`、`update_by`，通过 MyBatis-Plus 自动注入，禁止手动维护
- 建表脚本（DDL）必须与实体类保持一致，包含相同的审计字段
- **例外**（可不继承 BaseEntity）：关联表（如 `SysUserRole`、`SysRoleMenu`）、日志表（如 `SysOperLog`）

#### 1.2 DTO/VO 分层

- **ENTITY**：数据库实体类，仅用于持久层映射
- **DTO**：接收前端请求参数，Controller 层入参
- **VO**：封装返回前端的数据，Controller 层出参
- **禁止将 ENTITY 直接作为 API 接口的入参或出参**

#### 1.3 对象转换

- 统一使用 MapStruct 框架进行 DTO ↔ ENTITY、ENTITY ↔ VO 转换
- 禁止使用 `BeanUtils.copyProperties` 或手动 getter/setter
- MapStruct Mapper 接口定义在对应业务模块的 `converter` 包下

#### 1.4 数据库命名

- 表命名：小写+下划线，模块前缀（`sys_`/`toc_`），单数形式
- 字段命名：小写+下划线，主键命名 `表名_id`
- 索引命名：唯一索引 `uk_表名_字段名`，普通索引 `idx_表名_字段名`
- 所有表和字段必须添加 COMMENT 注释

#### 1.5 ID 生成

- 全局唯一 ID 统一使用雪花算法生成

### 2. 异常处理规范

- 业务异常统一使用 `BizException`（`com.de.food.common.exception.BizException`）
- 错误码统一定义在 `ErrorCode` 枚举（`com.de.food.common.result.ErrorCode`），禁止硬编码
- **禁止在 Controller 中 try-catch 吞掉异常**
- **禁止返回 `null` 给前端**
- 异常消息需对用户友好，敏感信息（如 SQL 错误）不得直接返回前端
- 由 `GlobalExceptionHandler` 统一拦截处理

### 3. 日志规范

- 使用 `@Slf4j` 注解，禁止使用 `System.out.println()` 或 `e.printStackTrace()`
- 日志级别：`debug`（调试）、`info`（关键业务节点）、`warn`（业务异常）、`error`（系统异常，必须含异常堆栈）
- 使用占位符而非字符串拼接：`log.info("用户登录成功, userId={}", userId)`
- **禁止打印敏感信息**：密码、Token、密钥等；手机号、身份证号需脱敏

### 4. API 接口规范

#### 4.1 版本与文档

- 接口路径必须包含版本号：`/api/v1/资源名` 或 `/admin/v1/资源名`
- 向后兼容不升级版本号，不兼容修改升级版本号（`v1` → `v2`）
- Controller 类级别定义模块前缀，方法级别定义具体资源
- API 文档使用 `@Operation`/`@Schema` 注解；废弃接口使用 `@Deprecated`

#### 4.2 权限控制（admin 模块）

- 所有 Controller 接口**必须添加 `@PreAuthorize` 注解**
- 权限标识符命名：`模块:资源:操作`（如 `system:user:list`），常用操作：`list`、`detail`、`add`、`edit`、`delete`、`export`、`import`
- Controller 类级别**不要添加**全局权限注解，方法级别单独声明
- 登录/登出等公开接口无需权限注解（已在 SecurityConfig 中放行）

### 5. 代码编写规范

#### 5.1 导入与工具类

- **优先使用 `import` 导入类，避免使用全限定名**，仅在类名冲突时例外
- 工具类必须放置在对应模块的 `util` 包下，禁止散落在 controller/service 等包中

#### 5.2 JSON 序列化

- 全局统一使用 Jackson 3.x，禁止 Fastjson、Gson 等
- **业务代码统一使用 `JacksonUtils` 工具类**（`com.de.food.common.util.JacksonUtils`），禁止 `new ObjectMapper()` 或 `JsonMapper.builder()`
- **框架层集成**（如 SecurityConfig、RedisConfig）注入 Spring 自动配置的 `ObjectMapper` Bean，不要自行创建实例
- 如需特殊序列化策略，应在 `JacksonUtils` 中扩展方法，而非另建 ObjectMapper
- 日期时间格式：`@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")`
- **导入规则**：databind/core 类（`ObjectMapper`、`JsonMapper`、`TypeReference`、`JacksonException` 等）使用 `tools.jackson.*`；注解类（`@JsonFormat`、`@JsonIgnore`、`@JsonInclude`、`@JsonProperty` 等）仍使用 `com.fasterxml.jackson.annotation.*`
- JSR310 时间模块已内置到 databind，禁止手动注册 `JavaTimeModule`

#### 5.3 数据安全

- 敏感数据入库前必须加密或脱敏，禁止明文存储

#### 5.4 文件编码

- 项目所有上下文相关文件（如 `.md`、`.java`、`.yml`、`.properties`、`.xml` 等）必须使用 **UTF-8** 编码格式
- 禁止使用 GBK、GB2312 等其他编码，避免中文乱码问题
- 编辑器/IDE 需统一配置默认编码为 UTF-8
- Git 提交时需确保文件编码一致性
