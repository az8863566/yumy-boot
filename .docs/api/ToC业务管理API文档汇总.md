# ToC业务管理 API 文档汇总

> **模块概览**: 本汇总包含5个ToC业务管理模块的API接口概览  
> **基础路径**: `/admin/v1`  
> **认证方式**: Bearer Token (JWT)  
> **更新日期**: 2026-04-26

---

## 模块列表

| 序号 | 模块名称 | 基础路径 | 接口数量 | 详细文档 |
|------|----------|----------|----------|----------|
| 1 | 轮播图管理 | `/admin/v1/banner` | 5 | 见下方详情 |
| 2 | 分类管理 | `/admin/v1/category` | 7 | 见下方详情 |
| 3 | 菜谱管理 | `/admin/v1/recipe` | 6 | 见下方详情 |
| 4 | 评论管理 | `/admin/v1/comment` | 3 | 见下方详情 |
| 5 | C端用户管理 | `/admin/v1/toc-user` | 3 | 见下方详情 |

---

## 一、轮播图管理 (`/admin/v1/banner`)

### 接口列表

| 序号 | 接口名称 | 方法 | 路径 | 权限标识 |
|------|----------|------|------|----------|
| 1 | 分页查询轮播图 | GET | `/admin/v1/banner/page` | `toc:banner:list` |
| 2 | 查询轮播图详情 | GET | `/admin/v1/banner/{bannerId}` | `toc:banner:detail` |
| 3 | 新增轮播图 | POST | `/admin/v1/banner` | `toc:banner:add` |
| 4 | 修改轮播图 | PUT | `/admin/v1/banner` | `toc:banner:edit` |
| 5 | 删除轮播图 | DELETE | `/admin/v1/banner/{bannerId}` | `toc:banner:delete` |

### 核心数据模型

#### 轮播图信息 (TocBannerVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| bannerId | Long | 轮播图ID |
| title | String | 轮播图标题 |
| subtitle | String | 副标题/描述 |
| image | String | 轮播图图片URL |
| linkType | Integer | 跳转类型(0无跳转 1菜谱详情 2外部链接) |
| linkValue | String | 跳转目标(菜谱ID或URL) |
| sortOrder | Integer | 排序顺序(越小越靠前) |
| status | Integer | 状态(1启用 0停用) |
| createTime | String | 创建时间 |
| updateTime | String | 更新时间 |

### 使用场景

- 管理APP/网站首页轮播广告
- 支持跳转到菜谱详情或外部链接
- 通过sortOrder控制显示顺序

---

## 二、分类管理 (`/admin/v1/category`)

### 接口列表

| 序号 | 接口名称 | 方法 | 路径 | 权限标识 |
|------|----------|------|------|----------|
| 1 | 获取分类树 | GET | `/admin/v1/category/tree` | `toc:category:list` |
| 2 | 新增父分类 | POST | `/admin/v1/category/parent` | `toc:category:add` |
| 3 | 修改父分类 | PUT | `/admin/v1/category/parent` | `toc:category:edit` |
| 4 | 删除父分类 | DELETE | `/admin/v1/category/parent/{categoryId}` | `toc:category:delete` |
| 5 | 新增子分类 | POST | `/admin/v1/category/sub` | `toc:category:add` |
| 6 | 修改子分类 | PUT | `/admin/v1/category/sub` | `toc:category:edit` |
| 7 | 删除子分类 | DELETE | `/admin/v1/category/sub/{categoryId}` | `toc:category:delete` |

### 核心数据模型

#### 分类树 (TocCategoryTreeVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| categoryId | Long | 分类ID |
| categoryName | String | 分类名称 |
| icon | String | 分类图标 |
| sortOrder | Integer | 排序顺序 |
| status | Integer | 状态(1启用 0停用) |
| children | List | 子分类列表 |

### 使用场景

- 管理菜谱分类体系(父分类-子分类两级结构)
- 分类树接口返回完整的层级结构
- 支持独立管理父分类和子分类

---

## 三、菜谱管理 (`/admin/v1/recipe`)

### 接口列表

| 序号 | 接口名称 | 方法 | 路径 | 权限标识 |
|------|----------|------|------|----------|
| 1 | 分页查询菜谱 | GET | `/admin/v1/recipe/page` | `toc:recipe:list` |
| 2 | 查询菜谱详情 | GET | `/admin/v1/recipe/{recipeId}` | `toc:recipe:detail` |
| 3 | 新增菜谱 | POST | `/admin/v1/recipe` | `toc:recipe:add` |
| 4 | 修改菜谱 | PUT | `/admin/v1/recipe` | `toc:recipe:edit` |
| 5 | 删除菜谱 | DELETE | `/admin/v1/recipe/{recipeId}` | `toc:recipe:delete` |
| 6 | 设置推荐排序 | PUT | `/admin/v1/recipe/{recipeId}/recommend` | `toc:recipe:edit` |

### 核心数据模型

#### 菜谱信息 (TocRecipeVO / TocRecipeDetailVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| recipeId | Long | 菜谱ID |
| title | String | 菜谱标题 |
| coverImage | String | 封面图片URL |
| categoryId | Long | 分类ID |
| difficulty | Integer | 难度等级 |
| cookTime | Integer | 烹饪时间(分钟) |
| servings | Integer | 份量 |
| isRecommend | Integer | 是否推荐(1是 0否) |
| recommendSort | Integer | 推荐排序 |
| status | Integer | 状态(1上架 0下架) |
| userId | Long | 发布用户ID |
| viewCount | Long | 浏览量 |
| likeCount | Long | 点赞数 |
| commentCount | Long | 评论数 |

#### 推荐设置 (TocRecipeRecommendDTO)

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| isRecommend | Integer | 是 | 是否推荐(1是 0否) |
| recommendSort | Integer | 否 | 推荐排序 |

### 使用场景

- 管理C端用户发布的菜谱内容
- 设置推荐菜谱及排序(首页推荐位)
- 审核菜谱内容(上架/下架)

---

## 四、评论管理 (`/admin/v1/comment`)

### 接口列表

| 序号 | 接口名称 | 方法 | 路径 | 权限标识 |
|------|----------|------|------|----------|
| 1 | 分页查询评论 | GET | `/admin/v1/comment/page` | `toc:comment:list` |
| 2 | 查询评论详情 | GET | `/admin/v1/comment/{commentId}` | `toc:comment:detail` |
| 3 | 删除评论 | DELETE | `/admin/v1/comment/{commentId}` | `toc:comment:delete` |

### 核心数据模型

#### 评论信息 (TocCommentVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| commentId | Long | 评论ID |
| recipeId | Long | 菜谱ID |
| userId | Long | 评论用户ID |
| username | String | 用户名 |
| userAvatar | String | 用户头像 |
| content | String | 评论内容 |
| images | List\<String\> | 评论图片列表 |
| likeCount | Long | 点赞数 |
| status | Integer | 状态(1正常 0隐藏) |
| createTime | String | 评论时间 |

### 使用场景

- 审核用户发布的评论
- 删除违规评论
- 查看菜谱下的用户互动情况

---

## 五、C端用户管理 (`/admin/v1/toc-user`)

### 接口列表

| 序号 | 接口名称 | 方法 | 路径 | 权限标识 |
|------|----------|------|------|----------|
| 1 | 分页查询C端用户 | GET | `/admin/v1/toc-user/page` | `toc:user:list` |
| 2 | 查询C端用户详情 | GET | `/admin/v1/toc-user/{userId}` | `toc:user:detail` |
| 3 | 切换C端用户状态 | PUT | `/admin/v1/toc-user/{userId}/status` | `toc:user:edit` |

### 核心数据模型

#### C端用户信息 (TocUserVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| userId | Long | 用户ID |
| username | String | 用户名 |
| nickname | String | 昵称 |
| avatar | String | 头像 |
| phone | String | 手机号 |
| email | String | 邮箱 |
| status | Integer | 状态(1正常 0停用) |
| recipeCount | Long | 发布菜谱数 |
| likeCount | Long | 获得点赞数 |
| createTime | String | 注册时间 |

#### 用户状态切换 (TocUserStatusDTO)

| 字段名 | 类型 | 必填 | 说明 |
|--------|------|------|------|
| status | Integer | 是 | 状态(1正常 0停用) |

### 使用场景

- 管理C端注册用户
- 查看用户活跃度统计(菜谱数、点赞数)
- 停用违规用户账号

---

## 权限标识符汇总

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

所有接口需要在请求头中携带 JWT Token：

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

- **状态字段**: 1表示正常/启用/上架, 0表示停用/隐藏/下架
- **删除方式**: 采用逻辑删除
- **时间格式**: `yyyy-MM-dd HH:mm:ss`

---

## 业务流程示例

### 1. 新增菜谱并设置为推荐

```
1. POST /admin/v1/upload - 上传菜谱封面图片,获取URL
2. POST /admin/v1/recipe - 新增菜谱,传入封面URL
3. PUT /admin/v1/recipe/{recipeId}/recommend - 设置为推荐并设置排序
```

### 2. 用户发布菜谱后被停用

```
1. GET /admin/v1/toc-user/page - 查看用户列表
2. GET /admin/v1/toc-user/{userId} - 查看用户详情和统计
3. GET /admin/v1/recipe/page?userId={userId} - 查看用户发布的菜谱
4. PUT /admin/v1/toc-user/{userId}/status - 停用用户账号(status=0)
```

### 3. 管理首页轮播图

```
1. POST /admin/v1/upload - 上传轮播图图片
2. POST /admin/v1/banner - 新增轮播图,设置跳转目标和排序
3. GET /admin/v1/banner/page - 查看轮播图列表
4. PUT /admin/v1/banner - 修改轮播图信息
```

---

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 删除操作均采用逻辑删除
3. 图片类字段(如coverImage, avatar)需要先调用文件上传接口获取URL
4. 推荐排序数字越小,显示位置越靠前
5. 评论和菜谱支持图片列表,图片以JSON数组形式存储
6. C端用户状态为停用时,用户无法登录和发布内容
7. 分类管理采用父子两级结构,父分类下可包含多个子分类

---

## 相关文档

- [后台管理API文档索引](./后台管理API文档索引.md) - 查看所有模块的完整索引
- [认证管理API文档](./认证管理API文档.md) - 登录获取Token
- [文件上传API文档](./文件上传API文档.md) - 上传图片文件
