package com.de.food.admin.controller;

import com.de.food.admin.dto.SysMenuCreateDTO;
import com.de.food.admin.dto.SysMenuQueryDTO;
import com.de.food.admin.dto.SysMenuUpdateDTO;
import com.de.food.admin.service.SysMenuService;
import com.de.food.admin.vo.SysMenuVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜单管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/menu")
@RequiredArgsConstructor
@Tag(name = "菜单管理")
public class SysMenuController {

    private final SysMenuService sysMenuService;

    @Operation(summary = "查询菜单树")
    @PreAuthorize("hasAuthority('system:menu:list')")
    @GetMapping("/tree")
    public Result<List<SysMenuVO>> tree(SysMenuQueryDTO queryDTO) {
        return Result.ok(sysMenuService.tree(queryDTO));
    }

    @Operation(summary = "查询菜单详情")
    @PreAuthorize("hasAuthority('system:menu:detail')")
    @GetMapping("/{menuId}")
    public Result<SysMenuVO> getDetail(@PathVariable Long menuId) {
        return Result.ok(sysMenuService.getDetail(menuId));
    }

    @Operation(summary = "新增菜单")
    @PreAuthorize("hasAuthority('system:menu:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysMenuCreateDTO dto) {
        sysMenuService.createMenu(dto);
        return Result.ok();
    }

    @Operation(summary = "修改菜单")
    @PreAuthorize("hasAuthority('system:menu:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody SysMenuUpdateDTO dto) {
        sysMenuService.updateMenu(dto);
        return Result.ok();
    }

    @Operation(summary = "删除菜单")
    @PreAuthorize("hasAuthority('system:menu:delete')")
    @DeleteMapping("/{menuId}")
    public Result<Void> delete(@PathVariable Long menuId) {
        sysMenuService.deleteMenu(menuId);
        return Result.ok();
    }
}
