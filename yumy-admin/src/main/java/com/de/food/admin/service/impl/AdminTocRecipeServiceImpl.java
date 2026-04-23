package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.AdminTocRecipeConverter;
import com.de.food.admin.dto.TocRecipeCreateDTO;
import com.de.food.admin.dto.TocRecipeQueryDTO;
import com.de.food.admin.dto.TocRecipeRecommendDTO;
import com.de.food.admin.dto.TocRecipeUpdateDTO;
import com.de.food.admin.service.AdminTocRecipeService;
import com.de.food.admin.vo.TocRecipeDetailVO;
import com.de.food.admin.vo.TocRecipeVO;
import com.de.food.business.entity.TocRecipe;
import com.de.food.business.entity.TocRecipeIngredient;
import com.de.food.business.entity.TocRecipeStep;
import com.de.food.business.mapper.TocRecipeIngredientMapper;
import com.de.food.business.mapper.TocRecipeMapper;
import com.de.food.business.mapper.TocRecipeStepMapper;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 菜谱管理 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AdminTocRecipeServiceImpl extends ServiceImpl<TocRecipeMapper, TocRecipe> implements AdminTocRecipeService {

    private final TocRecipeIngredientMapper tocRecipeIngredientMapper;
    private final TocRecipeStepMapper tocRecipeStepMapper;
    private final AdminTocRecipeConverter recipeConverter;

    @Override
    public IPage<TocRecipeVO> page(TocRecipeQueryDTO queryDTO) {
        Page<TocRecipe> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<TocRecipe> wrapper = new LambdaQueryWrapper<TocRecipe>()
                .like(StringUtils.hasText(queryDTO.getTitle()), TocRecipe::getTitle, queryDTO.getTitle())
                .eq(queryDTO.getCategoryId() != null, TocRecipe::getCategoryId, queryDTO.getCategoryId())
                .eq(StringUtils.hasText(queryDTO.getDifficulty()), TocRecipe::getDifficulty, queryDTO.getDifficulty())
                .gt(queryDTO.getRecommended() != null && queryDTO.getRecommended(), TocRecipe::getRecommendSort, 0)
                .eq(queryDTO.getRecommended() != null && !queryDTO.getRecommended(), TocRecipe::getRecommendSort, 0)
                .orderByDesc(TocRecipe::getCreateTime);

        IPage<TocRecipe> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(recipeConverter::toVO);
    }

    @Override
    public TocRecipeDetailVO getDetail(Long recipeId) {
        TocRecipe recipe = baseMapper.selectById(recipeId);
        if (recipe == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }

        TocRecipeDetailVO detailVO = recipeConverter.toDetailVO(recipe);

        // 查询食材列表
        List<TocRecipeIngredient> ingredients = tocRecipeIngredientMapper.selectList(
                new LambdaQueryWrapper<TocRecipeIngredient>()
                        .eq(TocRecipeIngredient::getRecipeId, recipeId)
                        .orderByAsc(TocRecipeIngredient::getSortOrder));
        detailVO.setIngredients(recipeConverter.toIngredientVOList(ingredients));

        // 查询步骤列表
        List<TocRecipeStep> steps = tocRecipeStepMapper.selectList(
                new LambdaQueryWrapper<TocRecipeStep>()
                        .eq(TocRecipeStep::getRecipeId, recipeId)
                        .orderByAsc(TocRecipeStep::getStepNumber));
        detailVO.setSteps(recipeConverter.toStepVOList(steps));

        return detailVO;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createRecipe(TocRecipeCreateDTO dto) {
        TocRecipe entity = recipeConverter.toEntity(dto);
        entity.setLikes(0);
        baseMapper.insert(entity);

        Long recipeId = entity.getRecipeId();
        saveIngredientsAndSteps(recipeId, dto.getIngredients(), dto.getSteps());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateRecipe(TocRecipeUpdateDTO dto) {
        TocRecipe entity = baseMapper.selectById(dto.getRecipeId());
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }
        recipeConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);

        Long recipeId = dto.getRecipeId();
        // 先删后插：食材
        tocRecipeIngredientMapper.delete(
                new LambdaQueryWrapper<TocRecipeIngredient>()
                        .eq(TocRecipeIngredient::getRecipeId, recipeId));
        // 先删后插：步骤
        tocRecipeStepMapper.delete(
                new LambdaQueryWrapper<TocRecipeStep>()
                        .eq(TocRecipeStep::getRecipeId, recipeId));

        saveIngredientsAndSteps(recipeId, dto.getIngredients(), dto.getSteps());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteRecipe(Long recipeId) {
        TocRecipe entity = baseMapper.selectById(recipeId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }
        baseMapper.deleteById(recipeId);

        // 删除关联的食材和步骤
        tocRecipeIngredientMapper.delete(
                new LambdaQueryWrapper<TocRecipeIngredient>()
                        .eq(TocRecipeIngredient::getRecipeId, recipeId));
        tocRecipeStepMapper.delete(
                new LambdaQueryWrapper<TocRecipeStep>()
                        .eq(TocRecipeStep::getRecipeId, recipeId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void setRecommendSort(Long recipeId, TocRecipeRecommendDTO dto) {
        TocRecipe entity = baseMapper.selectById(recipeId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }
        entity.setRecommendSort(dto.getRecommendSort());
        baseMapper.updateById(entity);
    }

    /**
     * 批量保存食材和步骤
     */
    private void saveIngredientsAndSteps(Long recipeId,
                                          List<TocRecipeCreateDTO.IngredientItem> ingredientItems,
                                          List<TocRecipeCreateDTO.StepItem> stepItems) {
        if (ingredientItems != null && !ingredientItems.isEmpty()) {
            List<TocRecipeIngredient> ingredients = recipeConverter.toIngredientEntityList(ingredientItems);
            ingredients.forEach(i -> i.setRecipeId(recipeId));
            ingredients.forEach(tocRecipeIngredientMapper::insert);
        }
        if (stepItems != null && !stepItems.isEmpty()) {
            List<TocRecipeStep> steps = recipeConverter.toStepEntityList(stepItems);
            steps.forEach(s -> s.setRecipeId(recipeId));
            steps.forEach(tocRecipeStepMapper::insert);
        }
    }
}
