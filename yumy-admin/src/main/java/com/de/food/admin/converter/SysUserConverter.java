package com.de.food.admin.converter;

import com.de.food.admin.dto.SysUserCreateDTO;
import com.de.food.admin.dto.SysUserUpdateDTO;
import com.de.food.admin.entity.SysUser;
import com.de.food.admin.vo.SysUserVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 用户对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysUserConverter {

    SysUser toEntity(SysUserCreateDTO dto);

    void updateEntity(SysUserUpdateDTO dto, @MappingTarget SysUser entity);

    SysUserVO toVO(SysUser entity);
}
