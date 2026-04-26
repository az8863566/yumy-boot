package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 菜谱详情 VO（完整信息，含食材和步骤）
 */
@Data
@Schema(description = "菜谱详情")
public class TocRecipeDetailVO {

    @Schema(description = "菜谱ID")
    private String recipeId;

    @Schema(description = "菜谱标题")
    private String title;

    @Schema(description = "菜谱描述")
    private String description;

    @Schema(description = "封面图URL")
    private String image;

    @Schema(description = "所属子分类ID")
    private String categoryId;

    @Schema(description = "点赞数")
    private Integer likes;

    @Schema(description = "难度")
    private String difficulty;

    @Schema(description = "制作时间")
    private String time;

    @Schema(description = "几人份")
    private Integer servings;

    @Schema(description = "食材列表")
    private List<IngredientVO> ingredients;

    @Schema(description = "制作步骤")
    private List<StepVO> steps;

    /**
     * 食材 VO
     */
    @Data
    @Schema(description = "食材")
    public static class IngredientVO {

        @Schema(description = "食材名称")
        private String name;

        @Schema(description = "用量")
        private String amount;
    }

    /**
     * 步骤 VO
     */
    @Data
    @Schema(description = "制作步骤")
    public static class StepVO {

        @Schema(description = "步骤序号")
        private Integer id;

        @Schema(description = "步骤描述")
        private String description;

        @Schema(description = "步骤配图URL")
        private String image;

        @Schema(description = "该步骤使用的食材名称")
        private List<String> ingredientsUsed;
    }
}
