package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 首页轮播图 VO
 */
@Data
@Schema(description = "首页轮播图")
public class TocBannerVO {

    @Schema(description = "轮播图ID")
    private String bannerId;

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
}
