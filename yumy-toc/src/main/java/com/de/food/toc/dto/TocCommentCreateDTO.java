package com.de.food.toc.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

/**
 * 发表评论 DTO
 */
@Data
@Schema(description = "发表评论参数")
public class TocCommentCreateDTO {

    @NotBlank(message = "评论内容不能为空")
    @Size(max = 2000, message = "评论内容最长2000个字符")
    @Schema(description = "评论内容")
    private String text;

    @Size(max = 9, message = "图片数量最多9张")
    @Schema(description = "评论图片URL数组")
    private List<String> images;
}
