package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据表
 */
@EqualsAndHashCode(callSuper = true)
@Data
@TableName(value = "sys_dict_data", schema = "admin")
@Schema(description = "字典数据")
public class SysDictData extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "字典编码")
    private Long dictCode;

    @Schema(description = "字典排序")
    private Integer dictSort;

    @Schema(description = "字典标签(展示值)")
    private String dictLabel;

    @Schema(description = "字典键值(实际值)")
    private String dictValue;

    @Schema(description = "字典类型(关联sys_dict_type)")
    private String dictType;

    @Schema(description = "样式属性")
    private String cssClass;

    @Schema(description = "表格回显样式")
    private String listClass;

    @Schema(description = "是否默认(1是 0否)")
    private Integer isDefault;

    @Schema(description = "状态(1正常 0停用)")
    private Integer status;

    @Schema(description = "备注")
    private String remark;
}
