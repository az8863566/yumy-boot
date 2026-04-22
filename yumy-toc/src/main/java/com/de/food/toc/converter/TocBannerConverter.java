package com.de.food.toc.converter;

import com.de.food.business.entity.TocBanner;
import com.de.food.toc.vo.TocBannerVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 轮播图对象转换器
 */
@Mapper(componentModel = "spring")
public interface TocBannerConverter {

    TocBannerVO toVO(TocBanner entity);

    List<TocBannerVO> toVOList(List<TocBanner> entities);
}
