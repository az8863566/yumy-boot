package com.de.food.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.de.food.admin.entity.SysUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户信息 Mapper
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {
}
