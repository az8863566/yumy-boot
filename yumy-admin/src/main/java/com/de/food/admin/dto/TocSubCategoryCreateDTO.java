package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 子分类新增 DTO
 */
@Data
@Schema(description = "子分类新增参数")
public class TocSubCategoryCreateDTO {

    @NotNull(message = "父分类ID不能为空")
    @Schema(description = "父分类ID")
    private Long parentId;

    @NotBlank(message = "子分类名称不能为空")
    @Size(max = 50, message = "子分类名称最长50个字符")
    @Schema(description = "子分类名称")
    private String name;

    @NotBlank(message = "分类封面图URL不能为空")
    @Size(max = 500, message = "封面图URL最长500个字符")
    @Schema(description = "分类封面图URL")
    private String image;

    @Schema(description = "排序顺序")
    private Integer sortOrder;
}
