package com.de.food.toc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.toc.dto.TocRecipeQueryDTO;
import com.de.food.toc.vo.TocRecipeDetailVO;
import com.de.food.toc.vo.TocRecipeVO;

import java.util.List;

/**
 * 菜谱 Service
 */
public interface TocRecipeService {

    /**
     * 分页查询菜谱列表
     */
    IPage<TocRecipeVO> page(TocRecipeQueryDTO queryDTO);

    /**
     * 获取菜谱详情
     */
    TocRecipeDetailVO getDetail(Long recipeId);

    /**
     * 获取人气排行榜
     */
    List<TocRecipeVO> getTopRanked(int limit);

    /**
     * 获取推荐菜谱
     */
    IPage<TocRecipeVO> getRecommended(int pageNum, int pageSize);
}
