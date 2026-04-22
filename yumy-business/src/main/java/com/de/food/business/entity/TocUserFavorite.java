package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户收藏表
 * <p>
 * 关联表，不继承 BaseEntity，使用物理删除，避免逻辑删除与唯一约束冲突。
 * 唯一约束：uk_toc_user_favorite_user_id_recipe_id (user_id, recipe_id)，
 * 确保同一用户对同一菜谱只能收藏一次。
 */
@Data
@TableName(value = "toc_user_favorite", schema = "toc")
@Schema(description = "用户收藏")
public class TocUserFavorite implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "收藏ID")
    private Long favoriteId;

    @Schema(description = "用户ID")
    private Long userId;

    @Schema(description = "菜谱ID")
    private Long recipeId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
}
