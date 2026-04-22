package com.de.food.admin.service.impl;

import com.de.food.admin.dto.AuthLoginDTO;
import com.de.food.admin.security.SysUserDetails;
import com.de.food.admin.service.AuthService;
import com.de.food.admin.service.SysUserService;
import com.de.food.admin.vo.AuthLoginVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 认证 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SysUserService sysUserService;
    private final AuthenticationManager authenticationManager;
    private final SecurityContextHolderStrategy securityContextHolderStrategy =
            SecurityContextHolder.getContextHolderStrategy();

    @Override
    public AuthLoginVO login(AuthLoginDTO dto) {
        // 1. 使用 AuthenticationManager 校验凭据（触发 SysUserDetailsService 加载用户）
        UsernamePasswordAuthenticationToken authToken =
                new UsernamePasswordAuthenticationToken(dto.getUsername(), dto.getPassword());
        Authentication authentication = authenticationManager.authenticate(authToken);

        // 2. 校验用户状态
        SysUserDetails userDetails = (SysUserDetails) authentication.getPrincipal();
        if (!userDetails.isEnabled()) {
            throw new BizException(ErrorCode.USER_DISABLED);
        }

        // 3. 设置 SecurityContext，Spring Security 将自动创建 Session
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        this.securityContextHolderStrategy.setContext(context);

        // 4. 查询权限标识（前端菜单/按钮级鉴权使用）
        List<String> permissions = sysUserService.getPermissionsByUserId(userDetails.getUserId());

        // 5. 组装响应（Session 模式无需返回 Token）
        AuthLoginVO vo = new AuthLoginVO();
        vo.setUsername(userDetails.getUsername());
        vo.setNickname(userDetails.getNickname());
        vo.setPermissions(permissions);
        return vo;
    }
}
