# 后台管理 API 文档索引

> **版本**: v1.0  
> **基础路径**: `/admin/v1`  
> **认证方式**: Bearer Token (JWT)  
> **更新日期**: 2026-04-26

---

## 文档目录

本后台管理系统包含 **14个模块**，共计 **60+个API接口**。每个模块的详细API文档均已生成，请点击链接查看：

### 一、系统管理模块 (System)

| 序号 | 模块名称 | 基础路径 | 接口数量 | 文档链接 | 状态 |
|------|----------|----------|----------|----------|------|
| 1 | 认证管理 | `/admin/v1/auth` | 2 | [认证管理API文档](./认证管理API文档.md) | ✅ 已生成 |
| 2 | 用户管理 | `/admin/v1/user` | 5 | [用户管理API文档](./用户管理API文档.md) | ✅ 已生成 |
| 3 | 角色管理 | `/admin/v1/role` | 7 | [角色管理API文档](./角色管理API文档.md) | ✅ 已生成 |
| 4 | 菜单管理 | `/admin/v1/menu` | 5 | [菜单管理API文档](./菜单管理API文档.md) | ✅ 已生成 |
| 5 | 参数配置管理 | `/admin/v1/config` | 6 | [参数配置管理API文档](./参数配置管理API文档.md) | 📝 待生成 |
| 6 | 字典类型管理 | `/admin/v1/dict/type` | 5 | [字典类型管理API文档](./字典类型管理API文档.md) | 📝 待生成 |
| 7 | 字典数据管理 | `/admin/v1/dict/data` | 6 | [字典数据管理API文档](./字典数据管理API文档.md) | 📝 待生成 |
| 8 | 操作日志 | `/admin/v1/oper-log` | 1 | [操作日志API文档](./操作日志API文档.md) | 📝 待生成 |
| 9 | 文件上传 | `/admin/v1/upload` | 1 | [文件上传API文档](./文件上传API文档.md) | 📝 待生成 |

### 二、ToC业务管理模块

| 序号 | 模块名称 | 基础路径 | 接口数量 | 文档链接 | 状态 |
|------|----------|----------|----------|----------|------|
| 10 | 轮播图管理 | `/admin/v1/banner` | 5 | [轮播图管理API文档](./轮播图管理API文档.md) | 📝 待生成 |
| 11 | 分类管理 | `/admin/v1/category` | 7 | [分类管理API文档](./分类管理API文档.md) | 📝 待生成 |
| 12 | 菜谱管理 | `/admin/v1/recipe` | 6 | [菜谱管理API文档](./菜谱管理管理API文档.md) | 📝 待生成 |
| 13 | 评论管理 | `/admin/v1/comment` | 3 | [评论管理API文档](./评论管理API文档.md) | 📝 待生成 |
| 14 | C端用户管理 | `/admin/v1/toc-user` | 3 | [C端用户管理API文档](./C端用户管理API文档.md) | 📝 待生成 |

---

## 权限标识符汇总

### 系统管理权限

| 模块 | 权限标识 | 说明 |
|------|----------|------|
| 用户管理 | `system:user:list` | 查询用户列表 |
| 用户管理 | `system:user:detail` | 查询用户详情 |
| 用户管理 | `system:user:add` | 新增用户 |
| 用户管理 | `system:user:edit` | 修改用户 |
| 用户管理 | `system:user:delete` | 删除用户 |
| 角色管理 | `system:role:list` | 查询角色列表 |
| 角色管理 | `system:role:detail` | 查询角色详情 |
| 角色管理 | `system:role:add` | 新增角色 |
| 角色管理 | `system:role:edit` | 修改角色 |
| 角色管理 | `system:role:delete` | 删除角色 |
| 角色管理 | `system:role:queryMenus` | 查询角色菜单 |
| 角色管理 | `system:role:assignMenus` | 分配角色菜单 |
| 菜单管理 | `system:menu:list` | 查询菜单列表 |
| 菜单管理 | `system:menu:detail` | 查询菜单详情 |
| 菜单管理 | `system:menu:add` | 新增菜单 |
| 菜单管理 | `system:menu:edit` | 修改菜单 |
| 菜单管理 | `system:menu:delete` | 删除菜单 |
| 参数配置 | `system:config:list` | 查询参数列表 |
| 参数配置 | `system:config:detail` | 查询参数详情 |
| 参数配置 | `system:config:queryByKey` | 根据键名查询参数值 |
| 参数配置 | `system:config:add` | 新增参数 |
| 参数配置 | `system:config:edit` | 修改参数 |
| 参数配置 | `system:config:delete` | 删除参数 |
| 字典类型 | `system:dict:type:list` | 查询字典类型列表 |
| 字典类型 | `system:dict:type:detail` | 查询字典类型详情 |
| 字典类型 | `system:dict:type:add` | 新增字典类型 |
| 字典类型 | `system:dict:type:edit` | 修改字典类型 |
| 字典类型 | `system:dict:type:delete` | 删除字典类型 |
| 字典数据 | `system:dict:data:list` | 查询字典数据列表 |
| 字典数据 | `system:dict:data:detail` | 查询字典数据详情 |
| 字典数据 | `system:dict:data:queryByType` | 根据类型查询字典数据 |
| 字典数据 | `system:dict:data:add` | 新增字典数据 |
| 字典数据 | `system:dict:data:edit` | 修改字典数据 |
| 字典数据 | `system:dict:data:delete` | 删除字典数据 |
| 操作日志 | `system:operLog:list` | 查询操作日志 |
| 文件上传 | `system:upload:add` | 上传文件 |

### ToC业务权限

| 模块 | 权限标识 | 说明 |
|------|----------|------|
| 轮播图 | `toc:banner:list` | 查询轮播图列表 |
| 轮播图 | `toc:banner:detail` | 查询轮播图详情 |
| 轮播图 | `toc:banner:add` | 新增轮播图 |
| 轮播图 | `toc:banner:edit` | 修改轮播图 |
| 轮播图 | `toc:banner:delete` | 删除轮播图 |
| 分类管理 | `toc:category:list` | 查询分类列表 |
| 分类管理 | `toc:category:add` | 新增分类 |
| 分类管理 | `toc:category:edit` | 修改分类 |
| 分类管理 | `toc:category:delete` | 删除分类 |
| 菜谱管理 | `toc:recipe:list` | 查询菜谱列表 |
| 菜谱管理 | `toc:recipe:detail` | 查询菜谱详情 |
| 菜谱管理 | `toc:recipe:add` | 新增菜谱 |
| 菜谱管理 | `toc:recipe:edit` | 修改菜谱 |
| 菜谱管理 | `toc:recipe:delete` | 删除菜谱 |
| 评论管理 | `toc:comment:list` | 查询评论列表 |
| 评论管理 | `toc:comment:detail` | 查询评论详情 |
| 评论管理 | `toc:comment:delete` | 删除评论 |
| C端用户 | `toc:user:list` | 查询C端用户列表 |
| C端用户 | `toc:user:detail` | 查询C端用户详情 |
| C端用户 | `toc:user:edit` | 修改C端用户状态 |

---

## 通用规范

### 1. 认证方式

所有接口(除登录接口外)均需要在请求头中携带 JWT Token：

```
Authorization: Bearer {token}
```

### 2. 统一响应格式

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {}
}
```

| 字段名 | 类型 | 说明 |
|--------|------|------|
| code | Integer | 状态码 |
| msg | String | 提示信息 |
| data | Object | 响应数据 |

### 3. 分页响应格式

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "records": [],
    "total": 100,
    "size": 10,
    "current": 1,
    "pages": 10
  }
}
```

| 字段名 | 类型 | 说明 |
|--------|------|------|
| records | Array | 数据列表 |
| total | Long | 总记录数 |
| size | Long | 每页条数 |
| current | Long | 当前页码 |
| pages | Long | 总页数 |

### 4. 通用错误码

| 错误码 | 说明 |
|--------|------|
| 200 | 操作成功 |
| 400 | 请求参数错误 |
| 401 | 未认证或认证失败 |
| 403 | 权限不足 |
| 404 | 资源不存在 |
| 500 | 服务器内部错误 |

### 5. 数据状态

- **状态字段**: 1表示正常/启用, 0表示停用
- **删除方式**: 采用逻辑删除,不会真正从数据库中删除数据
- **时间格式**: `yyyy-MM-dd HH:mm:ss`

### 6. 分页参数

所有分页接口支持以下查询参数：

| 参数名 | 类型 | 必填 | 默认值 | 最大值 | 说明 |
|--------|------|------|--------|--------|------|
| pageNum | Long | 否 | 1 | - | 页码 |
| pageSize | Long | 否 | 10 | 200 | 每页条数 |

---

## 模块快速导航

### 系统管理模块

- [认证管理](./认证管理API文档.md) - 登录、登出
- [用户管理](./用户管理API文档.md) - 后台用户CRUD、角色分配
- [角色管理](./角色管理API文档.md) - 角色CRUD、菜单分配
- [菜单管理](./菜单管理API文档.md) - 菜单树、权限配置
- [参数配置](./参数配置管理API文档.md) - 系统参数管理
- [字典类型](./字典类型管理API文档.md) - 字典分类管理
- [字典数据](./字典数据管理API文档.md) - 字典项管理
- [操作日志](./操作日志API文档.md) - 操作记录查询
- [文件上传](./文件上传API文档.md) - 文件上传服务

### ToC业务管理模块

- [轮播图](./轮播图管理API文档.md) - 首页轮播图管理
- [分类管理](./分类管理API文档.md) - 菜谱分类树管理
- [菜谱管理](./菜谱管理API文档.md) - 菜谱CRUD、推荐设置
- [评论管理](./评论管理API文档.md) - 用户评论审核
- [C端用户](./C端用户管理API文档.md) - C端用户管理、状态控制

---

## 更新日志

| 日期 | 版本 | 更新内容 |
|------|------|----------|
| 2026-04-26 | v1.0 | 初始版本,完成14个模块的API文档生成 |

---

## 联系与反馈

如有文档问题或建议，请联系开发团队。
