package com.de.food.toc.service;

import com.de.food.toc.vo.TocCategoryVO;

import java.util.List;

/**
 * 分类 Service
 */
public interface TocCategoryService {

    /**
     * 获取分类列表（父分类+子分类树）
     */
    List<TocCategoryVO> getCategoryTree();

    /**
     * 获取首页展示的子分类列表
     * <p>根据系统参数 toc.home.category_id 配置的父分类ID，查询其下的子分类</p>
     */
    List<TocCategoryVO.TocSubCategoryVO> getHomeCategories();
}
