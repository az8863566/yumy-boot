package com.de.food.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局错误码定义
 * <p>
 * 错误码与 HTTP 状态码独立：业务码用于 {@code Result.code} 字段返回前端，
 * HTTP 状态码由 {@code GlobalExceptionHandler} 在 response 上单独设置。
 * 编码规则：
 * <ul>
 *   <li>0       — 成功</li>
 *   <li>1xxx   — 通用错误（参数、认证、授权等）</li>
 *   <li>2xxx   — 系统管理业务错误</li>
 *   <li>3xxx   — ToC 业务错误</li>
 * </ul>
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 成功
    SUCCESS(0, "成功"),

    // 通用错误（1xxx）
    FAIL(1000, "失败"),
    PARAM_ERROR(1001, "参数错误"),
    UNAUTHORIZED(1002, "未认证"),
    FORBIDDEN(1003, "无权限"),
    NOT_FOUND(1004, "资源不存在"),
    INTERNAL_ERROR(1005, "系统内部错误"),
    METHOD_NOT_ALLOWED(1006, "请求方法不允许"),

    // 系统管理业务错误（2xxx）
    TOKEN_EXPIRED(2001, "Token已过期"),
    TOKEN_INVALID(2002, "Token无效"),
    USER_NOT_FOUND(2003, "用户不存在"),
    USER_PASSWORD_ERROR(2004, "密码错误"),
    USER_DISABLED(2005, "用户已被禁用"),
    UPLOAD_FILE_EMPTY(2006, "上传文件不能为空"),
    UPLOAD_FILE_TYPE_NOT_ALLOWED(2007, "不支持的文件类型"),
    UPLOAD_FILE_SIZE_EXCEEDED(2008, "上传文件大小超限"),

    // ToC 业务错误（3xxx）
    TOC_USER_EXISTS(3001, "用户名已存在"),
    TOC_RECIPE_NOT_FOUND(3002, "菜谱不存在"),
    TOC_ALREADY_LIKED(3003, "已点赞"),
    TOC_NOT_LIKED(3004, "未点赞"),
    TOC_ALREADY_FAVORITED(3005, "已收藏"),
    TOC_NOT_FAVORITED(3006, "未收藏"),
    TOC_HOME_CATEGORY_NOT_CONFIGURED(3007, "首页展示分类未配置"),
    TOC_BANNER_NOT_FOUND(3008, "轮播图不存在"),
    TOC_PARENT_CATEGORY_NOT_FOUND(3009, "父分类不存在"),
    TOC_SUB_CATEGORY_NOT_FOUND(3010, "子分类不存在"),
    TOC_USER_NOT_FOUND(3011, "C端用户不存在"),
    TOC_COMMENT_NOT_FOUND(3012, "评论不存在"),
    TOC_PARENT_CATEGORY_HAS_SUB(3013, "该父分类下存在子分类，无法删除"),
    TOC_SUB_CATEGORY_HAS_RECIPE(3014, "该子分类下存在菜谱，无法删除");

    private final int code;
    private final String msg;
}
