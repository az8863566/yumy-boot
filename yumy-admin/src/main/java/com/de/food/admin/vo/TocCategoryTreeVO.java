package com.de.food.admin.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 分类树 VO
 */
@Data
@Schema(description = "分类树")
public class TocCategoryTreeVO {

    @Schema(description = "分类ID")
    private String categoryId;

    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序顺序")
    private Integer sortOrder;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "子分类列表")
    private List<TocSubCategoryItemVO> subCategories;

    /**
     * 子分类项 VO
     */
    @Data
    @Schema(description = "子分类")
    public static class TocSubCategoryItemVO {

        @Schema(description = "子分类ID")
        private String categoryId;

        @Schema(description = "父分类ID")
        private String parentId;

        @Schema(description = "子分类名称")
        private String name;

        @Schema(description = "分类封面图URL")
        private String image;

        @Schema(description = "排序顺序")
        private Integer sortOrder;

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        @Schema(description = "创建时间")
        private LocalDateTime createTime;
    }
}
