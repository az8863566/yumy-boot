package com.de.food.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysMenuCreateDTO;
import com.de.food.admin.dto.SysMenuQueryDTO;
import com.de.food.admin.dto.SysMenuUpdateDTO;
import com.de.food.business.entity.SysMenu;
import com.de.food.admin.vo.SysMenuVO;

import java.util.List;

/**
 * 菜单 Service
 */
public interface SysMenuService extends IService<SysMenu> {

    /**
     * 查询菜单树
     */
    List<SysMenuVO> tree(SysMenuQueryDTO queryDTO);

    /**
     * 根据ID查询菜单
     */
    SysMenuVO getDetail(Long menuId);

    /**
     * 新增菜单
     */
    void createMenu(SysMenuCreateDTO dto);

    /**
     * 修改菜单
     */
    void updateMenu(SysMenuUpdateDTO dto);

    /**
     * 删除菜单
     */
    void deleteMenu(Long menuId);
}
