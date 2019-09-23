package com.wqp.common.util.common;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;

/**
 * Jackson工具类
 */
public class JacksonMapperUtil {
    private static ObjectMapper objectMapper = new ObjectMapper();

    static {
        // ObjectMapper 配置
//        objectMapper.setSerializationInclusion(JsonInclude.Include.ALWAYS);
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        // 忽略对象为空
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // 忽略未知字段
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 允许出现单引号
        objectMapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
        // 允许出理特殊字符和转义符
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        objectMapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        // 允许出现注释
        objectMapper.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // 格式化输出
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

    }

    /**
     * 对象转json字符串
     * @param object 原对象
     * @return
     */
    public static String object2json(Object object){
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串输出到文件
     * @param jsonString
     * @param filePath
     */
    public static void json2file(String jsonString, String filePath){
        try {
            if(!filePath.endsWith(".json")) new InvalidParameterException("文件路径参数错误");
            File jsonFile = new File(filePath);
            File parentFile = jsonFile.getParentFile();
            if(!parentFile.exists()) parentFile.mkdirs();
            Writer writer = new FileWriter(jsonFile);
            writer.write(jsonString);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * json字符串转对象
     * @param jsonString
     * @param objectClass
     * @param <T>
     * @return
     */
    public static <T> T json2object(String jsonString, Class<T> objectClass){
        try {
            return objectMapper.readValue(jsonString, objectClass);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json文件转对象
     * @param jsonFile
     * @param objectType
     * @param <T>
     * @return
     */
    public static <T> T json2object(File jsonFile, Class<T> objectType){
        try {
            return objectMapper.readValue(jsonFile, objectType);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json文件转对象
     * @param jsonFile
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T json2object(File jsonFile, TypeReference<T> typeReference){
        try {
            return objectMapper.readValue(jsonFile, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * json字符串转对象
     * @param jsonString
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T json2object(String jsonString, TypeReference<T> typeReference){
        try {
            return objectMapper.readValue(jsonString, typeReference);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
