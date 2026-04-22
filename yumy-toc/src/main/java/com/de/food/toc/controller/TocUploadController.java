package com.de.food.toc.controller;

import com.de.food.common.result.Result;
import com.de.food.common.vo.UploadVO;
import com.de.food.business.service.FileUploadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * C端-文件上传 Controller
 */
@RestController
@RequestMapping("/api/toc/v1/upload")
@RequiredArgsConstructor
@Tag(name = "C端-文件上传")
public class TocUploadController {

    private final FileUploadService fileUploadService;

    @Operation(summary = "上传文件")
    @PostMapping
    public Result<UploadVO> upload(@RequestPart("file") MultipartFile file) {
        return Result.ok(fileUploadService.upload(file));
    }
}
