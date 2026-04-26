package com.de.food.framework.security;

/**
 * 安全相关常量
 */
public final class SecurityConstants {

    private SecurityConstants() {}

    /**
     * 超级管理员角色标识
     * <p>
     * 拥有此角色的用户将自动绕过所有权限校验。
     */
    public static final String SUPER_ADMIN_ROLE = "ROLE_ADMIN";
}
