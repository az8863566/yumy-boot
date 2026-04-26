package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.batch.MybatisBatch;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.MybatisBatchUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysUserConverter;
import com.de.food.admin.dto.SysUserCreateDTO;
import com.de.food.admin.dto.SysUserQueryDTO;
import com.de.food.admin.dto.SysUserUpdateDTO;
import com.de.food.admin.vo.SysRoleVO;
import com.de.food.admin.vo.SysUserVO;
import com.de.food.business.entity.SysMenu;
import com.de.food.business.entity.SysRole;
import com.de.food.business.entity.SysRoleMenu;
import com.de.food.business.entity.SysUser;
import com.de.food.business.entity.SysUserRole;
import com.de.food.business.mapper.SysMenuMapper;
import com.de.food.business.mapper.SysRoleMapper;
import com.de.food.business.mapper.SysRoleMenuMapper;
import com.de.food.business.mapper.SysUserMapper;
import com.de.food.business.mapper.SysUserRoleMapper;
import org.apache.ibatis.session.SqlSessionFactory;
import com.de.food.admin.service.SysUserService;
import com.de.food.admin.converter.SysRoleConverter;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.framework.util.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    private final SysUserConverter sysUserConverter;
    private final SysRoleConverter sysRoleConverter;
    private final SysUserRoleMapper sysUserRoleMapper;
    private final SysRoleMapper sysRoleMapper;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysMenuMapper sysMenuMapper;
    private final PasswordEncoder passwordEncoder;
    private final SqlSessionFactory sqlSessionFactory;

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
        IPage<SysUserVO> voPage = entityPage.convert(sysUserConverter::toVO);

        // 批量填充角色信息
        fillUserRoles(voPage.getRecords());
        return voPage;
    }

    @Override
    public SysUserVO getDetail(Long userId) {
        SysUser entity = baseMapper.selectById(userId);
        if (entity == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        SysUserVO vo = sysUserConverter.toVO(entity);
        vo.setRoles(getRolesByUserId(userId));
        return vo;
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

        // 保存用户角色关联
        if (dto.getRoleIds() != null) {
            saveUserRoles(entity.getUserId(), dto.getRoleIds());
        }
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

        // 更新用户角色关联
        if (dto.getRoleIds() != null) {
            resetUserRoles(dto.getUserId(), dto.getRoleIds());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long userId) {
        // 禁止删除自己
        Long currentUserId = SecurityUtils.getUserId();
        if (currentUserId != null && currentUserId.equals(userId)) {
            throw new BizException(ErrorCode.PARAM_ERROR, "不允许删除当前登录用户");
        }
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

    /**
     * 重置用户角色关联（先删后插）
     */
    private void resetUserRoles(Long userId, List<Long> roleIds) {
        // 先删除原有角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getUserId, userId));
        // 再保存新的角色关联
        saveUserRoles(userId, roleIds);
    }

    /**
     * 批量保存用户角色关联
     */
    private void saveUserRoles(Long userId, List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return;
        }
        List<SysUserRole> userRoles = roleIds.stream().map(roleId -> {
            SysUserRole userRole = new SysUserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            return userRole;
        }).toList();
        MybatisBatch.Method<SysUserRole> method = new MybatisBatch.Method<>(SysUserRoleMapper.class);
        MybatisBatchUtils.execute(sqlSessionFactory, userRoles, method.insert());
    }

    /**
     * 批量填充用户角色信息
     */
    private void fillUserRoles(List<SysUserVO> users) {
        if (users == null || users.isEmpty()) {
            return;
        }
        List<Long> userIds = users.stream().map(u -> Long.valueOf(u.getUserId())).toList();

        // 批量查询用户角色关联
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().in(SysUserRole::getUserId, userIds));
        if (userRoles.isEmpty()) {
            return;
        }

        // 按用户ID分组
        Map<Long, List<Long>> userRoleMap = userRoles.stream()
                .collect(Collectors.groupingBy(SysUserRole::getUserId,
                        Collectors.mapping(SysUserRole::getRoleId, Collectors.toList())));

        // 批量查询所有角色
        List<Long> allRoleIds = userRoles.stream().map(SysUserRole::getRoleId).distinct().toList();
        List<SysRole> roles = sysRoleMapper.selectBatchIds(allRoleIds);
        Map<Long, SysRoleVO> roleVOMap = roles.stream()
                .collect(Collectors.toMap(SysRole::getRoleId, sysRoleConverter::toVO));

        // 填充角色信息
        for (SysUserVO user : users) {
            List<Long> roleIds = userRoleMap.getOrDefault(Long.valueOf(user.getUserId()), Collections.emptyList());
            List<SysRoleVO> roleVOs = roleIds.stream()
                    .map(roleVOMap::get)
                    .filter(r -> r != null)
                    .toList();
            user.setRoles(roleVOs);
        }
    }

    /**
     * 查询单个用户的角色列表
     */
    private List<SysRoleVO> getRolesByUserId(Long userId) {
        List<SysUserRole> userRoles = sysUserRoleMapper.selectList(
                new LambdaQueryWrapper<SysUserRole>().eq(SysUserRole::getUserId, userId));
        if (userRoles.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> roleIds = userRoles.stream().map(SysUserRole::getRoleId).toList();
        List<SysRole> roles = sysRoleMapper.selectBatchIds(roleIds);
        return roles.stream().map(sysRoleConverter::toVO).toList();
    }
}
