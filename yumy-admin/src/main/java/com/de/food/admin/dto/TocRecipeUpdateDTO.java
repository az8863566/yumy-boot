package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 菜谱修改 DTO
 */
@Data
@Schema(description = "菜谱修改参数")
public class TocRecipeUpdateDTO {

    @NotNull(message = "菜谱ID不能为空")
    @Schema(description = "菜谱ID")
    private Long recipeId;

    @NotBlank(message = "菜谱标题不能为空")
    @Size(max = 100, message = "菜谱标题最长100个字符")
    @Schema(description = "菜谱标题")
    private String title;

    @NotBlank(message = "菜谱描述不能为空")
    @Schema(description = "菜谱描述")
    private String description;

    @NotBlank(message = "封面图URL不能为空")
    @Size(max = 500, message = "封面图URL最长500个字符")
    @Schema(description = "封面图URL")
    private String image;

    @NotNull(message = "所属子分类ID不能为空")
    @Schema(description = "所属子分类ID")
    private Long categoryId;

    @NotBlank(message = "难度不能为空")
    @Schema(description = "难度：简单/中等/困难")
    private String difficulty;

    @NotBlank(message = "制作时间不能为空")
    @Schema(description = "制作时间")
    private String time;

    @Schema(description = "几人份")
    private Integer servings;

    @Schema(description = "推荐排序(0不推荐,>0推荐且越小越靠前)")
    private Integer recommendSort;

    @Valid
    @Schema(description = "食材列表(整体替换)")
    private List<TocRecipeCreateDTO.IngredientItem> ingredients;

    @Valid
    @Schema(description = "步骤列表(整体替换)")
    private List<TocRecipeCreateDTO.StepItem> steps;
}
