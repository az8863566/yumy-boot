# 字典数据管理 API 文档

## 基础信息

- **模块**: 字典数据管理
- **基础路径**: `/admin/v1/dict/data`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 字典数据信息 (SysDictDataVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| dictCode | Long | 字典编码 |
| dictSort | Integer | 字典排序 |
| dictLabel | String | 字典标签(展示值) |
| dictValue | String | 字典键值(实际值) |
| dictType | String | 字典类型 |
| cssClass | String | 样式属性 |
| listClass | String | 表格回显样式 |
| isDefault | Integer | 是否默认(1是 0否) |
| status | Integer | 状态(1正常 0停用) |
| createTime | String (yyyy-MM-dd HH:mm:ss) | 创建时间 |
| remark | String | 备注 |

### 字典数据查询参数 (SysDictDataQueryDTO)

| 字段名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| dictType | String | 否 | 字典类型 | - |
| dictLabel | String | 否 | 字典标签(模糊查询) | - |
| status | Integer | 否 | 状态(1正常 0停用) | - |
| pageNum | Long | 否 | 页码 | 1 |
| pageSize | Long | 否 | 每页条数(最大200) | 10 |

### 字典数据新增参数 (SysDictDataCreateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| dictSort | Integer | 否 | - | 字典排序 |
| dictLabel | String | 是 | 非空, 最长128字符 | 字典标签 |
| dictValue | String | 是 | 非空, 最长128字符 | 字典键值 |
| dictType | String | 是 | 非空, 最长128字符 | 字典类型 |
| cssClass | String | 否 | - | 样式属性 |
| listClass | String | 否 | - | 表格回显样式 |
| isDefault | Integer | 否 | - | 是否默认(1是 0否) |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

### 字典数据修改参数 (SysDictDataUpdateDTO)

| 字段名 | 类型 | 必填 | 校验规则 | 说明 |
|--------|------|------|----------|------|
| dictCode | Long | 是 | 非空 | 字典编码 |
| dictSort | Integer | 否 | - | 字典排序 |
| dictLabel | String | 是 | 非空, 最长128字符 | 字典标签 |
| dictValue | String | 是 | 非空, 最长128字符 | 字典键值 |
| dictType | String | 是 | 非空, 最长128字符 | 字典类型 |
| cssClass | String | 否 | - | 样式属性 |
| listClass | String | 否 | - | 表格回显样式 |
| isDefault | Integer | 否 | - | 是否默认(1是 0否) |
| status | Integer | 否 | - | 状态(1正常 0停用) |
| remark | String | 否 | - | 备注 |

---

## API 接口

### 1. 分页查询字典数据

**接口地址**: `GET /admin/v1/dict/data/page`

**权限标识**: `system:dict:data:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| dictType | String | Query | 否 | 字典类型 |
| dictLabel | String | Query | 否 | 字典标签(模糊查询) |
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
        "dictCode": 1,
        "dictSort": 1,
        "dictLabel": "正常",
        "dictValue": "1",
        "dictType": "sys_user_status",
        "cssClass": null,
        "listClass": "success",
        "isDefault": 1,
        "status": 1,
        "createTime": "2026-04-26 10:00:00",
        "remark": "正常状态"
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

### 2. 根据字典类型查询字典数据列表

**接口地址**: `GET /admin/v1/dict/data/type/{dictType}`

**权限标识**: `system:dict:data:queryByType`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictType | String | 是 | 字典类型 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": [
    {
      "dictCode": 1,
      "dictSort": 1,
      "dictLabel": "正常",
      "dictValue": "1",
      "dictType": "sys_user_status",
      "cssClass": null,
      "listClass": "success",
      "isDefault": 1,
      "status": 1,
      "createTime": "2026-04-26 10:00:00",
      "remark": "正常状态"
    },
    {
      "dictCode": 2,
      "dictSort": 2,
      "dictLabel": "停用",
      "dictValue": "0",
      "dictType": "sys_user_status",
      "cssClass": null,
      "listClass": "danger",
      "isDefault": 0,
      "status": 1,
      "createTime": "2026-04-26 10:00:00",
      "remark": "停用状态"
    }
  ]
}
```

---

### 3. 查询字典数据详情

**接口地址**: `GET /admin/v1/dict/data/{dictCode}`

**权限标识**: `system:dict:data:detail`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictCode | Long | 是 | 字典编码 |

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "dictCode": 1,
    "dictSort": 1,
    "dictLabel": "正常",
    "dictValue": "1",
    "dictType": "sys_user_status",
    "cssClass": null,
    "listClass": "success",
    "isDefault": 1,
    "status": 1,
    "createTime": "2026-04-26 10:00:00",
    "remark": "正常状态"
  }
}
```

---

### 4. 新增字典数据

**接口地址**: `POST /admin/v1/dict/data`

**权限标识**: `system:dict:data:add`

**请求体**:

```json
{
  "dictSort": 1,
  "dictLabel": "启用",
  "dictValue": "1",
  "dictType": "sys_normal_disable",
  "cssClass": null,
  "listClass": "success",
  "isDefault": 1,
  "status": 1,
  "remark": "启用状态"
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

### 5. 修改字典数据

**接口地址**: `PUT /admin/v1/dict/data`

**权限标识**: `system:dict:data:edit`

**请求体**:

```json
{
  "dictCode": 1,
  "dictSort": 1,
  "dictLabel": "正常(修改)",
  "dictValue": "1",
  "dictType": "sys_user_status",
  "cssClass": null,
  "listClass": "success",
  "isDefault": 1,
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

### 6. 删除字典数据

**接口地址**: `DELETE /admin/v1/dict/data/{dictCode}`

**权限标识**: `system:dict:data:delete`

**路径参数**:

| 参数名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| dictCode | Long | 是 | 字典编码 |

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

## 字段说明

### listClass 表格回显样式

常用的表格回显样式类型：

| 样式值 | 说明 | 颜色 |
|--------|------|------|
| default | 默认 | 灰色 |
| primary | 主要 | 蓝色 |
| success | 成功 | 绿色 |
| info | 信息 | 青色 |
| warning | 警告 | 黄色 |
| danger | 危险 | 红色 |

### isDefault 是否默认

| 值 | 说明 |
|----|------|
| 1 | 是(默认选项) |
| 0 | 否 |

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 删除字典数据采用逻辑删除
3. 字典数据必须关联到已存在的字典类型(dictType)
4. `根据字典类型查询字典数据列表` 接口常用于前端下拉选择框数据加载
5. dictSort 用于控制字典数据的显示顺序,数值越小越靠前
6. listClass 用于前端表格中根据字典值显示不同颜色的标签
