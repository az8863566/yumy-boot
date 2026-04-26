# 认证管理 API 文档

## 基础信息

- **模块**: 认证管理
- **基础路径**: `/admin/v1/auth`
- **认证方式**: 登录接口无需认证，其他接口需要 Bearer Token (JWT)

## 数据模型

### 登录参数 (AuthLoginDTO)

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| username | String | 是 | 用户名 |
| password | String | 是 | 密码(只写) |

### 登录响应 (AuthLoginVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| token | String | 访问令牌(JWT) |
| username | String | 用户名 |
| nickname | String | 昵称 |
| permissions | List\<String\> | 权限标识列表 |
| admin | Boolean | 是否超管 |

---

## API 接口

### 1. 登录

**接口地址**: `POST /admin/v1/auth/login`

**权限要求**: 无需认证

**请求体**:

```json
{
  "username": "admin",
  "password": "123456"
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...",
    "username": "admin",
    "nickname": "系统管理员",
    "permissions": [
      "system:user:list",
      "system:user:add",
      "system:user:edit",
      "system:user:delete",
      "system:role:list",
      "system:role:add",
      "system:role:edit",
      "system:role:delete"
    ],
    "admin": true
  }
}
```

---

### 2. 登出

**接口地址**: `POST /admin/v1/auth/logout`

**权限要求**: 需要认证 (Bearer Token)

**请求体**: 无

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

## 通用响应格式

所有接口均使用统一的响应格式：

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码, 200表示成功 |
| msg | String | 提示信息 |
| data | Object | 响应数据 |

## 错误码说明

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 用户名或密码错误 |
| 403 | 账号已被停用 |
| 500 | 服务器内部错误 |

## 认证说明

### Token 使用方式

登录成功后，在后续请求的 Header 中添加 Authorization：

```
Authorization: Bearer {token}
```

### Token 有效期

- JWT Token 默认有效期根据系统配置而定
- Token 过期后需要重新登录获取新 Token

### 权限说明

- 登录响应中返回的 `permissions` 列表包含当前用户的所有权限标识
- 权限标识格式：`模块:资源:操作`（如 `system:user:list`）
- `admin` 字段为 `true` 表示超管用户，拥有所有权限

## 注意事项

1. 密码字段在前端传输时应使用 HTTPS 协议保证安全性
2. 登出接口需要在请求头中携带有效的 Token
3. 建议前端在登录成功后将 Token 存储在 localStorage 或 sessionStorage 中
4. 连续登录失败多次后可能触发账号锁定机制（根据系统配置）
