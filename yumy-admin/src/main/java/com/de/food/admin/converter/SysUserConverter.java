package com.de.food.admin.converter;

import com.de.food.admin.dto.SysUserCreateDTO;
import com.de.food.admin.dto.SysUserUpdateDTO;
import com.de.food.business.entity.SysUser;
import com.de.food.admin.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * 用户对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysUserConverter {

    SysUser toEntity(SysUserCreateDTO dto);

    @Mapping(target = "username", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntity(SysUserUpdateDTO dto, @MappingTarget SysUser entity);

    SysUserVO toVO(SysUser entity);
}
