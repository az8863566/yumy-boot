# 操作日志 API 文档

## 基础信息

- **模块**: 操作日志
- **基础路径**: `/admin/v1/oper-log`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 操作日志信息 (SysOperLogVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| operId | Long | 日志ID |
| title | String | 模块标题 |
| businessType | Integer | 业务类型(1新增 2修改 3删除 4导出) |
| method | String | 方法名称 |
| requestMethod | String | 请求方式 |
| operName | String | 操作人员 |
| operUrl | String | 请求URL |
| operIp | String | 主机地址 |
| status | Integer | 操作状态(1正常 0异常) |
| errorMsg | String | 错误消息 |
| operTime | String (yyyy-MM-dd HH:mm:ss) | 操作时间 |
| costTime | Long | 消耗时间(毫秒) |

### 操作日志查询参数 (SysOperLogQueryDTO)

| 字段名 | 类型 | 必填 | 说明 | 默认值 |
|--------|------|------|------|--------|
| title | String | 否 | 模块标题(模糊查询) | - |
| businessType | Integer | 否 | 业务类型 | - |
| operName | String | 否 | 操作人员(模糊查询) | - |
| status | Integer | 否 | 操作状态(1正常 0异常) | - |
| pageNum | Long | 否 | 页码 | 1 |
| pageSize | Long | 否 | 每页条数(最大200) | 10 |

---

## API 接口

### 1. 分页查询操作日志

**接口地址**: `GET /admin/v1/oper-log/page`

**权限标识**: `system:operLog:list`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| title | String | Query | 否 | 模块标题(模糊查询) |
| businessType | Integer | Query | 否 | 业务类型 |
| operName | String | Query | 否 | 操作人员(模糊查询) |
| status | Integer | Query | 否 | 操作状态(1正常 0异常) |
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
        "operId": 1,
        "title": "用户管理",
        "businessType": 1,
        "method": "com.de.food.admin.controller.SysUserController.create()",
        "requestMethod": "POST",
        "operName": "admin",
        "operUrl": "/admin/v1/user",
        "operIp": "192.168.1.100",
        "status": 1,
        "errorMsg": null,
        "operTime": "2026-04-26 10:30:00",
        "costTime": 125
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
| 500 | 服务器内部错误 |

## 业务类型说明

| 类型值 | 类型名称 | 说明 |
|--------|----------|------|
| 1 | 新增 | 新增数据操作 |
| 2 | 修改 | 修改数据操作 |
| 3 | 删除 | 删除数据操作 |
| 4 | 导出 | 导出数据操作 |
| 5 | 导入 | 导入数据操作 |
| 6 | 查询 | 查询数据操作 |
| 7 | 登录 | 用户登录操作 |
| 8 | 登出 | 用户登出操作 |

## 操作状态说明

| 状态值 | 说明 |
|--------|------|
| 1 | 正常(操作成功) |
| 0 | 异常(操作失败) |

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 操作日志只支持查询,不支持新增、修改、删除
3. 操作日志由系统自动记录,无需手动调用接口
4. 建议定期清理历史日志,避免数据量过大影响性能
5. 可通过 `status` 字段筛选失败的操作记录,便于问题排查
6. `costTime` 字段单位为毫秒,可用于分析接口性能
