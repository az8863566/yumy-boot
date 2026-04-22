package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysMenuConverter;
import com.de.food.admin.dto.SysMenuCreateDTO;
import com.de.food.admin.dto.SysMenuQueryDTO;
import com.de.food.admin.dto.SysMenuUpdateDTO;
import com.de.food.business.entity.SysMenu;
import com.de.food.business.mapper.SysMenuMapper;
import com.de.food.admin.service.SysMenuService;
import com.de.food.admin.vo.SysMenuVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 菜单 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    private final SysMenuConverter sysMenuConverter;

    @Override
    public List<SysMenuVO> tree(SysMenuQueryDTO queryDTO) {
        LambdaQueryWrapper<SysMenu> wrapper = new LambdaQueryWrapper<SysMenu>()
                .like(queryDTO != null && StringUtils.hasText(queryDTO.getMenuName()), SysMenu::getMenuName, queryDTO.getMenuName())
                .eq(queryDTO != null && queryDTO.getMenuType() != null, SysMenu::getMenuType, queryDTO.getMenuType())
                .eq(queryDTO != null && queryDTO.getStatus() != null, SysMenu::getStatus, queryDTO.getStatus())
                .orderByAsc(SysMenu::getSortOrder);

        List<SysMenu> menus = baseMapper.selectList(wrapper);
        List<SysMenuVO> voList = menus.stream().map(sysMenuConverter::toVO).toList();

        // 构建树结构
        return buildTree(voList);
    }

    @Override
    public SysMenuVO getDetail(Long menuId) {
        SysMenu entity = baseMapper.selectById(menuId);
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "菜单不存在");
        }
        return sysMenuConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createMenu(SysMenuCreateDTO dto) {
        SysMenu entity = sysMenuConverter.toEntity(dto);
        if (entity.getParentId() == null) {
            entity.setParentId(0L);
        }
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateMenu(SysMenuUpdateDTO dto) {
        SysMenu entity = baseMapper.selectById(dto.getMenuId());
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "菜单不存在");
        }
        sysMenuConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteMenu(Long menuId) {
        // 检查是否有子菜单
        long childCount = baseMapper.selectCount(new LambdaQueryWrapper<SysMenu>()
                .eq(SysMenu::getParentId, menuId));
        if (childCount > 0) {
            throw new BizException(ErrorCode.PARAM_ERROR, "存在子菜单，不允许删除");
        }
        baseMapper.deleteById(menuId);
    }

    /**
     * 构建菜单树
     */
    private List<SysMenuVO> buildTree(List<SysMenuVO> menuList) {
        Map<Long, List<SysMenuVO>> groupedByParent = menuList.stream()
                .collect(Collectors.groupingBy(m -> m.getParentId() != null ? m.getParentId() : 0L));

        List<SysMenuVO> roots = groupedByParent.getOrDefault(0L, new ArrayList<>());
        for (SysMenuVO root : roots) {
            root.setChildren(groupedByParent.getOrDefault(root.getMenuId(), new ArrayList<>()));
            fillChildren(root, groupedByParent);
        }
        return roots;
    }

    private void fillChildren(SysMenuVO parent, Map<Long, List<SysMenuVO>> groupedByParent) {
        List<SysMenuVO> children = parent.getChildren();
        if (children != null) {
            for (SysMenuVO child : children) {
                child.setChildren(groupedByParent.getOrDefault(child.getMenuId(), new ArrayList<>()));
                fillChildren(child, groupedByParent);
            }
        }
    }
}
