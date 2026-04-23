package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 菜谱推荐排序 DTO
 */
@Data
@Schema(description = "菜谱推荐排序参数")
public class TocRecipeRecommendDTO {

    @NotNull(message = "推荐排序值不能为空")
    @Min(value = 0, message = "推荐排序值不能为负数")
    @Schema(description = "推荐排序值(0=取消推荐,>0=推荐且越小越靠前)")
    private Integer recommendSort;
}
