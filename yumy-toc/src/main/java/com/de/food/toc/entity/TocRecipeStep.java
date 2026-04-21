package com.de.food.toc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 菜谱步骤表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("toc_recipe_step")
@Schema(description = "菜谱步骤")
public class TocRecipeStep extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "步骤ID")
    private Long stepId;

    @Schema(description = "所属菜谱ID")
    private Long recipeId;

    @Schema(description = "步骤序号")
    private Integer stepNumber;

    @Schema(description = "步骤描述")
    private String description;

    @Schema(description = "步骤配图URL")
    private String image;

    @Schema(description = "该步骤使用的食材名称数组")
    private List<String> ingredientsUsed;
}
