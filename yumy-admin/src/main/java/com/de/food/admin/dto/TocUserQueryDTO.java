package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * C端用户查询 DTO
 */
@Data
@Schema(description = "C端用户查询参数")
public class TocUserQueryDTO {

    @Schema(description = "用户名(模糊)")
    private String username;

    @Schema(description = "昵称(模糊)")
    private String nickname;

    @Schema(description = "状态(1正常 0禁用)")
    private Integer status;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
