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
}
