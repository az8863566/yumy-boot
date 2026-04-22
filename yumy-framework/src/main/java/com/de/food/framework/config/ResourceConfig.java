package com.de.food.framework.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

/**
 * 静态资源映射配置
 * <p>
 * 将 /upload/** 请求映射到 .upload 本地目录，使上传的文件可通过 HTTP 直接访问
 */
@Configuration
@RequiredArgsConstructor
public class ResourceConfig implements WebMvcConfigurer {

    private final FileUploadConfig uploadConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String absolutePath = Paths.get(uploadConfig.getBasePath()).toAbsolutePath().normalize().toString();
        registry.addResourceHandler(uploadConfig.getUrlPrefix() + "/**")
                .addResourceLocations("file:" + absolutePath + "/");
    }
}
