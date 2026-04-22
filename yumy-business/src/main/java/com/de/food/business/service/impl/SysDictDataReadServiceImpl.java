package com.de.food.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.business.entity.SysDictData;
import com.de.food.business.mapper.SysDictDataMapper;
import com.de.food.business.service.SysDictDataReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 字典数据 只读 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysDictDataReadServiceImpl implements SysDictDataReadService {

    private final SysDictDataMapper sysDictDataMapper;

    @Override
    public List<SysDictData> listEnabledByDictType(String dictType) {
        return sysDictDataMapper.selectList(
                new LambdaQueryWrapper<SysDictData>()
                        .eq(SysDictData::getDictType, dictType)
                        .eq(SysDictData::getStatus, 1)
                        .orderByAsc(SysDictData::getDictSort));
    }
}
