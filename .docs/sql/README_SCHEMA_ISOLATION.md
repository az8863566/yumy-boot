# Schema 隔离部署指南

## 概述

本项目采用 PostgreSQL Schema 隔离方案，将后台管理模块（admin）和 ToC 端模块（toc）的数据完全分离，确保两个模块的表互不干扰。

## Schema 结构

```
yumy-boot (数据库)
├── admin (schema) - 后台管理模块
│   ├── sys_user
│   ├── sys_role
│   ├── sys_menu
│   ├── sys_user_role
│   ├── sys_role_menu
│   ├── sys_oper_log
│   ├── sys_dict_type
│   ├── sys_dict_data
│   └── sys_config
│
├── toc (schema) - ToC端模块
│   ├── toc_user
│   ├── toc_parent_category
│   ├── toc_sub_category
│   ├── toc_recipe
│   ├── toc_recipe_ingredient
│   ├── toc_recipe_step
│   ├── toc_comment
│   ├── toc_user_like
│   └── toc_user_favorite
│
└── public (schema) - 默认schema（保留）
```

## 部署步骤

### 1. 初始化 Schema

执行 SQL 脚本创建 admin 和 toc 两个 schema：

```bash
psql -U <username> -d yumy-boot -f .docs/sql/schema_init.sql
```

或者手动执行：

```sql
CREATE SCHEMA IF NOT EXISTS admin;
COMMENT ON SCHEMA admin IS '后台管理模块数据';

CREATE SCHEMA IF NOT EXISTS toc;
COMMENT ON SCHEMA toc IS 'ToC端模块数据';

ALTER DATABASE "yumy-boot" SET search_path TO admin, toc, public;
```

### 2. 执行 DDL 脚本

按照顺序执行建表脚本：

```bash
# 创建 admin schema 的表
psql -U <username> -d yumy-boot -f .docs/sql/ddl.sql

# 创建 toc schema 的表
psql -U <username> -d yumy-boot -f .docs/sql/toc_ddl.sql
```

### 3. 配置数据源连接

确保数据库连接 URL 中包含 `search_path` 参数：

```yaml
spring:
  datasource:
    url: jdbc:postgresql://host:port/yumy-boot?search_path=admin,toc,public
```

**各环境配置说明：**

- **local 环境**：已配置在 `application-local.yml`
- **dev 环境**：已配置在 `application-dev.yml`
- **prod 环境**：需要在环境变量 `DB_URL` 中包含 `search_path` 参数

**生产环境示例：**

```bash
export DB_URL="jdbc:postgresql://prod-host:5432/yumy-boot?search_path=admin,toc,public"
```

## 实体类配置

所有实体类已通过 `@TableName` 注解的 `schema` 属性明确指定所属 schema：

**Admin 模块实体类示例：**

```java
@TableName(value = "sys_user", schema = "admin")
public class SysUser extends BaseEntity {
    // ...
}
```

**ToC 模块实体类示例：**

```java
@TableName(value = "toc_recipe", schema = "toc")
public class TocRecipe extends BaseEntity {
    // ...
}
```

## 优势

1. **数据隔离**：admin 和 toc 模块的表完全独立，避免命名冲突
2. **权限控制**：可以针对不同 schema 设置不同的数据库访问权限
3. **备份灵活**：可以单独备份某个 schema 的数据
4. **维护清晰**：表结构按模块划分，便于理解和维护
5. **扩展性强**：未来可以轻松添加新的 schema（如 analytics、report 等）

## 注意事项

1. **search_path 顺序很重要**：`admin,toc,public` 表示查找表的优先级顺序
2. **跨 schema 查询**：如果需要跨 schema 查询，需要使用完整表名（如 `admin.sys_user`）
3. **迁移脚本**：未来的数据库迁移脚本也需要指定对应的 schema
4. **ORM 映射**：MyBatis-Plus 的 `@TableName` 注解已正确配置 schema，无需额外设置

## 验证 Schema 隔离

连接数据库后，可以通过以下 SQL 验证：

```sql
-- 查看所有 schema
SELECT schema_name FROM information_schema.schemata;

-- 查看 admin schema 中的表
SELECT table_name FROM information_schema.tables WHERE table_schema = 'admin';

-- 查看 toc schema 中的表
SELECT table_name FROM information_schema.tables WHERE table_schema = 'toc';

-- 验证 search_path
SHOW search_path;
```

## 故障排查

### 问题：找不到表

**原因**：search_path 未正确配置或 schema 不存在

**解决方案**：
1. 检查数据库连接 URL 是否包含 `search_path` 参数
2. 执行 `SHOW search_path;` 确认当前 search_path
3. 确认 schema 已创建：`\dn`

### 问题：表创建到了错误的 schema

**原因**：执行 DDL 时未指定 schema 前缀

**解决方案**：
1. 使用完整的表名（如 `admin.sys_user`）
2. 或在执行前先设置 search_path：`SET search_path TO admin;`
