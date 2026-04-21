package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 角色修改 DTO
 */
@Data
@Schema(description = "角色修改参数")
public class SysRoleUpdateDTO {

    @NotNull(message = "角色ID不能为空")
    @Schema(description = "角色ID")
    private Long roleId;

    @NotBlank(message = "角色名称不能为空")
    @Size(max = 64, message = "角色名称最长64个字符")
    @Schema(description = "角色名称")
    private String roleName;

    @NotBlank(message = "角色权限字符串不能为空")
    @Size(max = 64, message = "角色权限字符串最长64个字符")
    @Schema(description = "角色权限字符串")
    private String roleCode;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
