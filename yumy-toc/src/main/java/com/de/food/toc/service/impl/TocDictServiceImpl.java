package com.de.food.toc.service.impl;

import com.de.food.business.entity.SysDictData;
import com.de.food.business.service.SysDictDataReadService;
import com.de.food.toc.converter.TocDictConverter;
import com.de.food.toc.service.TocDictService;
import com.de.food.toc.vo.TocDictVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典 Service 实现（C端）
 */
@Service
@RequiredArgsConstructor
public class TocDictServiceImpl implements TocDictService {

    private final SysDictDataReadService sysDictDataReadService;
    private final TocDictConverter tocDictConverter;

    @Override
    public List<TocDictVO> listByDictType(String dictType) {
        List<SysDictData> list = sysDictDataReadService.listEnabledByDictType(dictType);
        return tocDictConverter.toVOList(list);
    }
}
