package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysOperLogQueryDTO;
import com.de.food.business.entity.SysOperLog;
import com.de.food.admin.vo.SysOperLogVO;

/**
 * 操作日志 Service
 */
public interface SysOperLogService extends IService<SysOperLog> {

    /**
     * 分页查询操作日志
     */
    IPage<SysOperLogVO> page(SysOperLogQueryDTO queryDTO);

    /**
     * 记录操作日志
     */
    void saveLog(SysOperLog operLog);
}
