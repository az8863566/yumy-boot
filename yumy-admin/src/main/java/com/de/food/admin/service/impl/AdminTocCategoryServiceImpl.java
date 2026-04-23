package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.AdminTocCategoryConverter;
import com.de.food.admin.dto.TocParentCategoryCreateDTO;
import com.de.food.admin.dto.TocParentCategoryUpdateDTO;
import com.de.food.admin.dto.TocSubCategoryCreateDTO;
import com.de.food.admin.dto.TocSubCategoryUpdateDTO;
import com.de.food.admin.service.AdminTocCategoryService;
import com.de.food.admin.vo.TocCategoryTreeVO;
import com.de.food.business.entity.TocParentCategory;
import com.de.food.business.entity.TocRecipe;
import com.de.food.business.entity.TocSubCategory;
import com.de.food.business.mapper.TocParentCategoryMapper;
import com.de.food.business.mapper.TocRecipeMapper;
import com.de.food.business.mapper.TocSubCategoryMapper;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分类管理 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AdminTocCategoryServiceImpl extends ServiceImpl<TocParentCategoryMapper, TocParentCategory> implements AdminTocCategoryService {

    private final TocSubCategoryMapper tocSubCategoryMapper;
    private final TocRecipeMapper tocRecipeMapper;
    private final AdminTocCategoryConverter categoryConverter;

    @Override
    public List<TocCategoryTreeVO> getCategoryTree() {
        List<TocParentCategory> parents = baseMapper.selectList(
                new LambdaQueryWrapper<TocParentCategory>()
                        .orderByAsc(TocParentCategory::getSortOrder));

        List<TocSubCategory> allSubs = tocSubCategoryMapper.selectList(
                new LambdaQueryWrapper<TocSubCategory>()
                        .orderByAsc(TocSubCategory::getSortOrder));

        Map<Long, List<TocCategoryTreeVO.TocSubCategoryItemVO>> subMap = allSubs.stream()
                .map(categoryConverter::toSubVO)
                .collect(Collectors.groupingBy(TocCategoryTreeVO.TocSubCategoryItemVO::getParentId));

        return parents.stream()
                .map(parent -> {
                    TocCategoryTreeVO vo = categoryConverter.toParentVONoSub(parent);
                    vo.setSubCategories(subMap.getOrDefault(parent.getCategoryId(), List.of()));
                    return vo;
                })
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createParentCategory(TocParentCategoryCreateDTO dto) {
        TocParentCategory entity = categoryConverter.toParentEntity(dto);
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateParentCategory(TocParentCategoryUpdateDTO dto) {
        TocParentCategory entity = baseMapper.selectById(dto.getCategoryId());
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_PARENT_CATEGORY_NOT_FOUND);
        }
        categoryConverter.updateParentEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteParentCategory(Long categoryId) {
        TocParentCategory entity = baseMapper.selectById(categoryId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_PARENT_CATEGORY_NOT_FOUND);
        }
        // 检查是否有关联子分类
        Long subCount = tocSubCategoryMapper.selectCount(
                new LambdaQueryWrapper<TocSubCategory>()
                        .eq(TocSubCategory::getParentId, categoryId));
        if (subCount > 0) {
            throw new BizException(ErrorCode.TOC_PARENT_CATEGORY_HAS_SUB);
        }
        baseMapper.deleteById(categoryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createSubCategory(TocSubCategoryCreateDTO dto) {
        // 校验父分类存在
        TocParentCategory parent = baseMapper.selectById(dto.getParentId());
        if (parent == null) {
            throw new BizException(ErrorCode.TOC_PARENT_CATEGORY_NOT_FOUND);
        }
        TocSubCategory entity = categoryConverter.toSubEntity(dto);
        tocSubCategoryMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSubCategory(TocSubCategoryUpdateDTO dto) {
        TocSubCategory entity = tocSubCategoryMapper.selectById(dto.getCategoryId());
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_SUB_CATEGORY_NOT_FOUND);
        }
        // 校验父分类存在
        TocParentCategory parent = baseMapper.selectById(dto.getParentId());
        if (parent == null) {
            throw new BizException(ErrorCode.TOC_PARENT_CATEGORY_NOT_FOUND);
        }
        categoryConverter.updateSubEntity(dto, entity);
        tocSubCategoryMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSubCategory(Long categoryId) {
        TocSubCategory entity = tocSubCategoryMapper.selectById(categoryId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_SUB_CATEGORY_NOT_FOUND);
        }
        // 检查是否有关联菜谱
        Long recipeCount = tocRecipeMapper.selectCount(
                new LambdaQueryWrapper<TocRecipe>()
                        .eq(TocRecipe::getCategoryId, categoryId));
        if (recipeCount > 0) {
            throw new BizException(ErrorCode.TOC_SUB_CATEGORY_HAS_RECIPE);
        }
        tocSubCategoryMapper.deleteById(categoryId);
    }
}
