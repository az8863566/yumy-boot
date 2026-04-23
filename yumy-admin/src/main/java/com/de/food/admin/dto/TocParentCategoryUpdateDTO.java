package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 父分类修改 DTO
 */
@Data
@Schema(description = "父分类修改参数")
public class TocParentCategoryUpdateDTO {

    @NotNull(message = "分类ID不能为空")
    @Schema(description = "分类ID")
    private Long categoryId;

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称最长50个字符")
    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序顺序")
    private Integer sortOrder;
}
