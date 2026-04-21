package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 字典类型新增 DTO
 */
@Data
@Schema(description = "字典类型新增参数")
public class SysDictTypeCreateDTO {

    @NotBlank(message = "字典名称不能为空")
    @Size(max = 128, message = "字典名称最长128个字符")
    @Schema(description = "字典名称")
    private String dictName;

    @NotBlank(message = "字典类型不能为空")
    @Size(max = 128, message = "字典类型最长128个字符")
    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
