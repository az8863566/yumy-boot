package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * C端用户状态切换 DTO
 */
@Data
@Schema(description = "C端用户状态切换参数")
public class TocUserStatusDTO {

    @NotNull(message = "状态不能为空")
    @Min(value = 0, message = "状态值只能为0或1")
    @Max(value = 1, message = "状态值只能为0或1")
    @Schema(description = "状态(1正常 0禁用)")
    private Integer status;
}
