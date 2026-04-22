package com.de.food.toc.converter;

import com.de.food.toc.dto.TocAuthRegisterDTO;
import com.de.food.toc.dto.TocUserUpdateDTO;
import com.de.food.business.entity.TocUser;
import com.de.food.toc.vo.TocUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * C端用户对象转换器
 */
@Mapper(componentModel = "spring")
public interface TocUserConverter {

    TocUser toEntity(TocAuthRegisterDTO dto);

    void updateEntity(TocUserUpdateDTO dto, @MappingTarget TocUser entity);

    TocUserVO toVO(TocUser entity);
}
