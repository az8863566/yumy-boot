package com.de.food.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.admin.entity.SysOperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * 操作日志 Mapper
 */
@Mapper
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {
}
