package com.de.food.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 菜谱详情 VO（含食材和步骤）
 */
@Data
@Schema(description = "菜谱详情")
public class TocRecipeDetailVO {

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

    @Schema(description = "推荐排序(0不推荐,>0推荐)")
    private Integer recommendSort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

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

        @Schema(description = "食材ID")
        private Long ingredientId;

        @Schema(description = "食材名称")
        private String name;

        @Schema(description = "用量")
        private String amount;

        @Schema(description = "排序顺序")
        private Integer sortOrder;
    }

    /**
     * 步骤 VO
     */
    @Data
    @Schema(description = "制作步骤")
    public static class StepVO {

        @Schema(description = "步骤ID")
        private Long stepId;

        @Schema(description = "步骤序号")
        private Integer stepNumber;

        @Schema(description = "步骤描述")
        private String description;

        @Schema(description = "步骤配图URL")
        private String image;

        @Schema(description = "该步骤使用的食材名称")
        private List<String> ingredientsUsed;
    }
}
