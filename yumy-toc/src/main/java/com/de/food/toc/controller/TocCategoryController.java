package com.de.food.toc.controller;

import com.de.food.common.result.Result;
import com.de.food.toc.service.TocCategoryService;
import com.de.food.toc.vo.TocCategoryVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 分类 Controller
 */
@RestController
@RequestMapping("/api/toc/categories")
@RequiredArgsConstructor
@Tag(name = "C端-分类")
public class TocCategoryController {

    private final TocCategoryService tocCategoryService;

    @Operation(summary = "获取分类列表（父分类+子分类树）")
    @GetMapping
    public Result<List<TocCategoryVO>> getCategoryTree() {
        return Result.ok(tocCategoryService.getCategoryTree());
    }
}
