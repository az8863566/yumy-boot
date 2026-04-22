package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 轮播图新增 DTO
 */
@Data
@Schema(description = "轮播图新增参数")
public class TocBannerCreateDTO {

    @NotBlank(message = "轮播图标题不能为空")
    @Size(max = 100, message = "轮播图标题最长100个字符")
    @Schema(description = "轮播图标题")
    private String title;

    @Size(max = 200, message = "副标题最长200个字符")
    @Schema(description = "副标题/描述")
    private String subtitle;

    @NotBlank(message = "轮播图图片URL不能为空")
    @Size(max = 500, message = "图片URL最长500个字符")
    @Schema(description = "轮播图图片URL")
    private String image;

    @Schema(description = "跳转类型(0无跳转 1菜谱详情 2外部链接)")
    private Integer linkType;

    @Size(max = 500, message = "跳转目标最长500个字符")
    @Schema(description = "跳转目标(菜谱ID或URL)")
    private String linkValue;

    @Schema(description = "排序顺序(越小越靠前)")
    private Integer sortOrder;

    @NotNull(message = "状态不能为空")
    @Schema(description = "状态(1启用 0停用)")
    private Integer status;
}
