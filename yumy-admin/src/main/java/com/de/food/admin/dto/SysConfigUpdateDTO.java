package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 参数配置修改 DTO
 */
@Data
@Schema(description = "参数配置修改参数")
public class SysConfigUpdateDTO {

    @NotNull(message = "参数ID不能为空")
    @Schema(description = "参数ID")
    private Long configId;

    @NotBlank(message = "参数名称不能为空")
    @Size(max = 128, message = "参数名称最长128个字符")
    @Schema(description = "参数名称")
    private String configName;

    @NotBlank(message = "参数键名不能为空")
    @Size(max = 128, message = "参数键名最长128个字符")
    @Schema(description = "参数键名")
    private String configKey;

    @Schema(description = "参数键值")
    private String configValue;

    @Schema(description = "系统内置(1是 2否)")
    private Integer configType;

    @Schema(description = "备注")
    private String remark;
}
