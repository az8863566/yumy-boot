package com.de.food.toc.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.toc.dto.TocCommentCreateDTO;
import com.de.food.toc.vo.TocCommentVO;

/**
 * 评论 Service
 */
public interface TocCommentService {

    /**
     * 获取菜谱评论列表
     */
    IPage<TocCommentVO> getCommentsByRecipeId(Long recipeId, Long pageNum, Long pageSize);

    /**
     * 发表评论
     */
    void createComment(Long recipeId, TocCommentCreateDTO dto);

    /**
     * 获取我的评论列表
     */
    IPage<TocCommentVO> getMyComments(Long pageNum, Long pageSize);
}
