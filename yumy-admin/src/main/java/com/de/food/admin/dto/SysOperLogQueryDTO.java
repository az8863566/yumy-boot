package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 操作日志查询 DTO
 */
@Data
@Schema(description = "操作日志查询参数")
public class SysOperLogQueryDTO {

    @Schema(description = "模块标题")
    private String title;

    @Schema(description = "业务类型(1新增 2修改 3删除 4导出)")
    private Integer businessType;

    @Schema(description = "操作人员")
    private String operName;

    @Schema(description = "操作状态(1正常 0异常)")
    private Integer status;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
