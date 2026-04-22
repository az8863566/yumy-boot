package com.de.food.admin.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 登录响应 VO
 * <p>
 * Admin 使用 Session 认证，无需返回 Token。
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "登录响应")
public class AuthLoginVO {

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "权限标识列表")
    private List<String> permissions;
}
