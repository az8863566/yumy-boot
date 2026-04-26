package com.de.food.toc.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 评论 VO
 */
@Data
@Schema(description = "评论")
public class TocCommentVO {

    @Schema(description = "评论ID")
    private String commentId;

    @Schema(description = "菜谱ID")
    private String recipeId;

    @Schema(description = "用户ID")
    private String userId;

    @Schema(description = "用户名")
    private String username;

    @Schema(description = "用户头像")
    private String avatar;

    @Schema(description = "评论内容")
    private String text;

    @Schema(description = "评论图片URL数组")
    private List<String> images;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "评论时间")
    private LocalDateTime createTime;
}
