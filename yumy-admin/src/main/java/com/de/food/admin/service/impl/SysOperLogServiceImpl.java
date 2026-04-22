package com.de.food.admin.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.de.food.admin.converter.SysOperLogConverter;
import com.de.food.admin.dto.SysOperLogQueryDTO;
import com.de.food.business.entity.SysOperLog;
import com.de.food.business.mapper.SysOperLogMapper;
import com.de.food.admin.service.SysOperLogService;
import com.de.food.admin.vo.SysOperLogVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 操作日志 Service 实现
 */
@Service
@RequiredArgsConstructor
public class SysOperLogServiceImpl extends ServiceImpl<SysOperLogMapper, SysOperLog> implements SysOperLogService {

    private final SysOperLogConverter sysOperLogConverter;

    @Override
    public IPage<SysOperLogVO> page(SysOperLogQueryDTO queryDTO) {
        Page<SysOperLog> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        LambdaQueryWrapper<SysOperLog> wrapper = new LambdaQueryWrapper<SysOperLog>()
                .like(StringUtils.hasText(queryDTO.getTitle()), SysOperLog::getTitle, queryDTO.getTitle())
                .eq(queryDTO.getBusinessType() != null, SysOperLog::getBusinessType, queryDTO.getBusinessType())
                .like(StringUtils.hasText(queryDTO.getOperName()), SysOperLog::getOperName, queryDTO.getOperName())
                .eq(queryDTO.getStatus() != null, SysOperLog::getStatus, queryDTO.getStatus())
                .orderByDesc(SysOperLog::getOperTime);

        IPage<SysOperLog> entityPage = baseMapper.selectPage(page, wrapper);
        return entityPage.convert(sysOperLogConverter::toVO);
    }

    @Override
    public void saveLog(SysOperLog operLog) {
        baseMapper.insert(operLog);
    }
}
