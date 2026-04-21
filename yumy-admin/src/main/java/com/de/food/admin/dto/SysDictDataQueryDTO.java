package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典数据查询 DTO
 */
@Data
@Schema(description = "字典数据查询参数")
public class SysDictDataQueryDTO {

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "字典标签(模糊)")
    private String dictLabel;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
