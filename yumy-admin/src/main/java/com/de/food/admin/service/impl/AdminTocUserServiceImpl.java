package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.AdminTocUserConverter;
import com.de.food.admin.dto.TocUserQueryDTO;
import com.de.food.admin.dto.TocUserStatusDTO;
import com.de.food.admin.service.AdminTocUserService;
import com.de.food.admin.vo.TocUserVO;
import com.de.food.business.entity.TocUser;
import com.de.food.business.mapper.TocUserMapper;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * C端用户管理 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AdminTocUserServiceImpl extends ServiceImpl<TocUserMapper, TocUser> implements AdminTocUserService {

    private final AdminTocUserConverter tocUserConverter;

    @Override
    public IPage<TocUserVO> page(TocUserQueryDTO queryDTO) {
        Page<TocUser> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<TocUser> wrapper = new LambdaQueryWrapper<TocUser>()
                .like(StringUtils.hasText(queryDTO.getUsername()), TocUser::getUsername, queryDTO.getUsername())
                .like(StringUtils.hasText(queryDTO.getNickname()), TocUser::getNickname, queryDTO.getNickname())
                .eq(queryDTO.getStatus() != null, TocUser::getStatus, queryDTO.getStatus())
                .orderByDesc(TocUser::getCreateTime);

        IPage<TocUser> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(tocUserConverter::toVO);
    }

    @Override
    public TocUserVO getDetail(Long userId) {
        TocUser entity = baseMapper.selectById(userId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_USER_NOT_FOUND);
        }
        return tocUserConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatus(Long userId, TocUserStatusDTO dto) {
        TocUser entity = baseMapper.selectById(userId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_USER_NOT_FOUND);
        }
        entity.setStatus(dto.getStatus());
        baseMapper.updateById(entity);
    }
}
