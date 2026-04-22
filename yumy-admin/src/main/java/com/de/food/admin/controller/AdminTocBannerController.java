package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.TocBannerCreateDTO;
import com.de.food.admin.dto.TocBannerQueryDTO;
import com.de.food.admin.dto.TocBannerUpdateDTO;
import com.de.food.admin.service.TocBannerService;
import com.de.food.admin.vo.TocBannerVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 轮播图管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/banner")
@RequiredArgsConstructor
@Tag(name = "轮播图管理")
public class AdminTocBannerController {

    private final TocBannerService tocBannerService;

    @Operation(summary = "分页查询轮播图")
    @PreAuthorize("hasAuthority('toc:banner:list')")
    @GetMapping("/page")
    public Result<IPage<TocBannerVO>> page(@Valid TocBannerQueryDTO queryDTO) {
        return Result.ok(tocBannerService.page(queryDTO));
    }

    @Operation(summary = "查询轮播图详情")
    @PreAuthorize("hasAuthority('toc:banner:detail')")
    @GetMapping("/{bannerId}")
    public Result<TocBannerVO> getDetail(@PathVariable Long bannerId) {
        return Result.ok(tocBannerService.getDetail(bannerId));
    }

    @Operation(summary = "新增轮播图")
    @PreAuthorize("hasAuthority('toc:banner:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody TocBannerCreateDTO dto) {
        tocBannerService.createBanner(dto);
        return Result.ok();
    }

    @Operation(summary = "修改轮播图")
    @PreAuthorize("hasAuthority('toc:banner:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody TocBannerUpdateDTO dto) {
        tocBannerService.updateBanner(dto);
        return Result.ok();
    }

    @Operation(summary = "删除轮播图")
    @PreAuthorize("hasAuthority('toc:banner:delete')")
    @DeleteMapping("/{bannerId}")
    public Result<Void> delete(@PathVariable Long bannerId) {
        tocBannerService.deleteBanner(bannerId);
        return Result.ok();
    }
}
