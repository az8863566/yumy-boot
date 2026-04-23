package com.de.food.admin.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.de.food.admin.dto.TocCommentQueryDTO;
import com.de.food.admin.service.AdminTocCommentService;
import com.de.food.admin.vo.TocCommentVO;
import com.de.food.common.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

/**
 * 评论管理 Controller
 */
@RestController
@RequestMapping("/admin/v1/comment")
@RequiredArgsConstructor
@Tag(name = "评论管理")
public class AdminTocCommentController {

    private final AdminTocCommentService adminTocCommentService;

    @Operation(summary = "分页查询评论")
    @PreAuthorize("hasAuthority('toc:comment:list')")
    @GetMapping("/page")
    public Result<IPage<TocCommentVO>> page(@Valid TocCommentQueryDTO queryDTO) {
        return Result.ok(adminTocCommentService.page(queryDTO));
    }

    @Operation(summary = "查询评论详情")
    @PreAuthorize("hasAuthority('toc:comment:detail')")
    @GetMapping("/{commentId}")
    public Result<TocCommentVO> getDetail(@PathVariable Long commentId) {
        return Result.ok(adminTocCommentService.getDetail(commentId));
    }

    @Operation(summary = "删除评论")
    @PreAuthorize("hasAuthority('toc:comment:delete')")
    @DeleteMapping("/{commentId}")
    public Result<Void> delete(@PathVariable Long commentId) {
        adminTocCommentService.deleteComment(commentId);
        return Result.ok();
    }
}
