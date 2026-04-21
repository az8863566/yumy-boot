package com.de.food.toc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.toc.entity.TocUserFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏 Mapper
 */
@Mapper
public interface TocUserFavoriteMapper extends BaseMapper<TocUserFavorite> {
}
