package com.de.food.common.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 实体公共基类
 * <p>
 * 包含雪花ID主键、逻辑删除标志、审计字段，所有业务实体必须继承此类。
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 逻辑删除标志（0 正常，1 删除）
     */
    @TableLogic
    private Integer deleted;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 创建人标识
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 更新人标识
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateBy;
}
