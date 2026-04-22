package com.de.food.toc.service;

import com.de.food.toc.dto.TocUserUpdateDTO;
import com.de.food.toc.vo.TocUserVO;

/**
 * C端用户 Service
 */
public interface TocUserService {

    /**
     * 获取当前用户信息
     */
    TocUserVO getCurrentUser();

    /**
     * 更新当前用户信息
     */
    TocUserVO updateUser(TocUserUpdateDTO dto);
}
