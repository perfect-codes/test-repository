package com.ruubypay.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class JsonUtil {

    static ObjectMapper objectMapper = new ObjectMapper();

    public static String toJson(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("对象转JSON异常");
        }
        return null;
    }

    public static <T> T toObject(String json,Class<T> cls){
        try {
            T obj = objectMapper.readValue(json,cls);
            return obj;
        } catch (IOException e) {
            log.error("JSON转对象异常");
        }
        return null;
    }
}
