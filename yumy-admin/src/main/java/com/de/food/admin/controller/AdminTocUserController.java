package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.TocUserQueryDTO;
import com.de.food.admin.dto.TocUserStatusDTO;
import com.de.food.admin.service.AdminTocUserService;
import com.de.food.admin.vo.TocUserVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * C端用户管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/toc-user")
@RequiredArgsConstructor
@Tag(name = "C端用户管理")
public class AdminTocUserController {

    private final AdminTocUserService adminTocUserService;

    @Operation(summary = "分页查询C端用户")
    @PreAuthorize("hasAuthority('toc:user:list')")
    @GetMapping("/page")
    public Result<IPage<TocUserVO>> page(@Valid TocUserQueryDTO queryDTO) {
        return Result.ok(adminTocUserService.page(queryDTO));
    }

    @Operation(summary = "查询C端用户详情")
    @PreAuthorize("hasAuthority('toc:user:detail')")
    @GetMapping("/{userId}")
    public Result<TocUserVO> getDetail(@PathVariable Long userId) {
        return Result.ok(adminTocUserService.getDetail(userId));
    }

    @Operation(summary = "切换C端用户状态")
    @PreAuthorize("hasAuthority('toc:user:edit')")
    @PutMapping("/{userId}/status")
    public Result<Void> updateStatus(@PathVariable Long userId,
                                      @Valid @RequestBody TocUserStatusDTO dto) {
        adminTocUserService.updateStatus(userId, dto);
        return Result.ok();
    }
}
