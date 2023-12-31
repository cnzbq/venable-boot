package com.venble.boot.web.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

/**
 * Json 工具类
 */
public final class JsonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = SpringContextUtils.getBean(ObjectMapper.class);
    }

    /**
     * 对象转换为 json 字符串
     *
     * @param object 对象
     * @return json 字符串
     */
    public static String toJsonString(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json 字符串转换为对象
     *
     * @param jsonStr json 字符串
     * @param clazz   对象类型
     */
    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, clazz);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * json 字符串转换为对象集合
     *
     * @param jsonStr json 字符串
     * @param clazz   对象类型
     */
    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        try {
            return objectMapper.readValue(jsonStr, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
