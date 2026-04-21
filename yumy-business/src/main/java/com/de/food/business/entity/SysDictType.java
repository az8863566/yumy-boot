package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("sys_dict_type")
@Schema(description = "字典类型")
public class SysDictType extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "字典ID")
    private Long dictId;

    @Schema(description = "字典名称")
    private String dictName;

    @Schema(description = "字典类型")
    private String dictType;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
