package com.de.food.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 轮播图 VO
 */
@Data
@Schema(description = "轮播图")
public class TocBannerVO {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;
}
