package com.de.food.admin.controller;

import com.de.food.admin.dto.AuthLoginDTO;
import com.de.food.admin.service.AuthService;
import com.de.food.admin.vo.AuthLoginVO;
import com.de.food.common.result.Result;
import com.de.food.framework.util.SecurityUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/v1/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理")
@Slf4j
public class AuthController {

    private final AuthService authService;

    @Operation(summary = "登录")
    @PostMapping("/login")
    public Result<AuthLoginVO> login(@Valid @RequestBody AuthLoginDTO dto) {
        return Result.ok(authService.login(dto));
    }

    /**
     * 登出
     * <p>
     * JWT 无状态认证下，服务端无法主动使 Token 失效。
     * 此接口仅记录审计日志，Token 将在过期后自然失效。
     * 前端应在调用此接口后清除本地存储的 Token。
     */
    @Operation(summary = "登出")
    @PostMapping("/logout")
    public Result<Void> logout() {
        Long userId = SecurityUtils.getUserId();
        String username = SecurityUtils.getUsername();
        log.info("用户登出, userId={}, username={}", userId, username);
        return Result.ok();
    }
}
