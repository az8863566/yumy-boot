package com.de.food.toc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * C端用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("toc_user")
@Schema(description = "C端用户")
public class TocUser extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "密码")
    private String password;

    @Schema(description = "头像")
    private String avatar;
}
