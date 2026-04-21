package com.de.food.toc.service;

import com.de.food.toc.dto.TocAuthLoginDTO;
import com.de.food.toc.dto.TocAuthRegisterDTO;
import com.de.food.toc.vo.TocAuthVO;

/**
 * C端认证 Service
 */
public interface TocAuthService {

    /**
     * 用户注册
     */
    TocAuthVO register(TocAuthRegisterDTO dto);

    /**
     * 用户登录
     */
    TocAuthVO login(TocAuthLoginDTO dto);
}
