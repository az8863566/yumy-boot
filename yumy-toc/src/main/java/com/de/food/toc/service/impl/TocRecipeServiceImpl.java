package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.toc.converter.TocRecipeConverter;
import com.de.food.toc.dto.TocRecipeQueryDTO;
import com.de.food.business.entity.TocRecipe;
import com.de.food.business.entity.TocRecipeIngredient;
import com.de.food.business.entity.TocRecipeStep;
import com.de.food.business.mapper.TocRecipeIngredientMapper;
import com.de.food.business.mapper.TocRecipeMapper;
import com.de.food.business.mapper.TocRecipeStepMapper;
import com.de.food.toc.service.TocRecipeService;
import com.de.food.toc.vo.TocRecipeDetailVO;
import com.de.food.toc.vo.TocRecipeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 菜谱 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocRecipeServiceImpl implements TocRecipeService {

    private final TocRecipeMapper tocRecipeMapper;
    private final TocRecipeIngredientMapper tocRecipeIngredientMapper;
    private final TocRecipeStepMapper tocRecipeStepMapper;
    private final TocRecipeConverter tocRecipeConverter;

    @Override
    public IPage<TocRecipeVO> page(TocRecipeQueryDTO queryDTO) {
        Page<TocRecipe> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<TocRecipe> wrapper = new LambdaQueryWrapper<TocRecipe>()
                .eq(queryDTO.getCategoryId() != null, TocRecipe::getCategoryId, queryDTO.getCategoryId())
                .and(StringUtils.hasText(queryDTO.getKeyword()), w -> w
                        .like(TocRecipe::getTitle, queryDTO.getKeyword())
                        .or()
                        .like(TocRecipe::getDescription, queryDTO.getKeyword()))
                .orderByDesc(TocRecipe::getCreateTime);

        IPage<TocRecipe> entityPage = tocRecipeMapper.selectPage(page, wrapper);
        return entityPage.convert(tocRecipeConverter::toVO);
    }

    @Override
    public TocRecipeDetailVO getDetail(Long recipeId) {
        TocRecipe recipe = tocRecipeMapper.selectById(recipeId);
        if (recipe == null) {
            throw new BizException(ErrorCode.TOC_RECIPE_NOT_FOUND);
        }

        TocRecipeDetailVO detailVO = tocRecipeConverter.toDetailVO(recipe);

        // 查询食材列表
        List<TocRecipeIngredient> ingredients = tocRecipeIngredientMapper.selectList(
                new LambdaQueryWrapper<TocRecipeIngredient>()
                        .eq(TocRecipeIngredient::getRecipeId, recipeId)
                        .orderByAsc(TocRecipeIngredient::getSortOrder));
        detailVO.setIngredients(tocRecipeConverter.toIngredientVOList(ingredients));

        // 查询步骤列表
        List<TocRecipeStep> steps = tocRecipeStepMapper.selectList(
                new LambdaQueryWrapper<TocRecipeStep>()
                        .eq(TocRecipeStep::getRecipeId, recipeId)
                        .orderByAsc(TocRecipeStep::getStepNumber));
        detailVO.setSteps(tocRecipeConverter.toStepVOList(steps));

        return detailVO;
    }

    @Override
    public List<TocRecipeVO> getTopRanked(int limit) {
        Page<TocRecipe> page = new Page<>(1, limit);
        LambdaQueryWrapper<TocRecipe> wrapper = new LambdaQueryWrapper<TocRecipe>()
                .orderByDesc(TocRecipe::getLikes);
        IPage<TocRecipe> entityPage = tocRecipeMapper.selectPage(page, wrapper);
        return entityPage.getRecords().stream()
                .map(tocRecipeConverter::toVO)
                .toList();
    }

    @Override
    public IPage<TocRecipeVO> getRecommended(int pageNum, int pageSize) {
        Page<TocRecipe> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TocRecipe> wrapper = new LambdaQueryWrapper<TocRecipe>()
                .gt(TocRecipe::getRecommendSort, 0)
                .orderByAsc(TocRecipe::getRecommendSort);
        IPage<TocRecipe> entityPage = tocRecipeMapper.selectPage(page, wrapper);
        return entityPage.convert(tocRecipeConverter::toVO);
    }
}
