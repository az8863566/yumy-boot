package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.framework.util.SecurityUtils;
import com.de.food.toc.converter.TocRecipeConverter;
import com.de.food.business.entity.TocRecipe;
import com.de.food.business.entity.TocUserFavorite;
import com.de.food.business.entity.TocUserLike;
import com.de.food.business.mapper.TocRecipeMapper;
import com.de.food.business.mapper.TocUserFavoriteMapper;
import com.de.food.business.mapper.TocUserLikeMapper;
import com.de.food.toc.service.TocUserInteractionService;
import com.de.food.toc.vo.TocFavoriteVO;
import com.de.food.toc.vo.TocLikeVO;
import com.de.food.toc.vo.TocRecipeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 用户交互 Service 实现（点赞、收藏）
 */
@Slf4j
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

        boolean liked;
        TocUserLike existing = tocUserLikeMapper.selectOne(new LambdaQueryWrapper<TocUserLike>()
                .eq(TocUserLike::getUserId, userId)
                .eq(TocUserLike::getRecipeId, recipeId));

        if (existing != null) {
            // 已点赞 → 物理删除（取消点赞）
            tocUserLikeMapper.deleteById(existing.getLikeId());
            // 原子更新：likes - 1
            tocRecipeMapper.update(null, new LambdaUpdateWrapper<TocRecipe>()
                    .eq(TocRecipe::getRecipeId, recipeId)
                    .setSql("likes = GREATEST(likes - 1, 0)"));
            liked = false;
        } else {
            // 未点赞 → 新增，依赖唯一约束兜底并发
            TocUserLike like = new TocUserLike();
            like.setUserId(userId);
            like.setRecipeId(recipeId);
            try {
                tocUserLikeMapper.insert(like);
            } catch (DuplicateKeyException e) {
                // 并发场景：另一请求已插入，视为已点赞
                log.info("点赞并发冲突, userId={}, recipeId={}", userId, recipeId);
            }
            // 原子更新：likes + 1
            tocRecipeMapper.update(null, new LambdaUpdateWrapper<TocRecipe>()
                    .eq(TocRecipe::getRecipeId, recipeId)
                    .setSql("likes = likes + 1"));
            liked = true;
        }

        // 查询最新 likes 数
        int currentLikes = tocRecipeMapper.selectById(recipeId).getLikes();
        TocLikeVO vo = new TocLikeVO();
        vo.setLiked(liked);
        vo.setLikes(currentLikes);
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

        boolean favorited;
        TocUserFavorite existing = tocUserFavoriteMapper.selectOne(new LambdaQueryWrapper<TocUserFavorite>()
                .eq(TocUserFavorite::getUserId, userId)
                .eq(TocUserFavorite::getRecipeId, recipeId));

        if (existing != null) {
            // 已收藏 → 物理删除（取消收藏）
            tocUserFavoriteMapper.deleteById(existing.getFavoriteId());
            favorited = false;
        } else {
            // 未收藏 → 新增，依赖唯一约束兜底并发
            TocUserFavorite favorite = new TocUserFavorite();
            favorite.setUserId(userId);
            favorite.setRecipeId(recipeId);
            try {
                tocUserFavoriteMapper.insert(favorite);
            } catch (DuplicateKeyException e) {
                // 并发场景：另一请求已插入，视为已收藏
                log.info("收藏并发冲突, userId={}, recipeId={}", userId, recipeId);
            }
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

        // 批量查询菜谱，避免 N+1 问题
        List<Long> recipeIds = favResult.getRecords().stream()
                .map(TocUserFavorite::getRecipeId)
                .distinct()
                .toList();
        Map<Long, TocRecipe> recipeMap = recipeIds.isEmpty() ? Map.of()
                : tocRecipeMapper.selectBatchIds(recipeIds).stream()
                .collect(Collectors.toMap(TocRecipe::getRecipeId, Function.identity()));

        return favResult.convert(fav -> {
            TocRecipe r = recipeMap.get(fav.getRecipeId());
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
