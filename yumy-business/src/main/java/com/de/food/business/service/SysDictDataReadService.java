package com.de.food.business.service;

import com.de.food.business.entity.SysDictData;

import java.util.List;

/**
 * 字典数据 只读 Service
 * <p>
 * 提供跨模块共享的字典数据查询能力，admin/toc 均可引用。
 * 写操作（CRUD）由 admin 模块的 SysDictDataService 负责。
 */
public interface SysDictDataReadService {

    /**
     * 根据字典类型查询启用的字典数据列表
     *
     * @param dictType 字典类型编码
     * @return 启用状态的字典数据列表，按排序字段升序
     */
    List<SysDictData> listEnabledByDictType(String dictType);
}
