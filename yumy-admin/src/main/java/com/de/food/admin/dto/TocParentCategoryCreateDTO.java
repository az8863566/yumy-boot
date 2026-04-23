package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 父分类新增 DTO
 */
@Data
@Schema(description = "父分类新增参数")
public class TocParentCategoryCreateDTO {

    @NotBlank(message = "分类名称不能为空")
    @Size(max = 50, message = "分类名称最长50个字符")
    @Schema(description = "分类名称")
    private String name;

    @Schema(description = "排序顺序")
    private Integer sortOrder;
}
