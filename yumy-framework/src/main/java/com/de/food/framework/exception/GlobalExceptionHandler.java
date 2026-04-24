package com.de.food.framework.exception;

import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.de.food.common.result.Result;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import jakarta.validation.ConstraintViolationException;

/**
 * 全局异常拦截器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常
     */
    @ExceptionHandler(BizException.class)
    public Result<Void> handleBizException(BizException e) {
        log.warn("业务异常: code={}, msg={}", e.getCode(), e.getMessage());
        return Result.fail(e.getCode(), e.getMessage());
    }

    /**
     * 参数校验异常（@RequestBody @Valid 触发）
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Void> handleValidationException(MethodArgumentNotValidException e, HttpServletResponse response) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        log.warn("参数校验异常: {}", message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.fail(ErrorCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 参数绑定异常（表单提交触发）
     */
    @ExceptionHandler(BindException.class)
    public Result<Void> handleBindException(BindException e, HttpServletResponse response) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数绑定失败");
        log.warn("参数绑定异常: {}", message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.fail(ErrorCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 约束校验异常（@Validated 路径参数/查询参数触发）
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<Void> handleConstraintViolationException(ConstraintViolationException e, HttpServletResponse response) {
        String message = e.getConstraintViolations().stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .reduce((a, b) -> a + "; " + b)
                .orElse("参数校验失败");
        log.warn("约束校验异常: {}", message);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.fail(ErrorCode.PARAM_ERROR.getCode(), message);
    }

    /**
     * 请求体不可读（JSON 格式错误、body 为空等）
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<Void> handleHttpMessageNotReadableException(HttpMessageNotReadableException e, HttpServletResponse response) {
        log.warn("请求体解析失败: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.fail(ErrorCode.PARAM_ERROR.getCode(), "请求体解析失败");
    }

    /**
     * 请求方法不支持（如 GET 请求打到了 POST 接口）
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Void> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e, HttpServletResponse response) {
        log.warn("请求方法不支持: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        return Result.fail(ErrorCode.METHOD_NOT_ALLOWED);
    }

    /**
     * 参数类型不匹配（如路径参数 id 传了非数字）
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result<Void> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e, HttpServletResponse response) {
        String name = e.getName();
        String value = e.getValue() != null ? e.getValue().toString() : "null";
        String requiredType = e.getRequiredType() != null ? e.getRequiredType().getSimpleName() : "未知";
        log.warn("参数类型不匹配: {}={}, 期望类型: {}", name, value, requiredType);
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return Result.fail(ErrorCode.PARAM_ERROR.getCode(), "参数类型不匹配: " + name);
    }

    /**
     * 认证异常（Spring Security 抛出）
     */
    @ExceptionHandler(AuthenticationException.class)
    public Result<Void> handleAuthenticationException(AuthenticationException e, HttpServletResponse response) {
        log.warn("认证异常: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return Result.fail(ErrorCode.UNAUTHORIZED);
    }

    /**
     * 授权异常（Spring Security 抛出）
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<Void> handleAccessDeniedException(AccessDeniedException e, HttpServletResponse response) {
        log.warn("授权异常: {}", e.getMessage());
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return Result.fail(ErrorCode.FORBIDDEN);
    }

    /**
     * 未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<Void> handleException(Exception e, HttpServletResponse response) {
        log.error("系统异常", e);
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        return Result.fail(ErrorCode.INTERNAL_ERROR);
    }
}
