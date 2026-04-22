package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import lombok.Data;

/**
 * 角色查询 DTO
 */
@Data
@Schema(description = "角色查询参数")
public class SysRoleQueryDTO {

    @Schema(description = "角色名称(模糊)")
    private String roleName;

    @Schema(description = "角色权限字符串")
    private String roleCode;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "页码", example = "1")
    private Long pageNum = 1L;

    @Max(value = 200, message = "每页条数最大200")
    @Schema(description = "每页条数", example = "10")
    private Long pageSize = 10L;
}
