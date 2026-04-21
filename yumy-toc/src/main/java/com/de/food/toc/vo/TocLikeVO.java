package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 点赞结果 VO
 */
@Data
@Schema(description = "点赞结果")
public class TocLikeVO {

    @Schema(description = "是否已点赞")
    private Boolean liked;

    @Schema(description = "当前菜谱点赞数")
    private Integer likes;
}
