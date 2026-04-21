package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 收藏结果 VO
 */
@Data
@Schema(description = "收藏结果")
public class TocFavoriteVO {

    @Schema(description = "是否已收藏")
    private Boolean favorited;
}
