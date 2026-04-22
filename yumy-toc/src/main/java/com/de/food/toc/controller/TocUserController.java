package com.de.food.toc.controller;

import com.de.food.common.result.Result;
import com.de.food.framework.util.SecurityUtils;
import com.de.food.toc.dto.TocUserUpdateDTO;
import com.de.food.toc.service.TocUserService;
import com.de.food.toc.vo.TocUserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * C端用户 Controller
 */
@RestController
@RequestMapping("/api/toc/v1/users")
@RequiredArgsConstructor
@Tag(name = "C端-用户信息")
public class TocUserController {

    private final TocUserService tocUserService;

    @Operation(summary = "获取当前用户信息")
    @GetMapping("/me")
    public Result<TocUserVO> getCurrentUser() {
        return Result.ok(tocUserService.getCurrentUser());
    }

    @Operation(summary = "更新当前用户信息")
    @PutMapping("/me")
    public Result<TocUserVO> updateUser(@Valid @RequestBody TocUserUpdateDTO dto) {
        return Result.ok(tocUserService.updateUser(dto));
    }
}
