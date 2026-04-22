package com.de.food.toc.service;

import com.de.food.toc.vo.TocDictVO;

import java.util.List;

/**
 * 字典 Service（C端）
 */
public interface TocDictService {

    /**
     * 根据字典类型查询启用的字典数据列表
     *
     * @param dictType 字典类型编码
     * @return 字典数据列表
     */
    List<TocDictVO> listByDictType(String dictType);
}
