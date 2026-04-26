# 参数配置管理 API 文档

## 基础信息

- **模块**: 参数配置管理
- **基础路径**: `/admin/v1/config`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 参数配置信息 (SysConfigVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| configId | Long | 参数ID |
| configName | String | 参数名称 |
| configKey | String | 参数键名 |
| configValue | String | 参数键值 |
| configType | Integer | 系统内置(1是 2否) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |

### 参数配置查询参数 (SysConfigQueryDTO)

| 字段名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| configName | String | 否 | 参数名称(模糊查询) | - |
| configKey | String | 否 | 参数键名 | - |
| configType | Integer | 否 | 系统内置(1是 2否) | - |
| pageNum | Long | 否 | 页码 | 1 |
| pageSize | Long | 否 | 每页条数(最大200) | 10 |

### 参数配置新增参数 (SysConfigCreateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| configName | String | 是 | 非空, 最长128字符 | 参数名称 |
| configKey | String | 是 | 非空, 最长128字符 | 参数键名 |
| configValue | String | 否 | - | 参数键值 |
| configType | Integer | 否 | - | 系统内置(1是 2否) |
| remark | String | 否 | - | 备注 |

### 参数配置修改参数 (SysConfigUpdateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| configId | Long | 是 | 非空 | 参数ID |
| configName | String | 是 | 非空, 最长128字符 | 参数名称 |
| configKey | String | 是 | 非空, 最长128字符 | 参数键名 |
| configValue | String | 否 | - | 参数键值 |
| configType | Integer | 否 | - | 系统内置(1是 2否) |
| remark | String | 否 | - | 备注 |

---

## API 接口

### 1. 分页查询参数配置

**接口地址**: `GET /admin/v1/config/page`

**权限标识**: `system:config:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| configName | String | Query | 否 | 参数名称(模糊查询) |
| configKey | String | Query | 否 | 参数键名 |
| configType | Integer | Query | 否 | 系统内置(1是 2否) |
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
        "configId": 1,
        "configName": "主框架页-侧边栏主题",
        "configKey": "sys.index.sideTheme",
        "configValue": "theme-dark",
        "configType": 1,
        "createTime": "2026-04-26 10:00:00",
        "remark": "侧边栏主题"
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

### 2. 查询参数配置详情

**接口地址**: `GET /admin/v1/config/{configId}`

**权限标识**: `system:config:detail`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| configId | Long | 是 | 参数ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "configId": 1,
    "configName": "主框架页-侧边栏主题",
    "configKey": "sys.index.sideTheme",
    "configValue": "theme-dark",
    "configType": 1,
    "createTime": "2026-04-26 10:00:00",
    "remark": "侧边栏主题"
  }
}
```

---

### 3. 根据键名查询参数值

**接口地址**: `GET /admin/v1/config/key/{configKey}`

**权限标识**: `system:config:queryByKey`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| configKey | String | 是 | 参数键名 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": "theme-dark"
}
```

---

### 4. 新增参数配置

**接口地址**: `POST /admin/v1/config`

**权限标识**: `system:config:add`

**请求体**:

```json
{
  "configName": "系统登录限制",
  "configKey": "sys.login.maxRetryCount",
  "configValue": "5",
  "configType": 2,
  "remark": "用户登录最大失败次数"
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

### 5. 修改参数配置

**接口地址**: `PUT /admin/v1/config`

**权限标识**: `system:config:edit`

**请求体**:

```json
{
  "configId": 1,
  "configName": "主框架页-侧边栏主题(修改)",
  "configKey": "sys.index.sideTheme",
  "configValue": "theme-light",
  "configType": 1,
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

### 6. 删除参数配置

**接口地址**: `DELETE /admin/v1/config/{configId}`

**权限标识**: `system:config:delete`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| configId | Long | 是 | 参数ID |

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

## 参数类型说明

| 类型值 | 类型名称 | 说明 |
|--------|----------|------|
| 1 | 系统内置 | 系统初始化时创建的参数,通常不允许删除 |
| 2 | 自定义 | 用户自定义的参数,可以自由管理 |

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 删除参数配置采用逻辑删除,不会真正从数据库中删除数据
3. 参数键名(configKey)在全局范围内应唯一
4. 系统内置参数(configType=1)建议谨慎修改
5. 参数键值可以是字符串、数字、JSON等格式
6. 通过 `根据键名查询参数值` 接口可快速获取某个参数的值,常用于前端动态配置
