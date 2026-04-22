package com.de.food.toc.service;

import com.de.food.toc.vo.TocBannerVO;

import java.util.List;

/**
 * C端轮播图 Service
 */
public interface TocBannerService {

    /**
     * 获取启用的轮播图列表
     */
    List<TocBannerVO> listEnabled();
}
