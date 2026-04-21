package com.de.food.framework.util;

import com.de.food.common.entity.UserInfo;
import com.de.food.common.entity.UserType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * 安全上下文工具类
 * <p>
 * 依托 Spring Security 上下文，在 Service、Mapper、工具类中
 * 随时获取当前登录用户的 ID 和基本信息。
 * <p>
 * 支持 Session 链和 JWT 链两种认证方式：
 * <ul>
 *   <li>Session 链：principal 为 {@code SysUserDetails}（实现 {@link UserInfo}）</li>
 *   <li>JWT 链：principal 为 {@code JwtUserInfo}（实现 {@link UserInfo}）</li>
 * </ul>
 */
public final class SecurityUtils {

    private SecurityUtils() {}

    /**
     * 获取当前用户上下文信息
     *
     * @return UserInfo，未登录时返回 null
     */
    public static UserInfo getUserInfo() {
        Authentication authentication = getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserInfo userInfo) {
            return userInfo;
        }
        return null;
    }

    /**
     * 获取当前登录用户 ID
     *
     * @return 用户 ID，未登录时返回 null
     */
    public static Long getUserId() {
        UserInfo userInfo = getUserInfo();
        return userInfo != null ? userInfo.getUserId() : null;
    }

    /**
     * 获取当前登录用户 ID，未登录时抛出异常
     *
     * @return 用户 ID
     * @throws IllegalStateException 未登录时抛出
     */
    public static Long requireUserId() {
        Long userId = getUserId();
        if (userId == null) {
            throw new IllegalStateException("当前未登录，无法获取用户ID");
        }
        return userId;
    }

    /**
     * 获取当前登录用户名
     *
     * @return 用户名，未登录时返回 null
     */
    public static String getUsername() {
        UserInfo userInfo = getUserInfo();
        return userInfo != null ? userInfo.getUsername() : null;
    }

    /**
     * 获取当前登录用户昵称
     *
     * @return 昵称，未登录时返回 null
     */
    public static String getNickname() {
        UserInfo userInfo = getUserInfo();
        return userInfo != null ? userInfo.getNickname() : null;
    }

    /**
     * 获取当前用户类型
     *
     * @return 用户类型，未登录时返回 null
     */
    public static UserType getUserType() {
        UserInfo userInfo = getUserInfo();
        return userInfo != null ? userInfo.getUserType() : null;
    }

    /**
     * 获取当前用户类型，未登录时抛出异常
     *
     * @return 用户类型
     * @throws IllegalStateException 未登录时抛出
     */
    public static UserType requireUserType() {
        UserType userType = getUserType();
        if (userType == null) {
            throw new IllegalStateException("当前未登录，无法获取用户类型");
        }
        return userType;
    }

    /**
     * 当前是否已认证（非匿名用户）
     *
     * @return true 已登录
     */
    public static boolean isAuthenticated() {
        Authentication authentication = getAuthentication();
        return authentication != null
                && authentication.isAuthenticated()
                && !(authentication.getPrincipal() instanceof String);
    }

    // ---------- 内部方法 ----------

    private static Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
