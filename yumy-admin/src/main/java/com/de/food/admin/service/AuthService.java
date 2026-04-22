package com.de.food.admin.service;

import com.de.food.admin.dto.AuthLoginDTO;
import com.de.food.admin.vo.AuthLoginVO;

/**
 * 认证 Service
 */
public interface AuthService {

    /**
     * 登录
     */
    AuthLoginVO login(AuthLoginDTO dto);
}
