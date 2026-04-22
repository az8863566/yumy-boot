package com.de.food.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志记录表
 */
@Data
@TableName(value = "sys_oper_log", schema = "admin")
@Schema(description = "操作日志")
public class SysOperLog implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    @Schema(description = "日志ID")
    private Long operId;

    @Schema(description = "模块标题")
    private String title;

    @Schema(description = "业务类型(1新增 2修改 3删除 4导出)")
    private Integer businessType;

    @Schema(description = "方法名称")
    private String method;

    @Schema(description = "请求方式")
    private String requestMethod;

    @Schema(description = "操作人员")
    private String operName;

    @Schema(description = "请求URL")
    private String operUrl;

    @Schema(description = "主机地址")
    private String operIp;

    @Schema(description = "请求参数")
    private String operParam;

    @Schema(description = "返回参数")
    private String jsonResult;

    @Schema(description = "操作状态(1正常 0异常)")
    private Integer status;

    @Schema(description = "错误消息")
    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "操作时间")
    private LocalDateTime operTime;

    @Schema(description = "消耗时间(毫秒)")
    private Long costTime;
}
