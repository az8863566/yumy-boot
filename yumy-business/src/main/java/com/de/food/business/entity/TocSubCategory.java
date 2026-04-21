package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 子分类表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("toc_sub_category")
@Schema(description = "子分类")
public class TocSubCategory extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "子分类ID")
    private Long categoryId;

    @Schema(description = "父分类ID")
    private Long parentId;

    @Schema(description = "子分类名称")
    private String name;

    @Schema(description = "分类封面图URL")
    private String image;

    @Schema(description = "排序顺序")
    private Integer sortOrder;
}
