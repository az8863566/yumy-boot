# 字典类型管理 API 文档

## 基础信息

- **模块**: 字典类型管理
- **基础路径**: `/admin/v1/dict/type`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 字典类型信息 (SysDictTypeVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| dictId | Long | 字典ID |
| dictName | String | 字典名称 |
| dictType | String | 字典类型(唯一标识) |
| status | Integer | 状态(1正常 0停用) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |

### 字典类型查询参数 (SysDictTypeQueryDTO)

| 字段名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| dictName | String | 否 | 字典名称(模糊查询) | - |
| dictType | String | 否 | 字典类型 | - |
| status | Integer | 否 | 状态(1正常 0停用) | - |
| pageNum | Long | 否 | 页码 | 1 |
| pageSize | Long | 否 | 每页条数(最大200) | 10 |

### 字典类型新增参数 (SysDictTypeCreateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| dictName | String | 是 | 非空, 最长128字符 | 字典名称 |
| dictType | String | 是 | 非空, 最长128字符 | 字典类型 |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

### 字典类型修改参数 (SysDictTypeUpdateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| dictId | Long | 是 | 非空 | 字典ID |
| dictName | String | 是 | 非空, 最长128字符 | 字典名称 |
| dictType | String | 是 | 非空, 最长128字符 | 字典类型 |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

---

## API 接口

### 1. 分页查询字典类型

**接口地址**: `GET /admin/v1/dict/type/page`

**权限标识**: `system:dict:type:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| dictName | String | Query | 否 | 字典名称(模糊查询) |
| dictType | String | Query | 否 | 字典类型 |
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
        "dictId": 1,
        "dictName": "用户状态",
        "dictType": "sys_user_status",
        "status": 1,
        "createTime": "2026-04-26 10:00:00",
        "remark": "用户状态列表"
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

### 2. 查询字典类型详情

**接口地址**: `GET /admin/v1/dict/type/{dictId}`

**权限标识**: `system:dict:type:detail`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictId | Long | 是 | 字典ID |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "dictId": 1,
    "dictName": "用户状态",
    "dictType": "sys_user_status",
    "status": 1,
    "createTime": "2026-04-26 10:00:00",
    "remark": "用户状态列表"
  }
}
```

---

### 3. 新增字典类型

**接口地址**: `POST /admin/v1/dict/type`

**权限标识**: `system:dict:type:add`

**请求体**:

```json
{
  "dictName": "菜单类型",
  "dictType": "sys_menu_type",
  "status": 1,
  "remark": "菜单类型字典"
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

### 4. 修改字典类型

**接口地址**: `PUT /admin/v1/dict/type`

**权限标识**: `system:dict:type:edit`

**请求体**:

```json
{
  "dictId": 1,
  "dictName": "用户状态(修改)",
  "dictType": "sys_user_status",
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

### 5. 删除字典类型

**接口地址**: `DELETE /admin/v1/dict/type/{dictId}`

**权限标识**: `system:dict:type:delete`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictId | Long | 是 | 字典ID |

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

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

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
2. 删除字典类型采用逻辑删除
3. 字典类型(dictType)在全局范围内应唯一
4. 删除字典类型前应先删除其关联的字典数据
5. 字典类型用于分类管理字典数据,类似于命名空间
