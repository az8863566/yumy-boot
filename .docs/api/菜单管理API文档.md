# 菜单管理 API 文档

## 基础信息

- **模块**: 菜单管理
- **基础路径**: `/admin/v1/menu`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 菜单信息 (SysMenuVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| menuId | Long | 菜单ID |
| parentId | Long | 父菜单ID |
| menuName | String | 菜单名称 |
| menuType | Integer | 菜单类型(1目录 2菜单 3按钮) |
| path | String | 路由地址 |
| component | String | 组件路径 |
| perms | String | 权限标识 |
| icon | String | 菜单图标 |
| sortOrder | Integer | 显示顺序 |
| visible | Integer | 菜单显示状态(1显示 0隐藏) |
| status | Integer | 菜单可用状态(1正常 0停用) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |
| children | List\<SysMenuVO\> | 子菜单(树形结构) |

### 菜单查询参数 (SysMenuQueryDTO)

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| menuName | String | 否 | 菜单名称(模糊查询) |
| status | Integer | 否 | 状态(1正常 0停用) |

### 菜单新增参数 (SysMenuCreateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| parentId | Long | 否 | - | 父菜单ID(顶级菜单为0或null) |
| menuName | String | 是 | 非空, 最长64字符 | 菜单名称 |
| menuType | Integer | 是 | 非空 | 菜单类型(1目录 2菜单 3按钮) |
| path | String | 否 | - | 路由地址 |
| component | String | 否 | - | 组件路径 |
| perms | String | 否 | - | 权限标识 |
| icon | String | 否 | - | 菜单图标 |
| sortOrder | Integer | 否 | - | 显示顺序 |
| visible | Integer | 否 | - | 菜单显示状态(1显示 0隐藏) |
| status | Integer | 否 | - | 菜单可用状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

### 菜单修改参数 (SysMenuUpdateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| menuId | Long | 是 | 非空 | 菜单ID |
| parentId | Long | 否 | - | 父菜单ID |
| menuName | String | 是 | 非空, 最长64字符 | 菜单名称 |
| menuType | Integer | 是 | 非空 | 菜单类型(1目录 2菜单 3按钮) |
| path | String | 否 | - | 路由地址 |
| component | String | 否 | - | 组件路径 |
| perms | String | 否 | - | 权限标识 |
| icon | String | 否 | - | 菜单图标 |
| sortOrder | Integer | 否 | - | 显示顺序 |
| visible | Integer | 否 | - | 菜单显示状态(1显示 0隐藏) |
| status | Integer | 否 | - | 菜单可用状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

---

## API 接口

### 1. 查询菜单树

**接口地址**: `GET /admin/v1/menu/tree`

**权限标识**: `system:menu:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| menuName | String | Query | 否 | 菜单名称(模糊查询) |
| status | Integer | Query | 否 | 状态(1正常 0停用) |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "menuId": 1,
      "parentId": 0,
      "menuName": "系统管理",
      "menuType": 1,
      "path": "/system",
      "component": null,
      "perms": null,
      "icon": "setting",
      "sortOrder": 1,
      "visible": 1,
      "status": 1,
      "createTime": "2026-04-26 10:00:00",
      "remark": "系统管理目录",
      "children": [
        {
          "menuId": 2,
          "parentId": 1,
          "menuName": "用户管理",
          "menuType": 2,
          "path": "user",
          "component": "system/user/index",
          "perms": "system:user:list",
          "icon": "user",
          "sortOrder": 1,
          "visible": 1,
          "status": 1,
          "createTime": "2026-04-26 10:00:00",
          "remark": "用户管理菜单",
          "children": []
        }
      ]
    }
  ]
}
```

---

### 2. 查询菜单详情

**接口地址**: `GET /admin/v1/menu/{menuId}`

**权限标识**: `system:menu:detail`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| menuId | Long | 是 | 菜单ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "menuId": 2,
    "parentId": 1,
    "menuName": "用户管理",
    "menuType": 2,
    "path": "user",
    "component": "system/user/index",
    "perms": "system:user:list",
    "icon": "user",
    "sortOrder": 1,
    "visible": 1,
    "status": 1,
    "createTime": "2026-04-26 10:00:00",
    "remark": "用户管理菜单",
    "children": []
  }
}
```

---

### 3. 新增菜单

**接口地址**: `POST /admin/v1/menu`

**权限标识**: `system:menu:add`

**请求体**:

```json
{
  "parentId": 1,
  "menuName": "角色管理",
  "menuType": 2,
  "path": "role",
  "component": "system/role/index",
  "perms": "system:role:list",
  "icon": "peoples",
  "sortOrder": 2,
  "visible": 1,
  "status": 1,
  "remark": "角色管理菜单"
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

### 4. 修改菜单

**接口地址**: `PUT /admin/v1/menu`

**权限标识**: `system:menu:edit`

**请求体**:

```json
{
  "menuId": 2,
  "parentId": 1,
  "menuName": "用户管理(修改)",
  "menuType": 2,
  "path": "user",
  "component": "system/user/index",
  "perms": "system:user:list",
  "icon": "user",
  "sortOrder": 1,
  "visible": 1,
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

### 5. 删除菜单

**接口地址**: `DELETE /admin/v1/menu/{menuId}`

**权限标识**: `system:menu:delete`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| menuId | Long | 是 | 菜单ID |

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

## 菜单类型说明

| 类型值 | 类型名称 | 说明 |
|--------|----------|------|
| 1 | 目录 | 顶级或中间目录节点,用于组织菜单结构 |
| 2 | 菜单 | 具体的页面菜单,对应前端路由和组件 |
| 3 | 按钮 | 页面内的操作按钮权限,不对应路由 |

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 删除菜单采用逻辑删除,不会真正从数据库中删除数据
3. 菜单树接口返回树形结构,通过 `children` 字段嵌套子菜单
4. 菜单类型为"按钮"时,通常不设置 `path` 和 `component`
5. `perms` 权限标识格式建议为 `模块:资源:操作`(如 `system:user:list`)
6. 顶级菜单的 `parentId` 可设置为 0 或 null
7. 删除菜单前应先删除其子菜单,或系统会自动级联删除子菜单
