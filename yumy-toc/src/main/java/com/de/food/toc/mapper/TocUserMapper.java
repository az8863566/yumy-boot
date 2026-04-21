package com.de.food.toc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.toc.entity.TocUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * C端用户 Mapper
 */
@Mapper
public interface TocUserMapper extends BaseMapper<TocUser> {
}
