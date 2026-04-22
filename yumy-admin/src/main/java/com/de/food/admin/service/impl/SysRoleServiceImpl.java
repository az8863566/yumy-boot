package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysRoleConverter;
import com.de.food.admin.dto.SysRoleCreateDTO;
import com.de.food.admin.dto.SysRoleQueryDTO;
import com.de.food.admin.dto.SysRoleUpdateDTO;
import com.de.food.business.entity.SysRole;
import com.de.food.business.entity.SysRoleMenu;
import com.de.food.business.entity.SysUserRole;
import com.de.food.business.mapper.SysRoleMapper;
import com.de.food.business.mapper.SysRoleMenuMapper;
import com.de.food.business.mapper.SysUserRoleMapper;
import com.de.food.admin.service.SysRoleService;
import com.de.food.admin.vo.SysRoleVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 角色 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {

    private final SysRoleConverter sysRoleConverter;
    private final SysRoleMenuMapper sysRoleMenuMapper;
    private final SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysRoleVO> page(SysRoleQueryDTO queryDTO) {
        Page<SysRole> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<SysRole>()
                .like(StringUtils.hasText(queryDTO.getRoleName()), SysRole::getRoleName, queryDTO.getRoleName())
                .eq(StringUtils.hasText(queryDTO.getRoleCode()), SysRole::getRoleCode, queryDTO.getRoleCode())
                .eq(queryDTO.getStatus() != null, SysRole::getStatus, queryDTO.getStatus())
                .orderByDesc(SysRole::getCreateTime);

        IPage<SysRole> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(sysRoleConverter::toVO);
    }

    @Override
    public SysRoleVO getDetail(Long roleId) {
        SysRole entity = baseMapper.selectById(roleId);
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "角色不存在");
        }
        return sysRoleConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRole(SysRoleCreateDTO dto) {
        // 校验角色编码唯一
        SysRole exists = baseMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                .eq(SysRole::getRoleCode, dto.getRoleCode()));
        if (exists != null) {
            throw new BizException(ErrorCode.PARAM_ERROR, "角色编码已存在");
        }
        SysRole entity = sysRoleConverter.toEntity(dto);
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRole(SysRoleUpdateDTO dto) {
        SysRole entity = baseMapper.selectById(dto.getRoleId());
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "角色不存在");
        }
        // 校验角色编码唯一（排除自身）
        if (dto.getRoleCode() != null) {
            SysRole exists = baseMapper.selectOne(new LambdaQueryWrapper<SysRole>()
                    .eq(SysRole::getRoleCode, dto.getRoleCode())
                    .ne(SysRole::getRoleId, dto.getRoleId()));
            if (exists != null) {
                throw new BizException(ErrorCode.PARAM_ERROR, "角色编码已存在");
            }
        }
        sysRoleConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRole(Long roleId) {
        baseMapper.deleteById(roleId);
        // 删除角色菜单关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId));
        // 删除用户角色关联
        sysUserRoleMapper.delete(new LambdaQueryWrapper<SysUserRole>()
                .eq(SysUserRole::getRoleId, roleId));
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Long roleId) {
        List<SysRoleMenu> roleMenus = sysRoleMenuMapper.selectList(
                new LambdaQueryWrapper<SysRoleMenu>().eq(SysRoleMenu::getRoleId, roleId));
        return roleMenus.stream().map(SysRoleMenu::getMenuId).toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignMenus(Long roleId, List<Long> menuIds) {
        // 校验角色是否存在
        SysRole role = baseMapper.selectById(roleId);
        if (role == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "角色不存在");
        }
        // 先删除旧关联
        sysRoleMenuMapper.delete(new LambdaQueryWrapper<SysRoleMenu>()
                .eq(SysRoleMenu::getRoleId, roleId));
        // 批量插入新关联
        if (menuIds != null && !menuIds.isEmpty()) {
            List<SysRoleMenu> roleMenus = menuIds.stream().map(menuId -> {
                SysRoleMenu rm = new SysRoleMenu();
                rm.setRoleId(roleId);
                rm.setMenuId(menuId);
                return rm;
            }).toList();
            roleMenus.forEach(sysRoleMenuMapper::insert);
        }
    }
}
