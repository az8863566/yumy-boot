package com.de.food.toc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 更新用户信息 DTO
 */
@Data
@Schema(description = "更新用户信息参数")
public class TocUserUpdateDTO {

    @Size(min = 2, max = 64, message = "用户名长度2-64个字符")
    @Schema(description = "用户名")
    private String username;

    @Size(max = 64, message = "昵称最长64个字符")
    @Schema(description = "昵称")
    private String nickname;

    @Size(max = 512, message = "头像URL最长512个字符")
    @Schema(description = "头像URL")
    private String avatar;
}
