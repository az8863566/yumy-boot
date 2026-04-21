package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 菜单权限表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_menu")
@Schema(description = "菜单权限")
public class SysMenu extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "菜单ID")
    private Long menuId;

    @Schema(description = "父菜单ID")
    private Long parentId;

    @Schema(description = "菜单名称")
    private String menuName;

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
