package com.de.food.toc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.toc.entity.TocUserLike;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户点赞 Mapper
 */
@Mapper
public interface TocUserLikeMapper extends BaseMapper<TocUserLike> {
}
