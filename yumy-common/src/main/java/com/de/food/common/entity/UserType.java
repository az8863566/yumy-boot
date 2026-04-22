package com.de.food.common.entity;

import lombok.Getter;

/**
 * 用户类型枚举
 * <p>
 * 用于区分不同认证链的用户类型,确保安全上下文的清晰隔离。
 */
public enum UserType {

    /**
     * 后台管理员用户 (Session 认证链)
     */
    ADMIN(1, "后台管理员"),

    /**
     * C端普通用户 (JWT 认证链)
     */
    TOC_USER(2, "C端用户");

    /**
     * 类型编码
     */
    private final Integer code;

    /**
     * 类型描述
     */
    private final String description;

    UserType(Integer code, String description) {
        this.code = code;
        this.description = description;
    }

    public Integer getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    /**
     * 根据编码获取用户类型
     *
     * @param code 类型编码
     * @return 用户类型枚举
     * @throws IllegalArgumentException 编码不存在时抛出
     */
    public static UserType fromCode(Integer code) {
        if (code == null) {
            return null;
        }
        for (UserType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        throw new IllegalArgumentException("未知的用户类型编码: " + code);
    }
}
