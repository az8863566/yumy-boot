package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户收藏表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("toc_user_favorite")
@Schema(description = "用户收藏")
public class TocUserFavorite extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "收藏ID")
    private Long favoriteId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "菜谱ID")
    private Long recipeId;
}
