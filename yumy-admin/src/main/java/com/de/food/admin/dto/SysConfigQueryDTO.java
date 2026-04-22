package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 参数配置查询 DTO
 */
@Data
@Schema(description = "参数配置查询参数")
public class SysConfigQueryDTO {

    @Schema(description = "参数名称(模糊)")
    private String configName;

    @Schema(description = "参数键名(模糊)")
    private String configKey;

    @Schema(description = "系统内置(1是 2否)")
    private Integer configType;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
