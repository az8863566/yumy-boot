package com.de.food.admin.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 字典数据表
 * <p>
 * 无 deleted 字段，不继承 BaseEntity
 */
@Data
@TableName("sys_dict_data")
@Schema(description = "字典数据")
public class SysDictData implements Serializable {

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

    @Schema(description = "创建人")
    @TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    private String createBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "创建时间")
    @TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT)
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    @TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE)
    private String updateBy;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "更新时间")
    @TableField(fill = com.baomidou.mybatisplus.annotation.FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    @Schema(description = "备注")
    private String remark;
}
