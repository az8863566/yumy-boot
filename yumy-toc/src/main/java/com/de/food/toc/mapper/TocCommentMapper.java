package com.de.food.toc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.toc.entity.TocComment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论 Mapper
 */
@Mapper
public interface TocCommentMapper extends BaseMapper<TocComment> {
}
