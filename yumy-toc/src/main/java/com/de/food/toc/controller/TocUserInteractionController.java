package com.de.food.toc.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.common.result.Result;
import com.de.food.toc.service.TocCommentService;
import com.de.food.toc.service.TocUserInteractionService;
import com.de.food.toc.vo.TocCommentVO;
import com.de.food.toc.vo.TocFavoriteVO;
import com.de.food.toc.vo.TocLikeVO;
import com.de.food.toc.vo.TocRecipeVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户交互 Controller（点赞、收藏、我的记录）
 */
@RestController
@RequestMapping("/api/toc/v1/users/me")
@RequiredArgsConstructor
@Tag(name = "C端-用户交互")
public class TocUserInteractionController {

    private final TocUserInteractionService tocUserInteractionService;
    private final TocCommentService tocCommentService;

    @Operation(summary = "点赞/取消点赞")
    @PostMapping("/recipes/{recipeId}/like")
    public Result<TocLikeVO> toggleLike(@PathVariable Long recipeId) {
        return Result.ok(tocUserInteractionService.toggleLike(recipeId));
    }

    @Operation(summary = "收藏/取消收藏")
    @PostMapping("/recipes/{recipeId}/favorite")
    public Result<TocFavoriteVO> toggleFavorite(@PathVariable Long recipeId) {
        return Result.ok(tocUserInteractionService.toggleFavorite(recipeId));
    }

    @Operation(summary = "获取我的收藏列表")
    @GetMapping("/favorites")
    public Result<IPage<TocRecipeVO>> getMyFavorites(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize) {
        return Result.ok(tocUserInteractionService.getMyFavorites(pageNum, pageSize));
    }

    @Operation(summary = "获取我的评论列表")
    @GetMapping("/comments")
    public Result<IPage<TocCommentVO>> getMyComments(
            @RequestParam(defaultValue = "1") Long pageNum,
            @RequestParam(defaultValue = "10") Long pageSize) {
        return Result.ok(tocCommentService.getMyComments(pageNum, pageSize));
    }

    @Operation(summary = "获取我的点赞菜谱ID列表")
    @GetMapping("/likes")
    public Result<List<Long>> getMyLikedRecipeIds() {
        return Result.ok(tocUserInteractionService.getMyLikedRecipeIds());
    }
}
