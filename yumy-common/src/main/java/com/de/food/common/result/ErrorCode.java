package com.de.food.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 全局错误码定义
 */
@Getter
@AllArgsConstructor
public enum ErrorCode {

    // 通用错误
    SUCCESS(0, "成功"),
    FAIL(-1, "失败"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未认证"),
    FORBIDDEN(403, "无权限"),
    NOT_FOUND(404, "资源不存在"),
    INTERNAL_ERROR(500, "系统内部错误"),

    // 业务错误（1000 起）
    TOKEN_EXPIRED(1001, "Token已过期"),
    TOKEN_INVALID(1002, "Token无效"),
    USER_NOT_FOUND(1003, "用户不存在"),
    USER_PASSWORD_ERROR(1004, "密码错误"),
    USER_DISABLED(1005, "用户已被禁用"),

    // ToC 业务错误（2000 起）
    TOC_USER_EXISTS(2001, "用户名已存在"),
    TOC_RECIPE_NOT_FOUND(2002, "菜谱不存在"),
    TOC_ALREADY_LIKED(2003, "已点赞"),
    TOC_NOT_LIKED(2004, "未点赞"),
    TOC_ALREADY_FAVORITED(2005, "已收藏"),
    TOC_NOT_FAVORITED(2006, "未收藏");

    private final int code;
    private final String msg;
}
