package com.de.food.toc.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.de.food.framework.util.SecurityUtils;
import com.de.food.toc.converter.TocCommentConverter;
import com.de.food.toc.dto.TocCommentCreateDTO;
import com.de.food.toc.entity.TocComment;
import com.de.food.toc.entity.TocUser;
import com.de.food.toc.mapper.TocCommentMapper;
import com.de.food.toc.mapper.TocUserMapper;
import com.de.food.toc.service.TocCommentService;
import com.de.food.toc.vo.TocCommentVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 评论 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocCommentServiceImpl implements TocCommentService {

    private final TocCommentMapper tocCommentMapper;
    private final TocCommentConverter tocCommentConverter;
    private final TocUserMapper tocUserMapper;

    @Override
    public IPage<TocCommentVO> getCommentsByRecipeId(Long recipeId, Long pageNum, Long pageSize) {
        Page<TocComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TocComment> wrapper = new LambdaQueryWrapper<TocComment>()
                .eq(TocComment::getRecipeId, recipeId)
                .orderByDesc(TocComment::getCreateTime);

        IPage<TocComment> entityPage = tocCommentMapper.selectPage(page, wrapper);
        return enrichCommentVO(entityPage);
    }

    @Override
    public void createComment(Long recipeId, TocCommentCreateDTO dto) {
        Long userId = SecurityUtils.requireUserId();
        TocComment comment = tocCommentConverter.toEntity(dto);
        comment.setRecipeId(recipeId);
        comment.setUserId(userId);
        tocCommentMapper.insert(comment);
    }

    @Override
    public IPage<TocCommentVO> getMyComments(Long pageNum, Long pageSize) {
        Long userId = SecurityUtils.requireUserId();
        Page<TocComment> page = new Page<>(pageNum, pageSize);
        LambdaQueryWrapper<TocComment> wrapper = new LambdaQueryWrapper<TocComment>()
                .eq(TocComment::getUserId, userId)
                .orderByDesc(TocComment::getCreateTime);

        IPage<TocComment> entityPage = tocCommentMapper.selectPage(page, wrapper);
        return enrichCommentVO(entityPage);
    }

    /**
     * 为评论列表填充用户名和头像
     */
    private IPage<TocCommentVO> enrichCommentVO(IPage<TocComment> entityPage) {
        // 收集评论中的用户ID
        Map<Long, TocUser> userMap = entityPage.getRecords().stream()
                .map(TocComment::getUserId)
                .distinct()
                .collect(Collectors.toMap(
                        Function.identity(),
                        id -> tocUserMapper.selectById(id)
                ));

        return entityPage.convert(comment -> {
            TocCommentVO vo = new TocCommentVO();
            vo.setCommentId(comment.getCommentId());
            vo.setRecipeId(comment.getRecipeId());
            vo.setUserId(comment.getUserId());
            vo.setText(comment.getText());
            vo.setImages(comment.getImages());
            vo.setCreateTime(comment.getCreateTime());

            TocUser user = userMap.get(comment.getUserId());
            if (user != null) {
                vo.setUsername(user.getUsername());
                vo.setAvatar(user.getAvatar());
            }
            return vo;
        });
    }
}
