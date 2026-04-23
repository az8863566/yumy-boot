package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.TocRecipeCreateDTO;
import com.de.food.admin.dto.TocRecipeQueryDTO;
import com.de.food.admin.dto.TocRecipeRecommendDTO;
import com.de.food.admin.dto.TocRecipeUpdateDTO;
import com.de.food.admin.service.AdminTocRecipeService;
import com.de.food.admin.vo.TocRecipeDetailVO;
import com.de.food.admin.vo.TocRecipeVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 菜谱管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/recipe")
@RequiredArgsConstructor
@Tag(name = "菜谱管理")
public class AdminTocRecipeController {

    private final AdminTocRecipeService adminTocRecipeService;

    @Operation(summary = "分页查询菜谱")
    @PreAuthorize("hasAuthority('toc:recipe:list')")
    @GetMapping("/page")
    public Result<IPage<TocRecipeVO>> page(@Valid TocRecipeQueryDTO queryDTO) {
        return Result.ok(adminTocRecipeService.page(queryDTO));
    }

    @Operation(summary = "查询菜谱详情")
    @PreAuthorize("hasAuthority('toc:recipe:detail')")
    @GetMapping("/{recipeId}")
    public Result<TocRecipeDetailVO> getDetail(@PathVariable Long recipeId) {
        return Result.ok(adminTocRecipeService.getDetail(recipeId));
    }

    @Operation(summary = "新增菜谱")
    @PreAuthorize("hasAuthority('toc:recipe:add')")
    @PostMapping
    public Result<Void> create(@Valid @RequestBody TocRecipeCreateDTO dto) {
        adminTocRecipeService.createRecipe(dto);
        return Result.ok();
    }

    @Operation(summary = "修改菜谱")
    @PreAuthorize("hasAuthority('toc:recipe:edit')")
    @PutMapping
    public Result<Void> update(@Valid @RequestBody TocRecipeUpdateDTO dto) {
        adminTocRecipeService.updateRecipe(dto);
        return Result.ok();
    }

    @Operation(summary = "删除菜谱")
    @PreAuthorize("hasAuthority('toc:recipe:delete')")
    @DeleteMapping("/{recipeId}")
    public Result<Void> delete(@PathVariable Long recipeId) {
        adminTocRecipeService.deleteRecipe(recipeId);
        return Result.ok();
    }

    @Operation(summary = "设置推荐排序")
    @PreAuthorize("hasAuthority('toc:recipe:edit')")
    @PutMapping("/{recipeId}/recommend")
    public Result<Void> setRecommendSort(@PathVariable Long recipeId,
                                          @Valid @RequestBody TocRecipeRecommendDTO dto) {
        adminTocRecipeService.setRecommendSort(recipeId, dto);
        return Result.ok();
    }
}
