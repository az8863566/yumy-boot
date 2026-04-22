package com.de.food.admin.converter;

import com.de.food.admin.dto.SysConfigCreateDTO;
import com.de.food.admin.dto.SysConfigUpdateDTO;
import com.de.food.business.entity.SysConfig;
import com.de.food.admin.vo.SysConfigVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 参数配置对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysConfigConverter {

    SysConfig toEntity(SysConfigCreateDTO dto);

    void updateEntity(SysConfigUpdateDTO dto, @MappingTarget SysConfig entity);

    SysConfigVO toVO(SysConfig entity);
}
