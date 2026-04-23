package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 评论查询 DTO
 */
@Data
@Schema(description = "评论查询参数")
public class TocCommentQueryDTO {

    @Schema(description = "菜谱ID(筛选)")
    private Long recipeId;

    @Schema(description = "用户ID(筛选)")
    private Long userId;

    @Schema(description = "评论内容(模糊)")
    private String text;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
