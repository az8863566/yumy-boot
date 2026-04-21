package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜谱列表 VO（简要信息）
 */
@Data
@Schema(description = "菜谱列表项")
public class TocRecipeVO {

    @Schema(description = "菜谱ID")
    private Long recipeId;

    @Schema(description = "菜谱标题")
    private String title;

    @Schema(description = "菜谱描述")
    private String description;

    @Schema(description = "封面图URL")
    private String image;

    @Schema(description = "所属子分类ID")
    private Long categoryId;

    @Schema(description = "点赞数")
    private Integer likes;

    @Schema(description = "难度")
    private String difficulty;

    @Schema(description = "制作时间")
    private String time;

    @Schema(description = "几人份")
    private Integer servings;
}
