package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 菜谱查询 DTO
 */
@Data
@Schema(description = "菜谱查询参数")
public class TocRecipeQueryDTO {

    @Schema(description = "菜谱标题(模糊)")
    private String title;

    @Schema(description = "所属子分类ID")
    private Long categoryId;

    @Schema(description = "难度")
    private String difficulty;

    @Schema(description = "是否推荐(recommendSort>0)")
    private Boolean recommended;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
