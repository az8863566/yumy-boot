# 角色管理 API 文档

## 基础信息

- **模块**: 角色管理
- **基础路径**: `/admin/v1/role`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 角色信息 (SysRoleVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| roleId | Long | 角色ID |
| roleName | String | 角色名称 |
| roleCode | String | 角色权限字符串 |
| status | Integer | 状态(1正常 0停用) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |

### 角色查询参数 (SysRoleQueryDTO)

| 字段名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| roleName | String | 否 | 角色名称(模糊查询) | - |
| roleCode | String | 否 | 角色权限字符串 | - |
| status | Integer | 否 | 状态(1正常 0停用) | - |
| pageNum | Long | 否 | 页码 | 1 |
| pageSize | Long | 否 | 每页条数(最大200) | 10 |

### 角色新增参数 (SysRoleCreateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| roleName | String | 是 | 非空, 最长64字符 | 角色名称 |
| roleCode | String | 是 | 非空, 最长64字符 | 角色权限字符串 |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

### 角色修改参数 (SysRoleUpdateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| roleId | Long | 是 | 非空 | 角色ID |
| roleName | String | 是 | 非空, 最长64字符 | 角色名称 |
| roleCode | String | 是 | 非空, 最长64字符 | 角色权限字符串 |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

---

## API 接口

### 1. 分页查询角色

**接口地址**: `GET /admin/v1/role/page`

**权限标识**: `system:role:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| roleName | String | Query | 否 | 角色名称(模糊查询) |
| roleCode | String | Query | 否 | 角色权限字符串 |
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
        "roleId": 1,
        "roleName": "系统管理员",
        "roleCode": "admin",
        "status": 1,
        "createTime": "2026-04-26 10:00:00",
        "remark": "系统超级管理员"
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

### 2. 查询角色详情

**接口地址**: `GET /admin/v1/role/{roleId}`

**权限标识**: `system:role:detail`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleId | Long | 是 | 角色ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "roleId": 1,
    "roleName": "系统管理员",
    "roleCode": "admin",
    "status": 1,
    "createTime": "2026-04-26 10:00:00",
    "remark": "系统超级管理员"
  }
}
```

---

### 3. 新增角色

**接口地址**: `POST /admin/v1/role`

**权限标识**: `system:role:add`

**请求体**:

```json
{
  "roleName": "普通用户",
  "roleCode": "user",
  "status": 1,
  "remark": "普通用户角色"
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

### 4. 修改角色

**接口地址**: `PUT /admin/v1/role`

**权限标识**: `system:role:edit`

**请求体**:

```json
{
  "roleId": 2,
  "roleName": "普通用户(修改)",
  "roleCode": "user",
  "status": 1,
  "remark": "修改后的备注"
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

### 5. 删除角色

**接口地址**: `DELETE /admin/v1/role/{roleId}`

**权限标识**: `system:role:delete`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleId | Long | 是 | 角色ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": null
}
```

---

### 6. 查询角色菜单ID列表

**接口地址**: `GET /admin/v1/role/{roleId}/menus`

**权限标识**: `system:role:queryMenus`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleId | Long | 是 | 角色ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [1, 2, 3, 5, 8, 10]
}
```

---

### 7. 分配角色菜单

**接口地址**: `POST /admin/v1/role/{roleId}/menus`

**权限标识**: `system:role:assignMenus`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| roleId | Long | 是 | 角色ID |

**请求体**:

```json
[1, 2, 3, 5, 8, 10]
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

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 删除角色采用逻辑删除,不会真正从数据库中删除数据
3. 角色权限字符串(roleCode)在全局范围内应唯一
4. 分页查询的 pageSize 最大值为 200
5. 状态字段: 1表示正常, 0表示停用