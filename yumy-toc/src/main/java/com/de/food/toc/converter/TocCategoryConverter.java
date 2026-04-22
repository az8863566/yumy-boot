package com.de.food.toc.converter;

import com.de.food.business.entity.TocParentCategory;
import com.de.food.business.entity.TocSubCategory;
import com.de.food.toc.vo.TocCategoryVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * 分类对象转换器
 */
@Mapper(componentModel = "spring")
public interface TocCategoryConverter {

    @Mapping(source = "categoryId", target = "id")
    TocCategoryVO toCategoryVO(TocParentCategory entity);

    @Mapping(source = "categoryId", target = "id")
    TocCategoryVO.TocSubCategoryVO toSubCategoryVO(TocSubCategory entity);

    List<TocCategoryVO.TocSubCategoryVO> toSubCategoryVOList(List<TocSubCategory> entities);
}
