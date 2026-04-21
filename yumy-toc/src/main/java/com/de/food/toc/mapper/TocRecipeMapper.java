package com.de.food.toc.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.toc.entity.TocRecipe;
import org.apache.ibatis.annotations.Mapper;

/**
 * 菜谱 Mapper
 */
@Mapper
public interface TocRecipeMapper extends BaseMapper<TocRecipe> {
}
