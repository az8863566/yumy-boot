package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.SysDictTypeCreateDTO;
import com.de.food.admin.dto.SysDictTypeQueryDTO;
import com.de.food.admin.dto.SysDictTypeUpdateDTO;
import com.de.food.admin.service.SysDictTypeService;
import com.de.food.admin.vo.SysDictTypeVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 字典类型管理 Controller
 */
@RestController
@RequestMapping("/admin/dict/type")
@RequiredArgsConstructor
@Tag(name = "字典类型管理")
public class SysDictTypeController {

    private final SysDictTypeService sysDictTypeService;

    @Operation(summary = "分页查询字典类型")
    @PreAuthorize("hasAuthority('system:dict:type:list')")
    @GetMapping("/page")
    public Result<IPage<SysDictTypeVO>> page(SysDictTypeQueryDTO queryDTO) {
        return Result.ok(sysDictTypeService.page(queryDTO));
    }

    @Operation(summary = "查询字典类型详情")
    @PreAuthorize("hasAuthority('system:dict:type:detail')")
    @GetMapping("/{dictId}")
    public Result<SysDictTypeVO> getDetail(@PathVariable Long dictId) {
        return Result.ok(sysDictTypeService.getDetail(dictId));
    }

    @Operation(summary = "新增字典类型")
    @PreAuthorize("hasAuthority('system:dict:type:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysDictTypeCreateDTO dto) {
        sysDictTypeService.createDictType(dto);
        return Result.ok();
    }

    @Operation(summary = "修改字典类型")
    @PreAuthorize("hasAuthority('system:dict:type:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody SysDictTypeUpdateDTO dto) {
        sysDictTypeService.updateDictType(dto);
        return Result.ok();
    }

    @Operation(summary = "删除字典类型")
    @PreAuthorize("hasAuthority('system:dict:type:delete')")
    @DeleteMapping("/{dictId}")
    public Result<Void> delete(@PathVariable Long dictId) {
        sysDictTypeService.deleteDictType(dictId);
        return Result.ok();
    }
}
