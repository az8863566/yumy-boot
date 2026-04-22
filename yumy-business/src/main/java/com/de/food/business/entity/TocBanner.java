package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 首页轮播图表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "toc_banner", schema = "toc")
@Schema(description = "首页轮播图")
public class TocBanner extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "轮播图ID")
    private Long bannerId;

    @Schema(description = "轮播图标题")
    private String title;

    @Schema(description = "副标题/描述")
    private String subtitle;

    @Schema(description = "轮播图图片URL")
    private String image;

    @Schema(description = "跳转类型(0无跳转 1菜谱详情 2外部链接)")
    private Integer linkType;

    @Schema(description = "跳转目标(菜谱ID或URL)")
    private String linkValue;

    @Schema(description = "排序顺序(越小越靠前)")
    private Integer sortOrder;

    @Schema(description = "状态(1启用 0停用)")
    private Integer status;
}
