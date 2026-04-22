package com.de.food.common.util;

import com.de.food.common.exception.BizException;
import com.de.food.common.result.ErrorCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import tools.jackson.core.JacksonException;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import lombok.extern.slf4j.Slf4j;

/**
 * JSON 工具类
 * <p>
 * 基于 Jackson 3.x 的全局唯一 ObjectMapper 实例，
 * 禁止在业务代码中 new ObjectMapper() 或 JsonMapper.builder()。
 * JSR310 时间模块已内置，无需手动注册。
 */
@Slf4j
public class JacksonUtils {

    private static final ObjectMapper MAPPER = JsonMapper.builder()
            .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
            .changeDefaultPropertyInclusion(incl -> incl.withValueInclusion(JsonInclude.Include.NON_NULL))
            .build();

    private JacksonUtils() {
    }

    /**
     * 对象转 JSON 字符串
     */
    public static String toJsonString(Object obj) {
        if (obj == null) return null;
        if (obj instanceof String) return (String) obj;
        try {
            return MAPPER.writeValueAsString(obj);
        } catch (JacksonException e) {
            log.error("JSON 序列化失败: {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_ERROR, "JSON 序列化失败");
        }
    }

    /**
     * JSON 字符串转 对象
     */
    public static <T> T parseObject(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) return null;
        try {
            return MAPPER.readValue(json, clazz);
        } catch (JacksonException e) {
            log.error("JSON 反序列化失败: {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_ERROR, "JSON 反序列化失败");
        }
    }

    /**
     * JSON 字符串转 复杂泛型对象 (如 List<User>)
     */
    public static <T> T parseType(String json, TypeReference<T> typeReference) {
        if (json == null || json.isEmpty()) return null;
        try {
            return MAPPER.readValue(json, typeReference);
        } catch (JacksonException e) {
            log.error("JSON 泛型反序列化失败: {}", e.getMessage());
            throw new BizException(ErrorCode.INTERNAL_ERROR, "JSON 泛型反序列化失败");
        }
    }
}