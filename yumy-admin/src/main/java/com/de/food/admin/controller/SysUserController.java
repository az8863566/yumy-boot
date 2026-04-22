package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.SysUserCreateDTO;
import com.de.food.admin.dto.SysUserQueryDTO;
import com.de.food.admin.dto.SysUserUpdateDTO;
import com.de.food.admin.service.SysUserService;
import com.de.food.admin.vo.SysUserVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 用户管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/user")
@RequiredArgsConstructor
@Tag(name = "用户管理")
public class SysUserController {

    private final SysUserService sysUserService;

    @Operation(summary = "分页查询用户")
    @PreAuthorize("hasAuthority('system:user:list')")
    @GetMapping("/page")
    public Result<IPage<SysUserVO>> page(@Valid SysUserQueryDTO queryDTO) {
        return Result.ok(sysUserService.page(queryDTO));
    }

    @Operation(summary = "查询用户详情")
    @PreAuthorize("hasAuthority('system:user:detail')")
    @GetMapping("/{userId}")
    public Result<SysUserVO> getDetail(@PathVariable Long userId) {
        return Result.ok(sysUserService.getDetail(userId));
    }

    @Operation(summary = "新增用户")
    @PreAuthorize("hasAuthority('system:user:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysUserCreateDTO dto) {
        sysUserService.createUser(dto);
        return Result.ok();
    }

    @Operation(summary = "修改用户")
    @PreAuthorize("hasAuthority('system:user:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody SysUserUpdateDTO dto) {
        sysUserService.updateUser(dto);
        return Result.ok();
    }

    @Operation(summary = "删除用户")
    @PreAuthorize("hasAuthority('system:user:delete')")
    @DeleteMapping("/{userId}")
    public Result<Void> delete(@PathVariable Long userId) {
        sysUserService.deleteUser(userId);
        return Result.ok();
    }
}
