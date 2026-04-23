package com.de.food.admin.converter;

import com.de.food.admin.dto.TocRecipeCreateDTO;
import com.de.food.admin.dto.TocRecipeUpdateDTO;
import com.de.food.admin.vo.TocRecipeDetailVO;
import com.de.food.admin.vo.TocRecipeVO;
import com.de.food.business.entity.TocRecipe;
import com.de.food.business.entity.TocRecipeIngredient;
import com.de.food.business.entity.TocRecipeStep;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 菜谱对象转换器
 */
@Mapper(componentModel = "spring")
public interface AdminTocRecipeConverter {

    @Mapping(target = "likes", ignore = true)
    TocRecipe toEntity(TocRecipeCreateDTO dto);

    @Mapping(target = "likes", ignore = true)
    void updateEntity(TocRecipeUpdateDTO dto, @MappingTarget TocRecipe entity);

    TocRecipeVO toVO(TocRecipe entity);

    TocRecipeDetailVO toDetailVO(TocRecipe entity);

    TocRecipeIngredient toIngredientEntity(TocRecipeCreateDTO.IngredientItem item);

    List<TocRecipeIngredient> toIngredientEntityList(List<TocRecipeCreateDTO.IngredientItem> items);

    TocRecipeStep toStepEntity(TocRecipeCreateDTO.StepItem item);

    List<TocRecipeStep> toStepEntityList(List<TocRecipeCreateDTO.StepItem> items);

    TocRecipeDetailVO.IngredientVO toIngredientVO(TocRecipeIngredient entity);

    List<TocRecipeDetailVO.IngredientVO> toIngredientVOList(List<TocRecipeIngredient> entities);

    TocRecipeDetailVO.StepVO toStepVO(TocRecipeStep entity);

    List<TocRecipeDetailVO.StepVO> toStepVOList(List<TocRecipeStep> entities);
}
