package com.de.food.admin.service.impl;

import com.de.food.admin.dto.AuthLoginDTO;
import com.de.food.admin.security.SysUserDetails;
import com.de.food.admin.service.AuthService;
import com.de.food.admin.service.SysUserService;
import com.de.food.admin.vo.AuthLoginVO;
import com.de.food.business.entity.SysUser;
import com.de.food.common.entity.UserType;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.framework.util.JwtUtil;
import com.de.food.framework.security.SecurityConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 认证 Service 实现
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserService sysUserService;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();

    @Override
    public AuthLoginVO login(AuthLoginDTO dto) {
        SysUser user = sysUserService.getByUsername(dto.getUsername());
        if (user == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }

        if (!passwordEncoder.matches(dto.getPassword(), user.getPassword())) {
            throw new BizException(ErrorCode.USER_PASSWORD_ERROR);
        }

        if (user.getStatus() == null || user.getStatus() != 1) {
            throw new BizException(ErrorCode.USER_DISABLED);
        }

        List<String> roleCodes = sysUserService.getRoleCodesByUserId(user.getUserId());
        List<String> permissions = sysUserService.getPermissionsByUserId(user.getUserId());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        roleCodes.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);
        permissions.stream().map(SimpleGrantedAuthority::new).forEach(authorities::add);

        boolean isAdmin = "admin".equals(user.getUsername());
        if (isAdmin) {
            authorities.add(new SimpleGrantedAuthority(SecurityConstants.SUPER_ADMIN_ROLE));
            log.info("超管登录, 已添加 {} 权限标识, authorities={}", SecurityConstants.SUPER_ADMIN_ROLE, authorities);
        }

        SysUserDetails userDetails = new SysUserDetails(
                user.getUserId(), user.getUsername(), user.getPassword(),
                user.getNickname(), user.getStatus(), authorities);
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, user.getPassword(), authorities);
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        this.securityContextHolderStrategy.setContext(context);

        List<String> roleAndPermList = authorities.stream()
                .map(SimpleGrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        String token = jwtUtil.generateToken(
                user.getUserId(),
                user.getUsername(),
                user.getNickname() != null ? user.getNickname() : user.getUsername(),
                UserType.ADMIN,
                roleAndPermList
        );

        AuthLoginVO vo = new AuthLoginVO();
        vo.setToken(token);
        vo.setUsername(userDetails.getUsername());
        vo.setNickname(userDetails.getNickname());
        vo.setPermissions(permissions);
        vo.setAdmin(isAdmin);
        return vo;
    }
}
