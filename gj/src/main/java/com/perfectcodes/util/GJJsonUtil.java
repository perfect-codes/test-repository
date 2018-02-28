package com.perfectcodes.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public final class GJJsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Logger logger = LoggerFactory.getLogger(GJJsonUtil.class);

    public static String toJSON(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            logger.error("JSON序列化错误",e);
        }
        return null;
    }

    public static <T> T fromJSON(String json,Class<T> clazz){
        try {
            return objectMapper.readValue(json,clazz);
        } catch (IOException e) {
            logger.error("JSON反序列化错误",e);
        }
        return null;
    }
}
