package com.de.food.admin.converter;

import com.de.food.admin.dto.TocParentCategoryCreateDTO;
import com.de.food.admin.dto.TocParentCategoryUpdateDTO;
import com.de.food.admin.dto.TocSubCategoryCreateDTO;
import com.de.food.admin.dto.TocSubCategoryUpdateDTO;
import com.de.food.admin.vo.TocCategoryTreeVO;
import com.de.food.business.entity.TocParentCategory;
import com.de.food.business.entity.TocSubCategory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * 分类对象转换器
 */
@Mapper(componentModel = "spring")
public interface AdminTocCategoryConverter {

    TocParentCategory toParentEntity(TocParentCategoryCreateDTO dto);

    void updateParentEntity(TocParentCategoryUpdateDTO dto, @MappingTarget TocParentCategory entity);

    @Mapping(target = "subCategories", ignore = true)
    TocCategoryTreeVO toParentVONoSub(TocParentCategory entity);

    TocSubCategory toSubEntity(TocSubCategoryCreateDTO dto);

    void updateSubEntity(TocSubCategoryUpdateDTO dto, @MappingTarget TocSubCategory entity);

    TocCategoryTreeVO.TocSubCategoryItemVO toSubVO(TocSubCategory entity);
}
