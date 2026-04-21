package com.de.food.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.admin.entity.SysRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色菜单关联 Mapper
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu> {
}
