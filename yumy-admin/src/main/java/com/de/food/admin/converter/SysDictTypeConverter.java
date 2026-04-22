package com.de.food.admin.converter;

import com.de.food.admin.dto.SysDictTypeCreateDTO;
import com.de.food.admin.dto.SysDictTypeUpdateDTO;
import com.de.food.business.entity.SysDictType;
import com.de.food.admin.vo.SysDictTypeVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 字典类型对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysDictTypeConverter {

    SysDictType toEntity(SysDictTypeCreateDTO dto);

    void updateEntity(SysDictTypeUpdateDTO dto, @MappingTarget SysDictType entity);

    SysDictTypeVO toVO(SysDictType entity);
}
