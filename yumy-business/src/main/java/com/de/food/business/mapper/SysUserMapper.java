package com.de.food.business.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.business.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息 Mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
