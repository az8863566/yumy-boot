package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysDictDataCreateDTO;
import com.de.food.admin.dto.SysDictDataQueryDTO;
import com.de.food.admin.dto.SysDictDataUpdateDTO;
import com.de.food.business.entity.SysDictData;
import com.de.food.admin.vo.SysDictDataVO;

import java.util.List;

/**
 * 字典数据 Service
 */
public interface SysDictDataService extends IService<SysDictData> {

    /**
     * 分页查询字典数据
     */
    IPage<SysDictDataVO> page(SysDictDataQueryDTO queryDTO);

    /**
     * 根据字典类型查询字典数据列表
     */
    List<SysDictDataVO> listByDictType(String dictType);

    /**
     * 根据ID查询字典数据
     */
    SysDictDataVO getDetail(Long dictCode);

    /**
     * 新增字典数据
     */
    void createDictData(SysDictDataCreateDTO dto);

    /**
     * 修改字典数据
     */
    void updateDictData(SysDictDataUpdateDTO dto);

    /**
     * 删除字典数据
     */
    void deleteDictData(Long dictCode);
}
