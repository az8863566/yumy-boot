package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 评论表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "toc_comment", schema = "toc")
@Schema(description = "评论")
public class TocComment extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "评论ID")
    private Long commentId;

    @Schema(description = "所属菜谱ID")
    private Long recipeId;

    @Schema(description = "评论用户ID")
    private Long userId;

    @Schema(description = "评论内容")
    private String text;

    @TableField(typeHandler = JacksonTypeHandler.class)
    @Schema(description = "评论图片URL数组")
    private List<String> images;
}
