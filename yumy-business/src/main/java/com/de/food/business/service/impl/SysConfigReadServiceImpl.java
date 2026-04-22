package com.de.food.business.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.de.food.business.entity.SysConfig;
import com.de.food.business.mapper.SysConfigMapper;
import com.de.food.business.service.SysConfigReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 系统参数配置 只读 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysConfigReadServiceImpl implements SysConfigReadService {

    private final SysConfigMapper sysConfigMapper;

    @Override
    public String getConfigValue(String configKey) {
        return getByKey(configKey).map(SysConfig::getConfigValue).orElse(null);
    }

    @Override
    public Optional<SysConfig> getByKey(String configKey) {
        SysConfig config = sysConfigMapper.selectOne(
                new LambdaQueryWrapper<SysConfig>()
                        .eq(SysConfig::getConfigKey, configKey));
        return Optional.ofNullable(config);
    }
}
