package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.toc.converter.TocCategoryConverter;
import com.de.food.business.entity.TocParentCategory;
import com.de.food.business.entity.TocSubCategory;
import com.de.food.business.mapper.TocParentCategoryMapper;
import com.de.food.business.mapper.TocSubCategoryMapper;
import com.de.food.business.service.SysConfigReadService;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.toc.service.TocCategoryService;
import com.de.food.toc.vo.TocCategoryVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分类 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocCategoryServiceImpl implements TocCategoryService {

    private final TocParentCategoryMapper tocParentCategoryMapper;
    private final TocSubCategoryMapper tocSubCategoryMapper;
    private final SysConfigReadService sysConfigReadService;
    private final TocCategoryConverter tocCategoryConverter;

    /** 系统参数键名：首页展示的父分类ID */
    private static final String HOME_CATEGORY_CONFIG_KEY = "toc.home.category_id";

    @Override
    public List<TocCategoryVO> getCategoryTree() {
        // 查询所有父分类
        List<TocParentCategory> parents = tocParentCategoryMapper.selectList(
                new LambdaQueryWrapper<TocParentCategory>()
                        .orderByAsc(TocParentCategory::getSortOrder));

        // 查询所有子分类
        List<TocSubCategory> subs = tocSubCategoryMapper.selectList(
                new LambdaQueryWrapper<TocSubCategory>()
                        .orderByAsc(TocSubCategory::getSortOrder));

        // 按 parentId 分组
        Map<Long, List<TocCategoryVO.TocSubCategoryVO>> subMap = subs.stream()
                .map(tocCategoryConverter::toSubCategoryVO)
                .collect(Collectors.groupingBy(TocCategoryVO.TocSubCategoryVO::getParentId));

        // 组装树
        return parents.stream()
                .map(parent -> {
                    TocCategoryVO vo = tocCategoryConverter.toCategoryVO(parent);
                    vo.setSubCategories(subMap.getOrDefault(parent.getCategoryId(), List.of()));
                    return vo;
                })
                .toList();
    }

    @Override
    public List<TocCategoryVO.TocSubCategoryVO> getHomeCategories() {
        // 从系统参数表读取首页展示的父分类ID
        String configValue = sysConfigReadService.getConfigValue(HOME_CATEGORY_CONFIG_KEY);
        if (configValue == null) {
            throw new BizException(ErrorCode.TOC_HOME_CATEGORY_NOT_CONFIGURED);
        }

        Long parentId;
        try {
            parentId = Long.valueOf(configValue);
        } catch (NumberFormatException e) {
            throw new BizException(ErrorCode.PARAM_ERROR, "首页分类配置值格式错误");
        }

        // 校验父分类是否存在
        TocParentCategory parent = tocParentCategoryMapper.selectById(parentId);
        if (parent == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "首页展示分类不存在");
        }

        // 查询该父分类下的子分类
        List<TocSubCategory> subs = tocSubCategoryMapper.selectList(
                new LambdaQueryWrapper<TocSubCategory>()
                        .eq(TocSubCategory::getParentId, parentId)
                        .orderByAsc(TocSubCategory::getSortOrder));

        return tocCategoryConverter.toSubCategoryVOList(subs);
    }
}
