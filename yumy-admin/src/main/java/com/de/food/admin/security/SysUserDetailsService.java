package com.de.food.admin.security;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.business.entity.SysUser;
import com.de.food.business.mapper.SysUserMapper;
import com.de.food.admin.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户认证服务
 * <p>
 * 加载用户信息并构建认证主体，供 Session 链和 JWT 链共用。
 */
@Service
@RequiredArgsConstructor
public class SysUserDetailsService implements UserDetailsService {

    private final SysUserMapper sysUserMapper;
    private final SysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser user = sysUserMapper.selectOne(
                new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
        if (user == null) {
            throw new UsernameNotFoundException("用户不存在: " + username);
        }

        // 查询角色与权限
        List<String> roleCodes = sysUserService.getRoleCodesByUserId(user.getUserId());
        List<String> permissions = sysUserService.getPermissionsByUserId(user.getUserId());

        // 合并角色 + 权限为 GrantedAuthority
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roleCodes.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);
        permissions.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);

        return new SysUserDetails(
                user.getUserId(), user.getUsername(), user.getPassword(),
                user.getNickname(), user.getStatus(), authorities);
    }
}
