package com.de.food.admin.converter;

import com.de.food.admin.dto.SysDictDataCreateDTO;
import com.de.food.admin.dto.SysDictDataUpdateDTO;
import com.de.food.admin.entity.SysDictData;
import com.de.food.admin.vo.SysDictDataVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 字典数据对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysDictDataConverter {

    SysDictData toEntity(SysDictDataCreateDTO dto);

    void updateEntity(SysDictDataUpdateDTO dto, @MappingTarget SysDictData entity);

    SysDictDataVO toVO(SysDictData entity);
}
