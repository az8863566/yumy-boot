package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysUserConverter;
import com.de.food.admin.dto.SysUserCreateDTO;
import com.de.food.admin.dto.SysUserQueryDTO;
import com.de.food.admin.dto.SysUserUpdateDTO;
import com.de.food.admin.entity.SysMenu;
import com.de.food.admin.entity.SysRole;
import com.de.food.admin.entity.SysRoleMenu;
import com.de.food.admin.entity.SysUser;
import com.de.food.admin.entity.SysUserRole;
import com.de.food.admin.mapper.SysMenuMapper;
import com.de.food.admin.mapper.SysRoleMapper;
import com.de.food.admin.mapper.SysRoleMenuMapper;
import com.de.food.admin.mapper.SysUserMapper;
import com.de.food.admin.mapper.SysUserRoleMapper;
import com.de.food.admin.service.SysUserService;
import com.de.food.admin.vo.SysUserVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 用户 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserConverter sysUserConverter;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public IPage<SysUserVO> page(SysUserQueryDTO queryDTO) {
        Page<SysUser> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<SysUser>()
                .like(StringUtils.hasText(queryDTO.getUsername()), SysUser::getUsername, queryDTO.getUsername())
                .like(StringUtils.hasText(queryDTO.getNickname()), SysUser::getNickname, queryDTO.getNickname())
                .eq(StringUtils.hasText(queryDTO.getPhone()), SysUser::getPhone, queryDTO.getPhone())
                .eq(queryDTO.getStatus() != null, SysUser::getStatus, queryDTO.getStatus())
                .orderByDesc(SysUser::getCreateTime);

        IPage<SysUser> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(sysUserConverter::toVO);
    }

    @Override
    public SysUserVO getDetail(Long userId) {
        SysUser entity = baseMapper.selectById(userId);
        if (entity == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return sysUserConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createUser(SysUserCreateDTO dto) {
        // 校验用户名唯一
        SysUser exists = getByUsername(dto.getUsername());
        if (exists != null) {
            throw new BizException(ErrorCode.PARAM_ERROR, "用户名已存在");
        }
        SysUser entity = sysUserConverter.toEntity(dto);
        entity.setPassword(passwordEncoder.encode(dto.getPassword()));
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateUser(SysUserUpdateDTO dto) {
        SysUser entity = baseMapper.selectById(dto.getUserId());
        if (entity == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        sysUserConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        baseMapper.deleteById(userId);
        // 删除用户角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
    }

    @Override
    public SysUser getByUsername(String username) {
        return baseMapper.selectOne(new LambdaQueryWrapper<SysUser>()
                .eq(SysUser::getUsername, username));
    }

    @Override
    public List<String> getRoleCodesByUserId(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (userRoles.isEmpty()) {
            return List.of();
        }
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).toList();
        List<SysRole> roles = sysRoleMapper.selectBatchIds(roleIds);
        return roles.stream().map(SysRole::getRoleCode).toList();
    }

    @Override
    public List<String> getPermissionsByUserId(Long userId) {
        // 1. 查询用户角色关联
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (userRoles.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).toList();

        // 2. 查询角色菜单关联
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(
                new LambdaQueryWrapper<SysRoleMenu>().in(SysRoleMenu::getRoleId, roleIds));
        if (roleMenus.isEmpty()) {
            return new ArrayList<>();
        }

        List<Long> menuIds = roleMenus.stream().map(SysRoleMenu::getMenuId).distinct().toList();

        // 3. 查询菜单权限标识
        List<SysMenu> menus = sysMenuMapper.selectBatchIds(menuIds);
        return menus.stream()
                .map(SysMenu::getPerms)
                .filter(StringUtils::hasText)
                .distinct()
                .toList();
    }
}
