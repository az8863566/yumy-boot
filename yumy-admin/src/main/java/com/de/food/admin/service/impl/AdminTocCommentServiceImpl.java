package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.AdminTocCommentConverter;
import com.de.food.admin.dto.TocCommentQueryDTO;
import com.de.food.admin.service.AdminTocCommentService;
import com.de.food.admin.vo.TocCommentVO;
import com.de.food.business.entity.TocComment;
import com.de.food.business.entity.TocRecipe;
import com.de.food.business.entity.TocUser;
import com.de.food.business.mapper.TocCommentMapper;
import com.de.food.business.mapper.TocRecipeMapper;
import com.de.food.business.mapper.TocUserMapper;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 评论管理 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AdminTocCommentServiceImpl extends ServiceImpl<TocCommentMapper, TocComment> implements AdminTocCommentService {

    private final AdminTocCommentConverter commentConverter;
    private final TocUserMapper tocUserMapper;
    private final TocRecipeMapper tocRecipeMapper;

    @Override
    public IPage<TocCommentVO> page(TocCommentQueryDTO queryDTO) {
        Page<TocComment> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Long recipeId = queryDTO.getRecipeId();
        Long userId = queryDTO.getUserId();
        LambdaQueryWrapper<TocComment> wrapper = new LambdaQueryWrapper<TocComment>()
                .eq(recipeId != null, TocComment::getRecipeId, recipeId)
                .eq(userId != null, TocComment::getUserId, userId)
                .like(StringUtils.hasText(queryDTO.getText()), TocComment::getText, queryDTO.getText())
                .orderByDesc(TocComment::getCreateTime);

        IPage<TocComment> entityPage = baseMapper.selectPage(page, wrapper);
        return enrichCommentVO(entityPage);
    }

    @Override
    public TocCommentVO getDetail(Long commentId) {
        TocComment entity = baseMapper.selectById(commentId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_COMMENT_NOT_FOUND);
        }
        TocCommentVO vo = commentConverter.toVO(entity);

        // 填充用户信息
        TocUser user = tocUserMapper.selectById(entity.getUserId());
        if (user != null) {
            vo.setUsername(user.getUsername());
            vo.setAvatar(user.getAvatar());
        }

        // 填充菜谱标题
        TocRecipe recipe = tocRecipeMapper.selectById(entity.getRecipeId());
        if (recipe != null) {
            vo.setRecipeTitle(recipe.getTitle());
        }

        return vo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId) {
        TocComment entity = baseMapper.selectById(commentId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_COMMENT_NOT_FOUND);
        }
        baseMapper.deleteById(commentId);
    }

    /**
     * 为评论列表填充用户信息和菜谱标题
     */
    private IPage<TocCommentVO> enrichCommentVO(IPage<TocComment> entityPage) {
        if (entityPage.getRecords().isEmpty()) {
            return entityPage.convert(comment -> commentConverter.toVO(comment));
        }

        // 批量查询用户信息
        List<Long> userIds = entityPage.getRecords().stream()
                .map(TocComment::getUserId)
                .distinct()
                .toList();
        Map<Long, TocUser> userMap = tocUserMapper.selectBatchIds(userIds).stream()
                .collect(Collectors.toMap(TocUser::getUserId, Function.identity()));

        // 批量查询菜谱信息
        List<Long> recipeIds = entityPage.getRecords().stream()
                .map(TocComment::getRecipeId)
                .distinct()
                .toList();
        Map<Long, TocRecipe> recipeMap = tocRecipeMapper.selectBatchIds(recipeIds).stream()
                .collect(Collectors.toMap(TocRecipe::getRecipeId, Function.identity()));

        return entityPage.convert(comment -> {
            TocCommentVO vo = commentConverter.toVO(comment);
            TocUser user = userMap.get(comment.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
                vo.setAvatar(user.getAvatar());
            }
            TocRecipe recipe = recipeMap.get(comment.getRecipeId());
            if (recipe != null) {
                vo.setRecipeTitle(recipe.getTitle());
            }
            return vo;
        });
    }
}
