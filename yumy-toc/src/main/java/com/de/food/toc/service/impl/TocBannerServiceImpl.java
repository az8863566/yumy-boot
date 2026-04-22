package com.de.food.toc.service.impl;

import com.de.food.business.entity.TocBanner;
import com.de.food.business.service.TocBannerReadService;
import com.de.food.toc.converter.TocBannerConverter;
import com.de.food.toc.service.TocBannerService;
import com.de.food.toc.vo.TocBannerVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * C端轮播图 Service 实现
 */
@Service
@RequiredArgsConstructor
public class TocBannerServiceImpl implements TocBannerService {

    private final TocBannerReadService tocBannerReadService;
    private final TocBannerConverter tocBannerConverter;

    @Override
    public List<TocBannerVO> listEnabled() {
        List<TocBanner> banners = tocBannerReadService.listEnabled();
        return tocBannerConverter.toVOList(banners);
    }
}
