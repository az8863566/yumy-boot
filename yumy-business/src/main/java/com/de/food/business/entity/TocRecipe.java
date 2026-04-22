package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜谱表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "toc_recipe", schema = "toc")
@Schema(description = "菜谱")
public class TocRecipe extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
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

    @Schema(description = "难度：简单/中等/困难")
    private String difficulty;

    @Schema(description = "制作时间")
    private String time;

    @Schema(description = "几人份")
    private Integer servings;
}
