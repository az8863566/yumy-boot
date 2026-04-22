package com.de.food.toc.converter;

import com.de.food.toc.dto.TocCommentCreateDTO;
import com.de.food.business.entity.TocComment;
import com.de.food.toc.vo.TocCommentVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * 评论对象转换器
 */
@Mapper(componentModel = "spring")
public interface TocCommentConverter {

    @Mapping(target = "commentId", ignore = true)
    @Mapping(target = "userId", ignore = true)
    @Mapping(target = "recipeId", ignore = true)
    TocComment toEntity(TocCommentCreateDTO dto);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "avatar", ignore = true)
    TocCommentVO toVO(TocComment entity);
}
