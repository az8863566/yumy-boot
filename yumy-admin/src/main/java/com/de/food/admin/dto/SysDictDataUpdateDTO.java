package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 字典数据修改 DTO
 */
@Data
@Schema(description = "字典数据修改参数")
public class SysDictDataUpdateDTO {

    @NotNull(message = "字典编码不能为空")
    @Schema(description = "字典编码")
    private Long dictCode;

    @Schema(description = "字典排序")
    private Integer dictSort;

    @NotBlank(message = "字典标签不能为空")
    @Size(max = 128, message = "字典标签最长128个字符")
    @Schema(description = "字典标签(展示值)")
    private String dictLabel;

    @NotBlank(message = "字典键值不能为空")
    @Size(max = 128, message = "字典键值最长128个字符")
    @Schema(description = "字典键值(实际值)")
    private String dictValue;

    @NotBlank(message = "字典类型不能为空")
    @Size(max = 128, message = "字典类型最长128个字符")
    @Schema(description = "字典类型(关联sys_dict_type)")
    private String dictType;

    @Schema(description = "样式属性")
    private String cssClass;

    @Schema(description = "表格回显样式")
    private String listClass;

    @Schema(description = "是否默认(1是 0否)")
    private Integer isDefault;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
