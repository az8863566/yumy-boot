package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysDictTypeCreateDTO;
import com.de.food.admin.dto.SysDictTypeQueryDTO;
import com.de.food.admin.dto.SysDictTypeUpdateDTO;
import com.de.food.admin.entity.SysDictType;
import com.de.food.admin.vo.SysDictTypeVO;

/**
 * 字典类型 Service
 */
public interface SysDictTypeService extends IService<SysDictType> {

    /**
     * 分页查询字典类型
     */
    IPage<SysDictTypeVO> page(SysDictTypeQueryDTO queryDTO);

    /**
     * 根据ID查询字典类型
     */
    SysDictTypeVO getDetail(Long dictId);

    /**
     * 新增字典类型
     */
    void createDictType(SysDictTypeCreateDTO dto);

    /**
     * 修改字典类型
     */
    void updateDictType(SysDictTypeUpdateDTO dto);

    /**
     * 删除字典类型
     */
    void deleteDictType(Long dictId);
}
