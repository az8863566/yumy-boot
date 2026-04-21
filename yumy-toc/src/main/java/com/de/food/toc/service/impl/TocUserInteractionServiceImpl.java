package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.framework.util.SecurityUtils;
import com.de.food.toc.converter.TocRecipeConverter;
import com.de.food.toc.entity.TocRecipe;
import com.de.food.toc.entity.TocUserFavorite;
import com.de.food.toc.entity.TocUserLike;
import com.de.food.toc.mapper.TocRecipeMapper;
import com.de.food.toc.mapper.TocUserFavoriteMapper;
import com.de.food.toc.mapper.TocUserLikeMapper;
import com.de.food.toc.service.TocUserInteractionService;
import com.de.food.toc.vo.TocFavoriteVO;
import com.de.food.toc.vo.TocLikeVO;
import com.de.food.toc.vo.TocRecipeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户交互 Service 实现（点赞、收藏）
 */
@Service
@RequiredArgsConstructor
public class TocUserInteractionServiceImpl implements TocUserInteractionService {

    private final TocUserLikeMapper tocUserLikeMapper;
    private final TocUserFavoriteMapper tocUserFavoriteMapper;
    private final TocRecipeMapper tocRecipeMapper;
    private final TocRecipeConverter tocRecipeConverter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TocLikeVO toggleLike(Long recipeId) {
        Long userId = SecurityUtils.requireUserId();

        // 校验菜谱存在
        TocRecipe recipe = tocRecipeMapper.selectById(recipeId);
        if (recipe == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }

        // 查询是否已点赞
        TocUserLike existing = tocUserLikeMapper.selectOne(new LambdaQueryWrapper<TocUserLike>()
                .eq(TocUserLike::getUserId, userId)
                .eq(TocUserLike::getRecipeId, recipeId));

        boolean liked;
        if (existing != null) {
            // 已点赞 → 取消（逻辑删除）
            tocUserLikeMapper.deleteById(existing.getLikeId());
            recipe.setLikes(Math.max(0, recipe.getLikes() - 1));
            liked = false;
        } else {
            // 未点赞 → 新增
            TocUserLike like = new TocUserLike();
            like.setUserId(userId);
            like.setRecipeId(recipeId);
            tocUserLikeMapper.insert(like);
            recipe.setLikes(recipe.getLikes() + 1);
            liked = true;
        }

        tocRecipeMapper.updateById(recipe);

        TocLikeVO vo = new TocLikeVO();
        vo.setLiked(liked);
        vo.setLikes(recipe.getLikes());
        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TocFavoriteVO toggleFavorite(Long recipeId) {
        Long userId = SecurityUtils.requireUserId();

        // 校验菜谱存在
        TocRecipe recipe = tocRecipeMapper.selectById(recipeId);
        if (recipe == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }

        // 查询是否已收藏
        TocUserFavorite existing = tocUserFavoriteMapper.selectOne(new LambdaQueryWrapper<TocUserFavorite>()
                .eq(TocUserFavorite::getUserId, userId)
                .eq(TocUserFavorite::getRecipeId, recipeId));

        boolean favorited;
        if (existing != null) {
            // 已收藏 → 取消（逻辑删除）
            tocUserFavoriteMapper.deleteById(existing.getFavoriteId());
            favorited = false;
        } else {
            // 未收藏 → 新增
            TocUserFavorite favorite = new TocUserFavorite();
            favorite.setUserId(userId);
            favorite.setRecipeId(recipeId);
            tocUserFavoriteMapper.insert(favorite);
            favorited = true;
        }

        TocFavoriteVO vo = new TocFavoriteVO();
        vo.setFavorited(favorited);
        return vo;
    }

    @Override
    public IPage<TocRecipeVO> getMyFavorites(Long pageNum, Long pageSize) {
        Long userId = SecurityUtils.requireUserId();

        // 查询用户收藏的菜谱ID
        Page<TocUserFavorite> favPage = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TocUserFavorite> favWrapper = new LambdaQueryWrapper<TocUserFavorite>()
                .eq(TocUserFavorite::getUserId, userId)
                .orderByDesc(TocUserFavorite::getCreateTime);
        IPage<TocUserFavorite> favResult = tocUserFavoriteMapper.selectPage(favPage, favWrapper);

        // 转换为菜谱VO分页
        return favResult.convert(fav -> {
            TocRecipe r = tocRecipeMapper.selectById(fav.getRecipeId());
            return r != null ? tocRecipeConverter.toVO(r) : null;
        });
    }

    @Override
    public List<Long> getMyLikedRecipeIds() {
        Long userId = SecurityUtils.requireUserId();
        List<TocUserLike> likes = tocUserLikeMapper.selectList(new LambdaQueryWrapper<TocUserLike>()
                .eq(TocUserLike::getUserId, userId)
                .select(TocUserLike::getRecipeId));
        return likes.stream().map(TocUserLike::getRecipeId).toList();
    }
}
