package com.de.food.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 菜谱列表 VO
 */
@Data
@Schema(description = "菜谱列表项")
public class TocRecipeVO {

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

    @Schema(description = "推荐排序(0不推荐,>0推荐)")
    private Integer recommendSort;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
