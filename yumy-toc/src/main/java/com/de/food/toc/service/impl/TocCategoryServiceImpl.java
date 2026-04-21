package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.toc.converter.TocCategoryConverter;
import com.de.food.toc.entity.TocParentCategory;
import com.de.food.toc.entity.TocSubCategory;
import com.de.food.toc.mapper.TocParentCategoryMapper;
import com.de.food.toc.mapper.TocSubCategoryMapper;
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
    private final TocCategoryConverter tocCategoryConverter;

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
}
