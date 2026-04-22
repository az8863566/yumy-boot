package com.de.food.toc.controller;

import com.de.food.common.result.Result;
import com.de.food.toc.dto.TocAuthLoginDTO;
import com.de.food.toc.dto.TocAuthRegisterDTO;
import com.de.food.toc.service.TocAuthService;
import com.de.food.toc.vo.TocAuthVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * C端认证 Controller
 */
@RestController
@RequestMapping("/api/toc/v1/auth")
@RequiredArgsConstructor
@Tag(name = "C端-用户认证")
public class TocAuthController {

    private final TocAuthService tocAuthService;

    @Operation(summary = "用户注册")
    @PostMapping("/register")
    public Result<TocAuthVO> register(@Valid @RequestBody TocAuthRegisterDTO dto) {
        return Result.ok(tocAuthService.register(dto));
    }

    @Operation(summary = "用户登录")
    @PostMapping("/login")
    public Result<TocAuthVO> login(@Valid @RequestBody TocAuthLoginDTO dto) {
        return Result.ok(tocAuthService.login(dto));
    }
}
