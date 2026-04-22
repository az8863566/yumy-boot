package com.de.food.business.service;

import com.de.food.business.entity.TocBanner;

import java.util.List;

/**
 * 首页轮播图 只读 Service
 * <p>
 * 提供跨模块共享的轮播图查询能力，admin/toc 均可引用。
 * 写操作（CRUD）由 admin 模块的 TocBannerService 负责。
 */
public interface TocBannerReadService {

    /**
     * 查询启用的轮播图列表（按排序字段升序）
     *
     * @return 启用状态的轮播图列表
     */
    List<TocBanner> listEnabled();
}
