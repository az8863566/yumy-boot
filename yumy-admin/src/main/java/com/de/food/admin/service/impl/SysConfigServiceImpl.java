package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysConfigConverter;
import com.de.food.admin.dto.SysConfigCreateDTO;
import com.de.food.admin.dto.SysConfigQueryDTO;
import com.de.food.admin.dto.SysConfigUpdateDTO;
import com.de.food.admin.entity.SysConfig;
import com.de.food.admin.mapper.SysConfigMapper;
import com.de.food.admin.service.SysConfigService;
import com.de.food.admin.vo.SysConfigVO;
import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * 参数配置 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements SysConfigService {

    private final SysConfigConverter sysConfigConverter;

    @Override
    public IPage<SysConfigVO> page(SysConfigQueryDTO queryDTO) {
        Page<SysConfig> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<SysConfig> wrapper = new LambdaQueryWrapper<SysConfig>()
                .like(StringUtils.hasText(queryDTO.getConfigName()), SysConfig::getConfigName, queryDTO.getConfigName())
                .like(StringUtils.hasText(queryDTO.getConfigKey()), SysConfig::getConfigKey, queryDTO.getConfigKey())
                .eq(queryDTO.getConfigType() != null, SysConfig::getConfigType, queryDTO.getConfigType())
                .orderByDesc(SysConfig::getCreateTime);

        IPage<SysConfig> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(sysConfigConverter::toVO);
    }

    @Override
    public SysConfigVO getDetail(Long configId) {
        SysConfig entity = baseMapper.selectById(configId);
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "参数配置不存在");
        }
        return sysConfigConverter.toVO(entity);
    }

    @Override
    public String getConfigValue(String configKey) {
        SysConfig config = baseMapper.selectOne(new LambdaQueryWrapper<SysConfig>()
                .eq(SysConfig::getConfigKey, configKey));
        return config != null ? config.getConfigValue() : null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createConfig(SysConfigCreateDTO dto) {
        SysConfig entity = sysConfigConverter.toEntity(dto);
        baseMapper.insert(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateConfig(SysConfigUpdateDTO dto) {
        SysConfig entity = baseMapper.selectById(dto.getConfigId());
        if (entity == null) {
            throw new BizException(ErrorCode.NOT_FOUND, "参数配置不存在");
        }
        sysConfigConverter.updateEntity(dto, entity);
        baseMapper.updateById(entity);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteConfig(Long configId) {
        SysConfig entity = baseMapper.selectById(configId);
        if (entity != null && entity.getConfigType() != null && entity.getConfigType() == 1) {
            throw new BizException(ErrorCode.PARAM_ERROR, "系统内置参数不允许删除");
        }
        baseMapper.deleteById(configId);
    }
}
