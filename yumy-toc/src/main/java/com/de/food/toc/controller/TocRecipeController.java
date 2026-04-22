package com.de.food.toc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.common.result.Result;
import com.de.food.toc.dto.TocRecipeQueryDTO;
import com.de.food.toc.service.TocRecipeService;
import com.de.food.toc.vo.TocRecipeDetailVO;
import com.de.food.toc.vo.TocRecipeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 菜谱 Controller
 */
@RestController
@RequestMapping("/api/toc/v1/recipes")
@RequiredArgsConstructor
@Tag(name = "C端-菜谱")
public class TocRecipeController {

    private final TocRecipeService tocRecipeService;

    @Operation(summary = "获取菜谱列表")
    @GetMapping
    public Result<IPage<TocRecipeVO>> page(@Valid TocRecipeQueryDTO queryDTO) {
        return Result.ok(tocRecipeService.page(queryDTO));
    }

    @Operation(summary = "获取菜谱详情")
    @GetMapping("/{recipeId}")
    public Result<TocRecipeDetailVO> getDetail(@PathVariable Long recipeId) {
        return Result.ok(tocRecipeService.getDetail(recipeId));
    }

    @Operation(summary = "获取人气排行榜")
    @GetMapping("/top-ranked")
    public Result<List<TocRecipeVO>> getTopRanked(
            @RequestParam(defaultValue = "3") int limit) {
        return Result.ok(tocRecipeService.getTopRanked(limit));
    }

    @Operation(summary = "获取推荐菜谱")
    @GetMapping("/recommended")
    public Result<IPage<TocRecipeVO>> getRecommended(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize) {
        return Result.ok(tocRecipeService.getRecommended(page, pageSize));
    }

    @Operation(summary = "搜索菜谱")
    @GetMapping("/search")
    public Result<IPage<TocRecipeVO>> search(
            @RequestParam String q,
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize) {
        TocRecipeQueryDTO queryDTO = new TocRecipeQueryDTO();
        queryDTO.setKeyword(q);
        queryDTO.setPageNum(pageNum);
        queryDTO.setPageSize(pageSize);
        return Result.ok(tocRecipeService.page(queryDTO));
    }
}
