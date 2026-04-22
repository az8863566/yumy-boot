package com.de.food.admin.converter;

import com.de.food.admin.dto.SysRoleCreateDTO;
import com.de.food.admin.dto.SysRoleUpdateDTO;
import com.de.food.business.entity.SysRole;
import com.de.food.admin.vo.SysRoleVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 角色对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysRoleConverter {

    SysRole toEntity(SysRoleCreateDTO dto);

    void updateEntity(SysRoleUpdateDTO dto, @MappingTarget SysRole entity);

    SysRoleVO toVO(SysRole entity);
}
