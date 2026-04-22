package com.de.food.business.service;

import com.de.food.business.entity.SysConfig;

import java.util.Optional;

/**
 * 系统参数配置 只读 Service
 * <p>
 * 提供跨模块共享的参数查询能力，admin/toc 均可引用。
 * 写操作（CRUD）由 admin 模块的 SysConfigService 负责。
 */
public interface SysConfigReadService {

    /**
     * 根据键名查询参数值
     *
     * @param configKey 参数键名
     * @return 参数键值，不存在则返回 null
     */
    String getConfigValue(String configKey);

    /**
     * 根据键名查询参数配置实体
     *
     * @param configKey 参数键名
     * @return Optional 包装的参数配置
     */
    Optional<SysConfig> getByKey(String configKey);
}
