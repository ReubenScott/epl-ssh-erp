package com.kindustry.util;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description: http://www.easysb.cn/2019/07/482.html
 * @Date: 2019/7/16
 * @Author: Jekkay Hu
 */
public class JsonUtil {

  private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);

  // 加载速度太慢了，放在静态代码块中
  // private static final ObjectMapper mapper = new ObjectMapper();
  private static ObjectMapper mapper;

  /**
   * 设置一些通用的属性
   */
  static {
    mapper = new ObjectMapper();
    // 如果json中有新增的字段并且是实体类类中不存在的，不报错
    // mapper.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false);
    // 如果存在未知属性，则忽略不报错
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    // 允许key没有双引号
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
    // 允许key有单引号
    mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
    // 允许整数以0开头
    mapper.configure(JsonParser.Feature.ALLOW_NUMERIC_LEADING_ZEROS, true);
    // 允许字符串中存在回车换行控制符
    mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
  }

  public static String toJSONString(Object obj) throws JsonProcessingException {
    return obj != null ? toJSONString(obj, () -> "", false) : "";
  }

  public static String toFormatJSONString(Object obj) throws JsonProcessingException {
    return obj != null ? toJSONString(obj, () -> "", true) : "";
  }

  public static String toJSONString(Object obj, Supplier<String> defaultSupplier, boolean format) throws JsonProcessingException {
    try {
      if (obj == null) {
        return defaultSupplier.get();
      }
      if (obj instanceof String) {
        return obj.toString();
      }
      if (obj instanceof Number) {
        return obj.toString();
      }
      if (format) {
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
      }
      return mapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      logger.error(String.format("toJSONString %s", obj != null ? obj.toString() : "null"), e);
      throw e;
    }

    // return defaultSupplier.get();
  }

  public static <T> T parse(String value, Class<T> tClass) {
    return StringUtils.isNotBlank(value) ? parse(value, tClass, () -> null) : null;
  }

  public static <T> T parse(Object obj, Class<T> tClass) throws JsonProcessingException {
    return obj != null ? parse(toJSONString(obj), tClass, () -> null) : null;
  }

  public static <T> T parse(String value, Class<T> tClass, Supplier<T> defaultSupplier) {
    try {
      if (StringUtils.isBlank(value)) {
        return defaultSupplier.get();
      }
      return mapper.readValue(value, tClass);
    } catch (Throwable e) {
      logger.error(String.format("parse exception: \n %s\n %s", value, tClass), e);
    }
    return defaultSupplier.get();
  }

  public static <T> List<T> parseList(String value, Class<T> tClass) {
    return StringUtils.isNotBlank(value) ? parseList(value, tClass, () -> null) : null;
  }

  public static <T> List<T> parseList(Object obj, Class<T> tClass) throws JsonProcessingException {
    return obj != null ? parseList(toJSONString(obj), tClass, () -> null) : null;
  }

  public static <T> List<T> parseList(String value, Class<T> tClass, Supplier<List<T>> defaultSupplier) {
    try {
      if (StringUtils.isBlank(value)) {
        return defaultSupplier.get();
      }
      JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, tClass);
      return mapper.readValue(value, javaType);
    } catch (Throwable e) {
      logger.error(String.format("parseList exception \n%s\n%s", value, tClass), e);
    }
    return defaultSupplier.get();
  }

  // 简单地直接用json复制或者转换(Cloneable)
  public static <T> T jsonCopy(Object obj, Class<T> tClass) throws JsonProcessingException {
    return obj != null ? parse(toJSONString(obj), tClass) : null;
  }

  public static Map<String, Object> toMap(String value) {
    return StringUtils.isNotBlank(value) ? toMap(value, () -> null) : null;
  }

  public static Map<String, Object> toMap(Object value) throws JsonProcessingException {
    return value != null ? toMap(value, () -> null) : null;
  }

  public static Map<String, Object> toMap(Object value, Supplier<Map<String, Object>> defaultSupplier) throws JsonProcessingException {
    if (value == null) {
      return defaultSupplier.get();
    }
    if (value instanceof Map) {
      return (Map<String, Object>)value;
    }
    return toMap(toJSONString(value), defaultSupplier);
  }

  public static Map<String, Object> toMap(String value, Supplier<Map<String, Object>> defaultSupplier) {
    if (StringUtils.isBlank(value)) {
      return defaultSupplier.get();
    }
    try {
      return parse(value, LinkedHashMap.class);
    } catch (Exception e) {
      logger.error(String.format("toMap exception\n%s", value), e);
    }
    return defaultSupplier.get();
  }

  public static List<Object> toList(String value) {
    return StringUtils.isNotBlank(value) ? toList(value, () -> null) : null;
  }

  public static List<Object> toList(Object value) throws JsonProcessingException {
    return value != null ? toList(value, () -> null) : null;
  }

  public static List<Object> toList(String value, Supplier<List<Object>> defaultSuppler) {
    if (StringUtils.isBlank(value)) {
      return defaultSuppler.get();
    }
    try {
      return parse(value, List.class);
    } catch (Exception e) {
      logger.error("toList exception\n" + value, e);
    }
    return defaultSuppler.get();
  }

  public static List<Object> toList(Object value, Supplier<List<Object>> defaultSuppler) throws JsonProcessingException {
    if (value == null) {
      return defaultSuppler.get();
    }
    if (value instanceof List) {
      return (List<Object>)value;
    }
    return toList(toJSONString(value), defaultSuppler);
  }

  public static long getLong(Map<String, Object> map, String key) {
    if (MapUtils.isEmpty(map)) {
      return 0L;
    }
    String valueStr = String.valueOf(map.get(key));
    if (StringUtils.isBlank(valueStr) || !StringUtils.isNumeric(valueStr)) {
      return 0L;
    }
    return Long.valueOf(valueStr);
  }

  public static int getInt(Map<String, Object> map, String key) {
    if (MapUtils.isEmpty(map)) {
      return 0;
    }
    String valueStr = String.valueOf(map.get(key));
    if (StringUtils.isBlank(valueStr) || !StringUtils.isNumeric(valueStr)) {
      return 0;
    }
    return Integer.valueOf(valueStr);
  }
}