package com.de.food.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.de.food.admin.dto.TocUserQueryDTO;
import com.de.food.admin.dto.TocUserStatusDTO;
import com.de.food.admin.vo.TocUserVO;
import com.de.food.business.entity.TocUser;

/**
 * C端用户管理 Service
 */
public interface AdminTocUserService extends IService<TocUser> {

    /**
     * 分页查询用户列表
     */
    IPage<TocUserVO> page(TocUserQueryDTO queryDTO);

    /**
     * 查询用户详情
     */
    TocUserVO getDetail(Long userId);

    /**
     * 切换用户状态
     */
    void updateStatus(Long userId, TocUserStatusDTO dto);
}
