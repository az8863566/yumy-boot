package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 父分类 VO（含子分类列表）
 */
@Data
@Schema(description = "父分类")
public class TocCategoryVO {

    @Schema(description = "分类ID")
    private String id;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "子分类列表")
    private List<TocSubCategoryVO> subCategories;

    /**
     * 子分类 VO
     */
    @Data
    @Schema(description = "子分类")
    public static class TocSubCategoryVO {

        @Schema(description = "子分类ID")
        private String id;

        @Schema(description = "父分类ID")
        private String parentId;

        @Schema(description = "子分类名称")
        private String name;

        @Schema(description = "分类封面图URL")
        private String image;
    }
}
