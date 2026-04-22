package com.de.food.admin.converter;

import com.de.food.admin.dto.SysMenuCreateDTO;
import com.de.food.admin.dto.SysMenuUpdateDTO;
import com.de.food.business.entity.SysMenu;
import com.de.food.admin.vo.SysMenuVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 菜单对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysMenuConverter {

    SysMenu toEntity(SysMenuCreateDTO dto);

    void updateEntity(SysMenuUpdateDTO dto, @MappingTarget SysMenu entity);

    SysMenuVO toVO(SysMenu entity);
}
