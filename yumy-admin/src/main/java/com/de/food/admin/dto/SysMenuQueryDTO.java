package com.de.food.admin.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 菜单查询 DTO
 */
@Data
@Schema(description = "菜单查询参数")
public class SysMenuQueryDTO {

    @Schema(description = "菜单名称(模糊)")
    private String menuName;

    @Schema(description = "菜单类型(1目录 2菜单 3按钮)")
    private Integer menuType;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;
}
