package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.TocCommentQueryDTO;
import com.de.food.admin.vo.TocCommentVO;
import com.de.food.business.entity.TocComment;

/**
 * 评论管理 Service
 */
public interface AdminTocCommentService extends IService<TocComment> {

    /**
     * 分页查询评论
     */
    IPage<TocCommentVO> page(TocCommentQueryDTO queryDTO);

    /**
     * 查询评论详情
     */
    TocCommentVO getDetail(Long commentId);

    /**
     * 删除评论
     */
    void deleteComment(Long commentId);
}
