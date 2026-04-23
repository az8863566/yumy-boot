package com.de.food.admin.converter;

import com.de.food.admin.vo.TocUserVO;
import com.de.food.business.entity.TocUser;
import org.mapstruct.Mapper;

/**
 * C端用户对象转换器
 */
@Mapper(componentModel = "spring")
public interface AdminTocUserConverter {

    TocUserVO toVO(TocUser entity);
}
