package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.TocBannerCreateDTO;
import com.de.food.admin.dto.TocBannerQueryDTO;
import com.de.food.admin.dto.TocBannerUpdateDTO;
import com.de.food.admin.vo.TocBannerVO;
import com.de.food.business.entity.TocBanner;

/**
 * 轮播图管理 Service
 */
public interface TocBannerService extends IService<TocBanner> {

    /**
     * 分页查询轮播图
     */
    IPage<TocBannerVO> page(TocBannerQueryDTO queryDTO);

    /**
     * 根据ID查询轮播图
     */
    TocBannerVO getDetail(Long bannerId);

    /**
     * 新增轮播图
     */
    void createBanner(TocBannerCreateDTO dto);

    /**
     * 修改轮播图
     */
    void updateBanner(TocBannerUpdateDTO dto);

    /**
     * 删除轮播图
     */
    void deleteBanner(Long bannerId);
}
