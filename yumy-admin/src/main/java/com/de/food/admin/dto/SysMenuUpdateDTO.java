package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 菜单修改 DTO
 */
@Data
@Schema(description = "菜单修改参数")
public class SysMenuUpdateDTO {

    @NotNull(message = "菜单ID不能为空")
    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @NotBlank(message = "菜单名称不能为空")
    @Size(max = 64, message = "菜单名称最长64个字符")
    @Schema(description = "菜单名称")
    private String menuName;

    @NotNull(message = "菜单类型不能为空")
    @Schema(description = "菜单类型(1目录 2菜单 3按钮)")
    private Integer menuType;

    @Schema(description = "路由地址")
    private String path;

    @Schema(description = "组件路径")
    private String component;

    @Schema(description = "权限标识")
    private String perms;

    @Schema(description = "菜单图标")
    private String icon;

    @Schema(description = "显示顺序")
    private Integer sortOrder;

    @Schema(description = "菜单显示状态(1显示 0隐藏)")
    private Integer visible;

    @Schema(description = "菜单可用状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
