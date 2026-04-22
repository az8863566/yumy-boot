package com.de.food.toc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 菜谱列表查询 DTO
 */
@Data
@Schema(description = "菜谱列表查询参数")
public class TocRecipeQueryDTO {

    @Schema(description = "子分类ID")
    private Long categoryId;

    @Schema(description = "搜索关键词")
    private String keyword;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
