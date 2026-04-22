package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜谱食材表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "toc_recipe_ingredient", schema = "toc")
@Schema(description = "菜谱食材")
public class TocRecipeIngredient extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "食材ID")
    private Long ingredientId;

    @Schema(description = "所属菜谱ID")
    private Long recipeId;

    @Schema(description = "食材名称")
    private String name;

    @Schema(description = "用量")
    private String amount;

    @Schema(description = "排序顺序")
    private Integer sortOrder;
}
