# 用户管理 API 文档

## 基础信息

- **模块**: 用户管理
- **基础路径**: `/admin/v1/user`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 用户信息 (SysUserVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| userId | Long | 用户ID |
| username | String | 用户名 |
| nickname | String | 昵称 |
| avatar | String | 头像 |
| email | String | 邮箱 |
| phone | String | 手机号 |
| status | Integer | 状态(1正常 0停用) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |
| roles | List\<SysRoleVO\> | 关联角色列表 |

### 角色信息 (SysRoleVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| roleId | Long | 角色ID |
| roleName | String | 角色名称 |
| roleCode | String | 角色权限字符串 |
| status | Integer | 状态(1正常 0停用) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |

### 用户查询参数 (SysUserQueryDTO)

| 字段名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| username | String | 否 | 用户名(模糊查询) | - |
| nickname | String | 否 | 昵称(模糊查询) | - |
| phone | String | 否 | 手机号 | - |
| status | Integer | 否 | 状态(1正常 0停用) | - |
| pageNum | Long | 否 | 页码 | 1 |
| pageSize | Long | 否 | 每页条数(最大200) | 10 |

### 用户新增参数 (SysUserCreateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| username | String | 是 | 非空, 最长64字符 | 用户名 |
| password | String | 是 | 非空, 6-64字符 | 密码(只写) |
| nickname | String | 是 | 非空, 最长64字符 | 昵称 |
| avatar | String | 否 | - | 头像 |
| email | String | 否 | 邮箱格式校验 | 邮箱 |
| phone | String | 否 | 手机号格式校验 | 手机号 |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |
| roleIds | List\<Long\> | 否 | - | 关联角色ID列表 |

### 用户修改参数 (SysUserUpdateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| userId | Long | 是 | 非空 | 用户ID |
| nickname | String | 否 | 最长64字符 | 昵称 |
| avatar | String | 否 | - | 头像 |
| email | String | 否 | 邮箱格式校验 | 邮箱 |
| phone | String | 否 | 手机号格式校验 | 手机号 |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |
| roleIds | List\<Long\> | 否 | - | 关联角色ID列表 |

---

## API 接口

### 1. 分页查询用户

**接口地址**: `GET /admin/v1/user/page`

**权限标识**: `system:user:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| username | String | Query | 否 | 用户名(模糊查询) |
| nickname | String | Query | 否 | 昵称(模糊查询) |
| phone | String | Query | 否 | 手机号 |
| status | Integer | Query | 否 | 状态(1正常 0停用) |
| pageNum | Long | Query | 否 | 页码, 默认1 |
| pageSize | Long | Query | 否 | 每页条数, 默认10, 最大200 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "records": [
      {
        "userId": 1,
        "username": "admin",
        "nickname": "系统管理员",
        "avatar": "https://example.com/avatar.jpg",
        "email": "admin@example.com",
        "phone": "13800138000",
        "status": 1,
        "createTime": "2026-04-26 10:00:00",
        "remark": "系统超级管理员",
        "roles": [
          {
            "roleId": 1,
            "roleName": "系统管理员",
            "roleCode": "admin",
            "status": 1,
            "createTime": "2026-04-26 10:00:00",
            "remark": "系统超级管理员"
          }
        ]
      }
    ],
    "total": 1,
    "size": 10,
    "current": 1,
    "pages": 1
  }
}
```

---

### 2. 查询用户详情

**接口地址**: `GET /admin/v1/user/{userId}`

**权限标识**: `system:user:detail`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | Long | 是 | 用户ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "userId": 1,
    "username": "admin",
    "nickname": "系统管理员",
    "avatar": "https://example.com/avatar.jpg",
    "email": "admin@example.com",
    "phone": "13800138000",
    "status": 1,
    "createTime": "2026-04-26 10:00:00",
    "remark": "系统超级管理员",
    "roles": [
      {
        "roleId": 1,
        "roleName": "系统管理员",
        "roleCode": "admin",
        "status": 1,
        "createTime": "2026-04-26 10:00:00",
        "remark": "系统超级管理员"
      }
    ]
  }
}
```

---

### 3. 新增用户

**接口地址**: `POST /admin/v1/user`

**权限标识**: `system:user:add`

**请求体**:

```json
{
  "username": "testuser",
  "password": "123456",
  "nickname": "测试用户",
  "avatar": "https://example.com/avatar.jpg",
  "email": "test@example.com",
  "phone": "13800138000",
  "status": 1,
  "remark": "测试用户",
  "roleIds": [1, 2]
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 4. 修改用户

**接口地址**: `PUT /admin/v1/user`

**权限标识**: `system:user:edit`

**请求体**:

```json
{
  "userId": 2,
  "nickname": "测试用户(修改)",
  "avatar": "https://example.com/new-avatar.jpg",
  "email": "newtest@example.com",
  "phone": "13900139000",
  "status": 1,
  "remark": "修改后的备注",
  "roleIds": [1, 3]
}
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 5. 删除用户

**接口地址**: `DELETE /admin/v1/user/{userId}`

**权限标识**: `system:user:delete`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| userId | Long | 是 | 用户ID |

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
| 401 | 未认证或认证失败 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

## 校验规则说明

### 邮箱格式校验

正则表达式: `^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$`

示例:
- ✅ `user@example.com`
- ✅ `test.user@domain.co.uk`
- ❌ `user@`
- ❌ `@example.com`

### 手机号格式校验

正则表达式: `^1[3-9]\d{9}$`

示例:
- ✅ `13800138000`
- ✅ `18912345678`
- ❌ `12800138000`
- ❌ `1380013800`

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 删除用户采用逻辑删除,不会真正从数据库中删除数据
3. 用户名(username)在全局范围内应唯一
4. 分页查询的 pageSize 最大值为 200
5. 状态字段: 1表示正常, 0表示停用
6. 密码字段(password)仅在新增用户时需要,修改用户时不支持修改密码
7. 关联角色通过 roleIds 字段进行分配,支持同时分配多个角色
8. 密码在前端传输时应使用 HTTPS 协议保证安全性