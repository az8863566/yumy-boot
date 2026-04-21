package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * C端用户信息 VO
 */
@Data
@Schema(description = "C端用户信息")
public class TocUserVO {

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "头像")
    private String avatar;
}
