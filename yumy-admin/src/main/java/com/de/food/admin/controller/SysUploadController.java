package com.de.food.admin.controller;

import com.de.food.common.result.Result;
import com.de.food.common.vo.UploadVO;
import com.de.food.business.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * 后台管理-文件上传 Controller
 */
@RestController
@RequestMapping("/admin/v1/upload")
@RequiredArgsConstructor
@Tag(name = "后台管理-文件上传")
public class SysUploadController {

    private final FileUploadService fileUploadService;

    @Operation(summary = "上传文件")
    @PostMapping
    @PreAuthorize("hasAuthority('system:upload:add')")
    public Result<UploadVO> upload(@RequestPart("file") MultipartFile file) {
        return Result.ok(fileUploadService.upload(file));
    }
}
