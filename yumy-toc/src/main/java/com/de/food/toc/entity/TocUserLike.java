package com.de.food.toc.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户点赞表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("toc_user_like")
@Schema(description = "用户点赞")
public class TocUserLike extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "点赞ID")
    private Long likeId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "菜谱ID")
    private Long recipeId;
}
