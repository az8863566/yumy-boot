package com.de.food.framework.security;

import com.de.food.common.entity.UserInfo;
import com.de.food.common.entity.UserType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * JWT 认证用户信息
 * <p>
 * 从 JWT Token 中解析出的用户信息，作为 JWT 链的 Authentication principal。
 * 实现 {@link UserInfo} 接口，使 {@code SecurityUtils} 可跨认证方式使用。
 */
@Getter
@AllArgsConstructor
public class JwtUserInfo implements UserInfo {

    private final Long userId;
    private final String username;
    private final String nickname;
    private final UserType userType;
}
