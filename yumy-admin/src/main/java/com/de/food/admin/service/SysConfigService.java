package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.SysConfigCreateDTO;
import com.de.food.admin.dto.SysConfigQueryDTO;
import com.de.food.admin.dto.SysConfigUpdateDTO;
import com.de.food.business.entity.SysConfig;
import com.de.food.admin.vo.SysConfigVO;

/**
 * 参数配置 Service
 */
public interface SysConfigService extends IService<SysConfig> {

    /**
     * 分页查询参数配置
     */
    IPage<SysConfigVO> page(SysConfigQueryDTO queryDTO);

    /**
     * 根据ID查询参数配置
     */
    SysConfigVO getDetail(Long configId);

    /**
     * 根据键名查询参数值
     */
    String getConfigValue(String configKey);

    /**
     * 新增参数配置
     */
    void createConfig(SysConfigCreateDTO dto);

    /**
     * 修改参数配置
     */
    void updateConfig(SysConfigUpdateDTO dto);

    /**
     * 删除参数配置
     */
    void deleteConfig(Long configId);
}
