package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 登录/注册响应 VO
 */
@Data
@Schema(description = "登录/注册响应")
public class TocAuthVO {

    @Schema(description = "JWT Token")
    private String token;

    @Schema(description = "用户信息")
    private TocUserVO user;
}
