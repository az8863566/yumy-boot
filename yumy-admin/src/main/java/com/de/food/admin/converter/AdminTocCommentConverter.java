package com.de.food.admin.converter;

import com.de.food.admin.vo.TocCommentVO;
import com.de.food.business.entity.TocComment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 评论对象转换器
 */
@Mapper(componentModel = "spring")
public interface AdminTocCommentConverter {

    @Mapping(target = "recipeTitle", ignore = true)
    @Mapping(target = "username", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    TocCommentVO toVO(TocComment entity);
}
