package com.de.food.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.business.entity.TocBanner;
import com.de.food.business.mapper.TocBannerMapper;
import com.de.food.business.service.TocBannerReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页轮播图 只读 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocBannerReadServiceImpl implements TocBannerReadService {

    private final TocBannerMapper tocBannerMapper;

    @Override
    public List<TocBanner> listEnabled() {
        return tocBannerMapper.selectList(
                new LambdaQueryWrapper<TocBanner>()
                        .eq(TocBanner::getStatus, 1)
                        .orderByAsc(TocBanner::getSortOrder));
    }
}
