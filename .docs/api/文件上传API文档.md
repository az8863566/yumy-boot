# 文件上传 API 文档

## 基础信息

- **模块**: 文件上传
- **基础路径**: `/admin/v1/upload`
- **认证方式**: Bearer Token (JWT)
- **权限要求**: 所有接口均需要相应的权限标识符

## 数据模型

### 文件上传响应 (UploadVO)

| 字段名 | 类型 | 说明 |
|--------|------|------|
| fileId | Long | 文件记录ID |
| url | String | 文件访问URL |
| originalName | String | 原始文件名 |
| storedName | String | 存储文件名 |
| size | Long | 文件大小(字节) |

---

## API 接口

### 1. 上传文件

**接口地址**: `POST /admin/v1/upload`

**权限标识**: `system:upload:add`

**Content-Type**: `multipart/form-data`

**请求参数**:

| 参数名 | 类型 | 位置 | 必填 | 说明 |
|--------|------|------|------|------|
| file | File | FormData | 是 | 文件对象 |

**请求示例** (使用 FormData):

```javascript
const formData = new FormData();
formData.append('file', fileInput.files[0]);

fetch('/admin/v1/upload', {
  method: 'POST',
  headers: {
    'Authorization': 'Bearer {token}'
  },
  body: formData
});
```

**响应示例**:

```json
{
  "code": 200,
  "msg": "操作成功",
  "data": {
    "fileId": 1,
    "url": "https://example.com/uploads/2026/04/26/avatar_123456.jpg",
    "originalName": "avatar.jpg",
    "storedName": "avatar_123456.jpg",
    "size": 102400
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
| 400 | 请求参数错误或文件格式不支持 |
| 401 | 未认证或认证失败 |
| 403 | 权限不足 |
| 413 | 文件过大 |
| 500 | 服务器内部错误 |

## 文件上传限制

### 支持的文件类型

根据系统配置，通常支持以下文件类型：

| 类型 | 扩展名 | MIME Type |
|------|--------|-----------|
| 图片 | .jpg, .jpeg, .png, .gif, .webp | image/* |
| 文档 | .pdf, .doc, .docx, .xls, .xlsx | application/* |
| 压缩包 | .zip, .rar, .7z | application/* |

### 文件大小限制

- **默认限制**: 根据 Spring Boot 配置而定 (通常 10MB)
- **修改方式**: 在 `application.yml` 中配置 `spring.servlet.multipart.max-file-size`

## 使用场景

1. **用户头像上传**: 用户管理模块上传头像图片
2. **轮播图上传**: 轮播图管理模块上传轮播图片
3. **菜谱图片上传**: 菜谱管理模块上传菜谱封面和步骤图片
4. **文件附件上传**: 各类业务模块上传附件

## 注意事项

1. 所有接口均需要在请求头中携带有效的 JWT Token
2. 必须使用 `multipart/form-data` 格式上传文件
3. 文件字段名必须为 `file`
4. 上传成功后会返回文件访问URL,可直接用于前端展示
5. 建议在前端进行文件大小和格式校验,减少无效请求
6. 存储文件名(storedName)通常使用时间戳或UUID避免重名
7. 文件存储路径和访问URL根据系统配置而定(本地存储或对象存储)
8. 大文件上传建议显示进度条,提升用户体验
