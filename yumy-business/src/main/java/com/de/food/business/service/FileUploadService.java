package com.de.food.business.service;

import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.common.vo.UploadVO;
import com.de.food.business.entity.SysFile;
import com.de.food.business.mapper.SysFileMapper;
import com.de.food.framework.config.FileUploadConfig;
import com.de.food.framework.util.SecurityUtils;
import com.de.food.common.entity.UserType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 文件上传服务
 * <p>
 * 将文件保存到项目内部 .upload 目录下，按日期分目录存储。
 * 文件名采用 UUID + 原始扩展名策略，避免重名冲突。
 * 上传成功后记录到 sys_file 表。
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final FileUploadConfig uploadConfig;
    private final SysFileMapper sysFileMapper;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    /**
     * 上传单个文件
     *
     * @param file 上传的文件
     * @return 上传结果
     */
    @Transactional(rollbackFor = Exception.class)
    public UploadVO upload(MultipartFile file) {
        validateFile(file);

        // 防御性校验：上传接口必须登录
        if (!SecurityUtils.isAuthenticated()) {
            throw new BizException(ErrorCode.UNAUTHORIZED);
        }

        // 清理原始文件名中的路径部分，防止路径遍历
        String originalName = sanitizeFilename(file.getOriginalFilename());
        String extension = getExtension(originalName);

        // 按日期分目录：.upload/2026/04/22/
        String datePath = LocalDate.now().format(DATE_FORMATTER);
        String storedName = UUID.randomUUID() + "." + extension;
        String relativePath = datePath + "/" + storedName;

        Path uploadDir = Paths.get(uploadConfig.getBasePath(), datePath);
        Path targetFile = uploadDir.resolve(storedName);

        try {
            // 创建目录（如不存在）
            Files.createDirectories(uploadDir);
            // 写入文件
            file.transferTo(targetFile.toFile());
        } catch (IOException e) {
            log.error("文件保存失败, relativePath={}", relativePath, e);
            throw new BizException(ErrorCode.INTERNAL_ERROR, "文件保存失败");
        }

        try {
            // 判断上传来源模块
            String module = resolveModule();

            // 入库记录
            String fileUrl = uploadConfig.getUrlPrefix() + "/" + relativePath;
            SysFile sysFile = new SysFile();
            sysFile.setOriginalName(originalName);
            sysFile.setStoredName(storedName);
            sysFile.setFilePath(relativePath);
            sysFile.setFileUrl(fileUrl);
            sysFile.setFileSize(file.getSize());
            sysFile.setFileType(extension);
            sysFile.setModule(module);
            sysFileMapper.insert(sysFile);

            log.info("文件上传成功, fileId={}, originalName={}, module={}", sysFile.getFileId(), originalName, module);

            UploadVO vo = new UploadVO();
            vo.setFileId(String.valueOf(sysFile.getFileId()));
            vo.setUrl(fileUrl);
            vo.setOriginalName(originalName);
            vo.setStoredName(storedName);
            vo.setSize(file.getSize());
            return vo;
        } catch (Exception e) {
            // 入库失败时清理已写入的磁盘文件，避免孤儿文件
            try {
                Files.deleteIfExists(targetFile);
                log.warn("入库失败，已清理磁盘文件, relativePath={}", relativePath);
            } catch (IOException deleteEx) {
                log.error("清理磁盘文件失败, relativePath={}", relativePath, deleteEx);
            }
            throw e;
        }
    }

    /**
     * 判断当前上传来源模块
     */
    private String resolveModule() {
        UserType userType = SecurityUtils.getUserType();
        if (userType != null) {
            return userType.name().toLowerCase();
        }
        return "unknown";
    }

    /**
     * 校验上传文件
     */
    private void validateFile(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new BizException(ErrorCode.UPLOAD_FILE_EMPTY);
        }

        // 校验文件大小
        if (file.getSize() > uploadConfig.getMaxFileSizeBytes()) {
            throw new BizException(ErrorCode.UPLOAD_FILE_SIZE_EXCEEDED);
        }

        // 校验文件扩展名
        String extension = getExtension(file.getOriginalFilename());
        if (!uploadConfig.isAllowedExtension(extension)) {
            throw new BizException(ErrorCode.UPLOAD_FILE_TYPE_NOT_ALLOWED);
        }
    }

    /**
     * 清理文件名中的路径部分，防止路径遍历攻击
     * <p>例如 "../../etc/passwd" → "passwd"</p>
     */
    private String sanitizeFilename(String filename) {
        if (!StringUtils.hasText(filename)) {
            return "unknown";
        }
        return Paths.get(filename).getFileName().toString();
    }

    /**
     * 获取文件扩展名
     */
    private String getExtension(String filename) {
        if (!StringUtils.hasText(filename)) {
            return "";
        }
        int dotIndex = filename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == filename.length() - 1) {
            return "";
        }
        return filename.substring(dotIndex + 1).toLowerCase();
    }
}
