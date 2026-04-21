package com.de.food.toc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 更新用户信息 DTO
 */
@Data
@Schema(description = "更新用户信息参数")
public class TocUserUpdateDTO {

    @Schema(description = "用户名/昵称")
    private String username;

    @Schema(description = "头像URL")
    private String avatar;
}
