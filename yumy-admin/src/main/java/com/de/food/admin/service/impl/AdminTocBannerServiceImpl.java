package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.AdminTocBannerConverter;
import com.de.food.admin.dto.TocBannerCreateDTO;
import com.de.food.admin.dto.TocBannerQueryDTO;
import com.de.food.admin.dto.TocBannerUpdateDTO;
import com.de.food.admin.service.TocBannerService;
import com.de.food.admin.vo.TocBannerVO;
import com.de.food.business.entity.TocBanner;
import com.de.food.business.mapper.TocBannerMapper;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 轮播图管理 Service 实现
 */
@Service
@RequiredArgsConstructor
public class AdminTocBannerServiceImpl extends ServiceImpl<TocBannerMapper, TocBanner> implements TocBannerService {

    private final AdminTocBannerConverter tocBannerConverter;

    @Override
    public IPage<TocBannerVO> page(TocBannerQueryDTO queryDTO) {
        Page<TocBanner> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<TocBanner> wrapper = new LambdaQueryWrapper<TocBanner>()
                .like(StringUtils.hasText(queryDTO.getTitle()), TocBanner::getTitle, queryDTO.getTitle())
                .eq(queryDTO.getStatus() != null, TocBanner::getStatus, queryDTO.getStatus())
                .orderByAsc(TocBanner::getSortOrder);

        IPage<TocBanner> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(tocBannerConverter::toVO);
    }

    @Override
    public TocBannerVO getDetail(Long bannerId) {
        TocBanner entity = baseMapper.selectById(bannerId);
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_BANNER_NOT_FOUND);
        }
        return tocBannerConverter.toVO(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createBanner(TocBannerCreateDTO dto) {
        TocBanner entity = tocBannerConverter.toEntity(dto);
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateBanner(TocBannerUpdateDTO dto) {
        TocBanner entity = baseMapper.selectById(dto.getBannerId());
        if (entity == null) {
            throw new BizException(ErrorCode.TOC_BANNER_NOT_FOUND);
        }
        tocBannerConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteBanner(Long bannerId) {
        baseMapper.deleteById(bannerId);
    }
}
