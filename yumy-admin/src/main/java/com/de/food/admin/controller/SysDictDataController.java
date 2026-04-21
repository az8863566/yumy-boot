package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.SysDictDataCreateDTO;
import com.de.food.admin.dto.SysDictDataQueryDTO;
import com.de.food.admin.dto.SysDictDataUpdateDTO;
import com.de.food.admin.service.SysDictDataService;
import com.de.food.admin.vo.SysDictDataVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 字典数据管理 Controller
 */
@RestController
@RequestMapping("/admin/dict/data")
@RequiredArgsConstructor
@Tag(name = "字典数据管理")
public class SysDictDataController {

    private final SysDictDataService sysDictDataService;

    @Operation(summary = "分页查询字典数据")
    @PreAuthorize("hasAuthority('system:dict:data:list')")
    @GetMapping("/page")
    public Result<IPage<SysDictDataVO>> page(SysDictDataQueryDTO queryDTO) {
        return Result.ok(sysDictDataService.page(queryDTO));
    }

    @Operation(summary = "根据字典类型查询字典数据列表")
    @PreAuthorize("hasAuthority('system:dict:data:queryByType')")
    @GetMapping("/type/{dictType}")
    public Result<List<SysDictDataVO>> listByDictType(@PathVariable String dictType) {
        return Result.ok(sysDictDataService.listByDictType(dictType));
    }

    @Operation(summary = "查询字典数据详情")
    @PreAuthorize("hasAuthority('system:dict:data:detail')")
    @GetMapping("/{dictCode}")
    public Result<SysDictDataVO> getDetail(@PathVariable Long dictCode) {
        return Result.ok(sysDictDataService.getDetail(dictCode));
    }

    @Operation(summary = "新增字典数据")
    @PreAuthorize("hasAuthority('system:dict:data:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody SysDictDataCreateDTO dto) {
        sysDictDataService.createDictData(dto);
        return Result.ok();
    }

    @Operation(summary = "修改字典数据")
    @PreAuthorize("hasAuthority('system:dict:data:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody SysDictDataUpdateDTO dto) {
        sysDictDataService.updateDictData(dto);
        return Result.ok();
    }

    @Operation(summary = "删除字典数据")
    @PreAuthorize("hasAuthority('system:dict:data:delete')")
    @DeleteMapping("/{dictCode}")
    public Result<Void> delete(@PathVariable Long dictCode) {
        sysDictDataService.deleteDictData(dictCode);
        return Result.ok();
    }
}
