package com.de.food.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.business.entity.SysDictType;
import com.de.food.business.mapper.SysDictTypeMapper;
import com.de.food.business.service.SysDictTypeReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 字典类型 只读 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysDictTypeReadServiceImpl implements SysDictTypeReadService {

    private final SysDictTypeMapper sysDictTypeMapper;

    @Override
    public Optional<SysDictType> getByDictType(String dictType) {
        SysDictType entity = sysDictTypeMapper.selectOne(
                new LambdaQueryWrapper<SysDictType>()
                        .eq(SysDictType::getDictType, dictType));
        return Optional.ofNullable(entity);
    }
}
