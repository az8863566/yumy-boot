package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.TocRecipeCreateDTO;
import com.de.food.admin.dto.TocRecipeQueryDTO;
import com.de.food.admin.dto.TocRecipeRecommendDTO;
import com.de.food.admin.dto.TocRecipeUpdateDTO;
import com.de.food.admin.vo.TocRecipeDetailVO;
import com.de.food.admin.vo.TocRecipeVO;
import com.de.food.business.entity.TocRecipe;

/**
 * 菜谱管理 Service
 */
public interface AdminTocRecipeService extends IService<TocRecipe> {

    /**
     * 分页查询菜谱
     */
    IPage<TocRecipeVO> page(TocRecipeQueryDTO queryDTO);

    /**
     * 查询菜谱详情
     */
    TocRecipeDetailVO getDetail(Long recipeId);

    /**
     * 新增菜谱（含食材+步骤）
     */
    void createRecipe(TocRecipeCreateDTO dto);

    /**
     * 修改菜谱（含食材+步骤）
     */
    void updateRecipe(TocRecipeUpdateDTO dto);

    /**
     * 删除菜谱
     */
    void deleteRecipe(Long recipeId);

    /**
     * 设置推荐排序
     */
    void setRecommendSort(Long recipeId, TocRecipeRecommendDTO dto);
}
