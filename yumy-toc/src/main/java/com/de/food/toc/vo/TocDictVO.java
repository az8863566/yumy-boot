package com.de.food.toc.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 字典数据 VO（C端）
 */
@Data
@Schema(description = "字典数据")
public class TocDictVO {

    @Schema(description = "字典标签(展示值)")
    private String dictLabel;

    @Schema(description = "字典键值(实际值)")
    private String dictValue;
}
