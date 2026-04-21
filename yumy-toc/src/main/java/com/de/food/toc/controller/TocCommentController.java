package com.de.food.toc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.common.result.Result;
import com.de.food.toc.dto.TocCommentCreateDTO;
import com.de.food.toc.service.TocCommentService;
import com.de.food.toc.vo.TocCommentVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 评论 Controller
 */
@RestController
@RequestMapping("/api/toc/recipes")
@RequiredArgsConstructor
@Tag(name = "C端-评论")
public class TocCommentController {

    private final TocCommentService tocCommentService;

    @Operation(summary = "获取菜谱评论列表")
    @GetMapping("/{recipeId}/comments")
    public Result<IPage<TocCommentVO>> getComments(
            @PathVariable Long recipeId,
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "20") Long pageSize) {
        return Result.ok(tocCommentService.getCommentsByRecipeId(recipeId, pageNum, pageSize));
    }

    @Operation(summary = "发表评论")
    @PostMapping("/{recipeId}/comments")
    public Result<Void> createComment(
            @PathVariable Long recipeId,
            @Valid @RequestBody TocCommentCreateDTO dto) {
        tocCommentService.createComment(recipeId, dto);
        return Result.ok();
    }
}
