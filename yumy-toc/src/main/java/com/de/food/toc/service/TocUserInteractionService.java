package com.de.food.toc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.toc.vo.TocFavoriteVO;
import com.de.food.toc.vo.TocLikeVO;
import com.de.food.toc.vo.TocRecipeVO;

import java.util.List;

/**
 * 用户交互 Service（点赞、收藏）
 */
public interface TocUserInteractionService {

    /**
     * 点赞/取消点赞
     */
    TocLikeVO toggleLike(Long recipeId);

    /**
     * 收藏/取消收藏
     */
    TocFavoriteVO toggleFavorite(Long recipeId);

    /**
     * 获取我的收藏列表
     */
    IPage<TocRecipeVO> getMyFavorites(Long pageNum, Long pageSize);

    /**
     * 获取我的点赞菜谱ID列表
     */
    List<Long> getMyLikedRecipeIds();
}
