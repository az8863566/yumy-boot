package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysUserCreateDTO;
import com.de.food.admin.dto.SysUserQueryDTO;
import com.de.food.admin.dto.SysUserUpdateDTO;
import com.de.food.business.entity.SysUser;
import com.de.food.admin.vo.SysUserVO;

import java.util.List;

/**
 * 用户 Service
 */
public interface SysUserService extends IService<SysUser> {

    /**
     * 分页查询用户
     */
    IPage<SysUserVO> page(SysUserQueryDTO queryDTO);

    /**
     * 根据ID查询用户
     */
    SysUserVO getDetail(Long userId);

    /**
     * 新增用户
     */
    void createUser(SysUserCreateDTO dto);

    /**
     * 修改用户
     */
    void updateUser(SysUserUpdateDTO dto);

    /**
     * 删除用户
     */
    void deleteUser(Long userId);

    /**
     * 根据用户名查询
     */
    SysUser getByUsername(String username);

    /**
     * 查询用户的角色编码列表
     */
    List<String> getRoleCodesByUserId(Long userId);

    /**
     * 查询用户的权限标识列表
     */
    List<String> getPermissionsByUserId(Long userId);
}
