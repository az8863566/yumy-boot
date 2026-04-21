package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.SysRoleCreateDTO;
import com.de.food.admin.dto.SysRoleQueryDTO;
import com.de.food.admin.dto.SysRoleUpdateDTO;
import com.de.food.admin.service.SysRoleService;
import com.de.food.admin.vo.SysRoleVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色管理 Controller
 */
@RestController
@RequestMapping("/admin/role")
@RequiredArgsConstructor
@Tag(name = "角色管理")
public class SysRoleController {

    private final SysRoleService sysRoleService;

    @Operation(summary = "分页查询角色")
    @PreAuthorize("hasAuthority('system:role:list')")
    @GetMapping("/page")
    public Result<IPage<SysRoleVO>> page(SysRoleQueryDTO queryDTO) {
        return Result.ok(sysRoleService.page(queryDTO));
    }

    @Operation(summary = "查询角色详情")
    @PreAuthorize("hasAuthority('system:role:detail')")
    @GetMapping("/{roleId}")
    public Result<SysRoleVO> getDetail(@PathVariable Long roleId) {
        return Result.ok(sysRoleService.getDetail(roleId));
    }

    @Operation(summary = "新增角色")
    @PreAuthorize("hasAuthority('system:role:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysRoleCreateDTO dto) {
        sysRoleService.createRole(dto);
        return Result.ok();
    }

    @Operation(summary = "修改角色")
    @PreAuthorize("hasAuthority('system:role:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody SysRoleUpdateDTO dto) {
        sysRoleService.updateRole(dto);
        return Result.ok();
    }

    @Operation(summary = "删除角色")
    @PreAuthorize("hasAuthority('system:role:delete')")
    @DeleteMapping("/{roleId}")
    public Result<Void> delete(@PathVariable Long roleId) {
        sysRoleService.deleteRole(roleId);
        return Result.ok();
    }

    @Operation(summary = "查询角色菜单ID列表")
    @PreAuthorize("hasAuthority('system:role:queryMenus')")
    @GetMapping("/{roleId}/menus")
    public Result<List<Long>> getMenuIds(@PathVariable Long roleId) {
        return Result.ok(sysRoleService.getMenuIdsByRoleId(roleId));
    }

    @Operation(summary = "分配角色菜单")
    @PreAuthorize("hasAuthority('system:role:assignMenus')")
    @PostMapping("/{roleId}/menus")
    public Result<Void> assignMenus(@PathVariable Long roleId, @RequestBody List<Long> menuIds) {
        sysRoleService.assignMenus(roleId, menuIds);
        return Result.ok();
    }
}
