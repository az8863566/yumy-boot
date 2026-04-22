package com.de.food.toc.controller;

import com.de.food.common.result.Result;
import com.de.food.toc.service.TocBannerService;
import com.de.food.toc.vo.TocBannerVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * C端轮播图 Controller
 */
@RestController
@RequestMapping("/api/toc/v1/banners")
@RequiredArgsConstructor
@Tag(name = "C端-轮播图")
public class TocBannerController {

    private final TocBannerService tocBannerService;

    @Operation(summary = "获取首页轮播图列表")
    @GetMapping
    public Result<List<TocBannerVO>> listEnabled() {
        return Result.ok(tocBannerService.listEnabled());
    }
}
