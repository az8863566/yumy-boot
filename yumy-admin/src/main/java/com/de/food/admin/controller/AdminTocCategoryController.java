package com.de.food.admin.controller;

import com.de.food.admin.dto.TocParentCategoryCreateDTO;
import com.de.food.admin.dto.TocParentCategoryUpdateDTO;
import com.de.food.admin.dto.TocSubCategoryCreateDTO;
import com.de.food.admin.dto.TocSubCategoryUpdateDTO;
import com.de.food.admin.service.AdminTocCategoryService;
import com.de.food.admin.vo.TocCategoryTreeVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 分类管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/category")
@RequiredArgsConstructor
@Tag(name = "分类管理")
public class AdminTocCategoryController {

    private final AdminTocCategoryService adminTocCategoryService;

    @Operation(summary = "获取分类树")
    @PreAuthorize("hasAuthority('toc:category:list')")
    @GetMapping("/tree")
    public Result<List<TocCategoryTreeVO>> tree() {
        return Result.ok(adminTocCategoryService.getCategoryTree());
    }

    @Operation(summary = "新增父分类")
    @PreAuthorize("hasAuthority('toc:category:add')")
    @PostMapping("/parent")
    public Result<Void> createParent(@Valid @RequestBody TocParentCategoryCreateDTO dto) {
        adminTocCategoryService.createParentCategory(dto);
        return Result.ok();
    }

    @Operation(summary = "修改父分类")
    @PreAuthorize("hasAuthority('toc:category:edit')")
    @PutMapping("/parent")
    public Result<Void> updateParent(@Valid @RequestBody TocParentCategoryUpdateDTO dto) {
        adminTocCategoryService.updateParentCategory(dto);
        return Result.ok();
    }

    @Operation(summary = "删除父分类")
    @PreAuthorize("hasAuthority('toc:category:delete')")
    @DeleteMapping("/parent/{categoryId}")
    public Result<Void> deleteParent(@PathVariable Long categoryId) {
        adminTocCategoryService.deleteParentCategory(categoryId);
        return Result.ok();
    }

    @Operation(summary = "新增子分类")
    @PreAuthorize("hasAuthority('toc:category:add')")
    @PostMapping("/sub")
    public Result<Void> createSub(@Valid @RequestBody TocSubCategoryCreateDTO dto) {
        adminTocCategoryService.createSubCategory(dto);
        return Result.ok();
    }

    @Operation(summary = "修改子分类")
    @PreAuthorize("hasAuthority('toc:category:edit')")
    @PutMapping("/sub")
    public Result<Void> updateSub(@Valid @RequestBody TocSubCategoryUpdateDTO dto) {
        adminTocCategoryService.updateSubCategory(dto);
        return Result.ok();
    }

    @Operation(summary = "删除子分类")
    @PreAuthorize("hasAuthority('toc:category:delete')")
    @DeleteMapping("/sub/{categoryId}")
    public Result<Void> deleteSub(@PathVariable Long categoryId) {
        adminTocCategoryService.deleteSubCategory(categoryId);
        return Result.ok();
    }
}
