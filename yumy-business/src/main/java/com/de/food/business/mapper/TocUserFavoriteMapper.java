package com.de.food.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.business.entity.TocUserFavorite;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户收藏 Mapper
 */
@Mapper
public interface TocUserFavoriteMapper extends BaseMapper<TocUserFavorite> {
}
