package com.de.food.toc.converter;

import com.de.food.business.entity.SysDictData;
import com.de.food.toc.vo.TocDictVO;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * 字典数据对象转换器（C端）
 */
@Mapper(componentModel = "spring")
public interface TocDictConverter {

    TocDictVO toVO(SysDictData entity);

    List<TocDictVO> toVOList(List<SysDictData> entities);
}
