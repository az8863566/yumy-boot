package com.de.food.framework.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 文件上传配置
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "upload")
public class FileUploadConfig {

    /**
     * 上传文件存储根路径（绝对路径或相对于项目根目录）
     */
    private String basePath = ".upload";

    /**
     * 文件访问URL前缀
     */
    private String urlPrefix = "/upload";

    /**
     * 单个文件最大大小（MB）
     */
    private int maxFileSizeMb = 10;

    /**
     * 允许的文件扩展名（小写，逗号分隔）
     */
    private String allowedExtensions = "jpg,jpeg,png,gif,webp,bmp,svg,mp4,mp3,pdf,doc,docx,xls,xlsx,ppt,pptx";

    /**
     * 扩展名白名单缓存（自动从 allowedExtensions 解析）
     */
    private Set<String> allowedExtensionSet;

    /**
     * 设置允许的扩展名时自动构建缓存
     */
    public void setAllowedExtensions(String allowedExtensions) {
        this.allowedExtensions = allowedExtensions;
        this.allowedExtensionSet = Arrays.stream(allowedExtensions.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .collect(Collectors.toUnmodifiableSet());
    }

    /**
     * 是否允许的扩展名
     */
    public boolean isAllowedExtension(String extension) {
        if (extension == null || allowedExtensionSet == null) {
            return false;
        }
        return allowedExtensionSet.contains(extension.toLowerCase());
    }

    /**
     * 获取最大文件大小（字节）
     */
    public long getMaxFileSizeBytes() {
        return (long) maxFileSizeMb * 1024 * 1024;
    }
}
