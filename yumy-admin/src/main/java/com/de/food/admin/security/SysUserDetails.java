package com.de.food.admin.security;

import com.de.food.common.entity.UserInfo;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * 系统用户认证主体
 * <p>
 * 封装用户基本信息与权限，供 Session 和 JWT 两套过滤器链共用。
 * 实现 {@link UserInfo} 接口，使 {@code SecurityUtils} 可跨认证方式使用。
 */
@Getter
public class SysUserDetails implements UserDetails, UserInfo {

    private final Long userId;
    private final String username;
    private final String password;
    private final String nickname;
    private final Integer status;
    private final Collection<? extends GrantedAuthority> authorities;

    public SysUserDetails(Long userId, String username, String password,
                          String nickname, Integer status,
                          Collection<? extends GrantedAuthority> authorities) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.status = status;
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status != null && status == 1;
    }
}
