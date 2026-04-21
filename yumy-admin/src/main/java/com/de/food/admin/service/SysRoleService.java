package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysRoleCreateDTO;
import com.de.food.admin.dto.SysRoleQueryDTO;
import com.de.food.admin.dto.SysRoleUpdateDTO;
import com.de.food.admin.entity.SysRole;
import com.de.food.admin.vo.SysRoleVO;

import java.util.List;

/**
 * 角色 Service
 */
public interface SysRoleService extends IService<SysRole> {

    /**
     * 分页查询角色
     */
    IPage<SysRoleVO> page(SysRoleQueryDTO queryDTO);

    /**
     * 根据ID查询角色
     */
    SysRoleVO getDetail(Long roleId);

    /**
     * 新增角色
     */
    void createRole(SysRoleCreateDTO dto);

    /**
     * 修改角色
     */
    void updateRole(SysRoleUpdateDTO dto);

    /**
     * 删除角色
     */
    void deleteRole(Long roleId);

    /**
     * 查询角色的菜单ID列表
     */
    List<Long> getMenuIdsByRoleId(Long roleId);

    /**
     * 分配角色菜单
     */
    void assignMenus(Long roleId, List<Long> menuIds);
}
