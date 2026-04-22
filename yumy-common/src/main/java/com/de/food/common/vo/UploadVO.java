package com.de.food.common.vo;

import lombok.Data;

/**
 * 文件上传结果 VO
 */
@Data
public class UploadVO {

    /** 文件记录ID */
    private Long fileId;

    /** 文件访问URL */
    private String url;

    /** 原始文件名 */
    private String originalName;

    /** 存储文件名 */
    private String storedName;

    /** 文件大小（字节） */
    private Long size;
}
