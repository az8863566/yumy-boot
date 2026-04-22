package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.SysOperLogQueryDTO;
import com.de.food.admin.service.SysOperLogService;
import com.de.food.admin.vo.SysOperLogVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 操作日志 Controller
 */
@RestController
@RequestMapping("/admin/v1/oper-log")
@RequiredArgsConstructor
@Tag(name = "操作日志")
public class SysOperLogController {

    private final SysOperLogService sysOperLogService;

    @Operation(summary = "分页查询操作日志")
    @PreAuthorize("hasAuthority('system:operLog:list')")
    @GetMapping("/page")
    public Result<IPage<SysOperLogVO>> page(@Valid SysOperLogQueryDTO queryDTO) {
        return Result.ok(sysOperLogService.page(queryDTO));
    }
}
