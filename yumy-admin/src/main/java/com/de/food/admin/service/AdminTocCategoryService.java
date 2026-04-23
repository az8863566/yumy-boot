package com.de.food.admin.service;

import com.de.food.admin.dto.TocParentCategoryCreateDTO;
import com.de.food.admin.dto.TocParentCategoryUpdateDTO;
import com.de.food.admin.dto.TocSubCategoryCreateDTO;
import com.de.food.admin.dto.TocSubCategoryUpdateDTO;
import com.de.food.admin.vo.TocCategoryTreeVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.business.entity.TocParentCategory;

import java.util.List;

/**
 * 分类管理 Service
 */
public interface AdminTocCategoryService extends IService<TocParentCategory> {

    /**
     * 获取分类树
     */
    List<TocCategoryTreeVO> getCategoryTree();

    /**
     * 新增父分类
     */
    void createParentCategory(TocParentCategoryCreateDTO dto);

    /**
     * 修改父分类
     */
    void updateParentCategory(TocParentCategoryUpdateDTO dto);

    /**
     * 删除父分类
     */
    void deleteParentCategory(Long categoryId);

    /**
     * 新增子分类
     */
    void createSubCategory(TocSubCategoryCreateDTO dto);

    /**
     * 修改子分类
     */
    void updateSubCategory(TocSubCategoryUpdateDTO dto);

    /**
     * 删除子分类
     */
    void deleteSubCategory(Long categoryId);
}
