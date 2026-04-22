package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.SysConfigCreateDTO;
import com.de.food.admin.dto.SysConfigQueryDTO;
import com.de.food.admin.dto.SysConfigUpdateDTO;
import com.de.food.admin.service.SysConfigService;
import com.de.food.admin.vo.SysConfigVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 参数配置管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/config")
@RequiredArgsConstructor
@Tag(name = "参数配置管理")
public class SysConfigController {

    private final SysConfigService sysConfigService;

    @Operation(summary = "分页查询参数配置")
    @PreAuthorize("hasAuthority('system:config:list')")
    @GetMapping("/page")
    public Result<IPage<SysConfigVO>> page(@Valid SysConfigQueryDTO queryDTO) {
        return Result.ok(sysConfigService.page(queryDTO));
    }

    @Operation(summary = "查询参数配置详情")
    @PreAuthorize("hasAuthority('system:config:detail')")
    @GetMapping("/{configId}")
    public Result<SysConfigVO> getDetail(@PathVariable Long configId) {
        return Result.ok(sysConfigService.getDetail(configId));
    }

    @Operation(summary = "根据键名查询参数值")
    @PreAuthorize("hasAuthority('system:config:queryByKey')")
    @GetMapping("/key/{configKey}")
    public Result<String> getByKey(@PathVariable String configKey) {
        return Result.ok(sysConfigService.getConfigValue(configKey));
    }

    @Operation(summary = "新增参数配置")
    @PreAuthorize("hasAuthority('system:config:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysConfigCreateDTO dto) {
        sysConfigService.createConfig(dto);
        return Result.ok();
    }

    @Operation(summary = "修改参数配置")
    @PreAuthorize("hasAuthority('system:config:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody SysConfigUpdateDTO dto) {
        sysConfigService.updateConfig(dto);
        return Result.ok();
    }

    @Operation(summary = "删除参数配置")
    @PreAuthorize("hasAuthority('system:config:delete')")
    @DeleteMapping("/{configId}")
    public Result<Void> delete(@PathVariable Long configId) {
        sysConfigService.deleteConfig(configId);
        return Result.ok();
    }
}
