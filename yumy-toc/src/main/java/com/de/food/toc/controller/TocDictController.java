package com.de.food.toc.controller;

import com.de.food.common.result.Result;
import com.de.food.toc.service.TocDictService;
import com.de.food.toc.vo.TocDictVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 字典 Controller（C端）
 */
@RestController
@RequestMapping("/api/toc/v1/dict")
@RequiredArgsConstructor
@Tag(name = "C端-字典数据")
public class TocDictController {

    private final TocDictService tocDictService;

    @Operation(summary = "根据字典类型查询字典数据")
    @GetMapping("/type/{dictType}")
    public Result<List<TocDictVO>> listByDictType(@PathVariable String dictType) {
        return Result.ok(tocDictService.listByDictType(dictType));
    }
}
