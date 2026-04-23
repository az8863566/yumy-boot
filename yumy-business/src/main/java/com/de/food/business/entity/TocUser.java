package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * C端用户表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "toc_user", schema = "toc")
@Schema(description = "C端用户")
public class TocUser extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "用户名")
    private String username;

    @JsonIgnore
    @Schema(description = "密码")
    private String password;

    @Schema(description = "昵称")
    private String nickname;

    @Schema(description = "头像")
    private String avatar;

    @Schema(description = "签名")
    private String signature;

    @Schema(description = "状态(1正常 0禁用)")
    private Integer status = 1;
}
