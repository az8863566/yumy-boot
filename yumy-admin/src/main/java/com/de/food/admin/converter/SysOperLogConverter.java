package com.de.food.admin.converter;

import com.de.food.business.entity.SysOperLog;
import com.de.food.admin.vo.SysOperLogVO;
import org.mapstruct.Mapper;

/**
 * 操作日志对象转换器
 */
@Mapper(componentModel = "spring")
public interface SysOperLogConverter {

    SysOperLogVO toVO(SysOperLog entity);
}
