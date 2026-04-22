package com.de.food.business.service;

import com.de.food.business.entity.SysDictType;

import java.util.Optional;

/**
 * 字典类型 只读 Service
 * <p>
 * 提供跨模块共享的字典类型查询能力，admin/toc 均可引用。
 * 写操作（CRUD）由 admin 模块的 SysDictTypeService 负责。
 */
public interface SysDictTypeReadService {

    /**
     * 根据字典类型编码查询字典类型
     *
     * @param dictType 字典类型编码
     * @return Optional 包装的字典类型
     */
    Optional<SysDictType> getByDictType(String dictType);
}
