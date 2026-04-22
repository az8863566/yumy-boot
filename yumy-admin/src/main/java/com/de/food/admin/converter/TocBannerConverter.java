package com.de.food.admin.converter;

import com.de.food.admin.dto.TocBannerCreateDTO;
import com.de.food.admin.dto.TocBannerUpdateDTO;
import com.de.food.admin.vo.TocBannerVO;
import com.de.food.business.entity.TocBanner;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

/**
 * 轮播图对象转换器
 */
@Mapper(componentModel = "spring")
public interface TocBannerConverter {

    TocBanner toEntity(TocBannerCreateDTO dto);

    void updateEntity(TocBannerUpdateDTO dto, @MappingTarget TocBanner entity);

    TocBannerVO toVO(TocBanner entity);
}
