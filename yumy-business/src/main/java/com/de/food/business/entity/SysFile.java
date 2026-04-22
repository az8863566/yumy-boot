package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.de.food.common.entity.BaseEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 文件上传记录表
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName(value = "sys_file", schema = "admin")
@Schema(description = "文件上传记录")
public class SysFile extends BaseEntity {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "文件ID")
    private Long fileId;

    @Schema(description = "原始文件名")
    private String originalName;

    @Schema(description = "存储文件名")
    private String storedName;

    @Schema(description = "文件相对路径")
    private String filePath;

    @Schema(description = "文件访问URL")
    private String fileUrl;

    @Schema(description = "文件大小（字节）")
    private Long fileSize;

    @Schema(description = "文件扩展名")
    private String fileType;

    @Schema(description = "上传来源模块(admin/toc)")
    private String module;
}
