package com.de.food.common.entity;

/**
 * 用户上下文信息接口
 * <p>
 * 定义从安全上下文中获取当前用户基本信息的能力，
 * 供 Session 链和 JWT 链的认证主体统一实现，
 * 使 {@code SecurityUtils} 可以跨认证方式使用。
 */
public interface UserInfo {

    /**
     * 获取用户 ID
     */
    Long getUserId();

    /**
     * 获取用户名
     */
    String getUsername();

    /**
     * 获取用户昵称
     */
    default String getNickname() {
        return null;
    }

    /**
     * 获取用户类型
     *
     * @return 用户类型枚举
     */
    UserType getUserType();
}
