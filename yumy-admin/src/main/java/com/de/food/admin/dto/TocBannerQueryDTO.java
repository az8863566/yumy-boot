package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 轮播图查询 DTO
 */
@Data
@Schema(description = "轮播图查询参数")
public class TocBannerQueryDTO {

    @Schema(description = "轮播图标题(模糊)")
    private String title;

    @Schema(description = "状态(1启用 0停用)")
    private Integer status;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
