package com.de.food.toc.converter;

import com.de.food.toc.entity.TocRecipe;
import com.de.food.toc.entity.TocRecipeIngredient;
import com.de.food.toc.entity.TocRecipeStep;
import com.de.food.toc.vo.TocRecipeDetailVO;
import com.de.food.toc.vo.TocRecipeVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 菜谱对象转换器
 */
@Mapper(componentModel = "spring")
public interface TocRecipeConverter {

    TocRecipeVO toVO(TocRecipe entity);

    TocRecipeDetailVO toDetailVO(TocRecipe entity);

    TocRecipeDetailVO.IngredientVO toIngredientVO(TocRecipeIngredient entity);

    List<TocRecipeDetailVO.IngredientVO> toIngredientVOList(List<TocRecipeIngredient> entities);

    TocRecipeDetailVO.StepVO toStepVO(TocRecipeStep entity);

    List<TocRecipeDetailVO.StepVO> toStepVOList(List<TocRecipeStep> entities);
}
