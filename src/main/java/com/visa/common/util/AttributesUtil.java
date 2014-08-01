package com.visa.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * key value键值对字符串属性工具类
 * 
 * @author haosun
 */
public final class AttributesUtil {

    private AttributesUtil() {

    }

    /**
     * 从attributes中获取一个属性值
     * 
     * @param attributes attributes
     * @param attributeName attributeName
     * @return attributeValue
     */
    @SuppressWarnings("unchecked")
    public static String getAttribute(String attributes, String attributeName) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }
        Map<String, String> attributeMap = (Map<String, String>) JsonUtil.toBean(attributes, Map.class);
        if (attributeMap == null) {
            return null;
        }
        return attributeMap.get(attributeName);
    }

    /**
     * 增加一对属性到attributes中，并返回增加后的attributes
     * 
     * @param attributes attributes
     * @param attributeName attributeName
     * @param attributeValue attributeValue
     * @return attributes
     */
    @SuppressWarnings("unchecked")
    public static String setAttribute(String attributes, String attributeName, String attributeValue) {
        if (attributeName == null || attributeValue == null) {
            return attributes;
        }
        Map<String, String> attributeMap = null;
        if (attributes == null || attributes.isEmpty()) {
            attributeMap = new HashMap<String, String>();
        } else {
            attributeMap = (Map<String, String>) JsonUtil.toBean(attributes, Map.class);
            if (attributeMap == null) {
                return attributes;
            }
        }
        attributeMap.put(attributeName, attributeValue);
        return JsonUtil.toString(attributeMap);
    }

    /**
     * 增加map到attributes中，并返回增加后的attributes
     * 
     * @param attributes attributes
     * @param attributeMap attributeMap
     * @return attributes
     */
    @SuppressWarnings("unchecked")
    public static String setAttributeMap(String attributes, Map<String, String> attributeMap) {
        if (attributeMap == null || attributeMap.isEmpty()) {
            return attributes;
        }
        Map<String, String> resultMap = null;
        if (attributes == null || attributes.isEmpty()) {
            resultMap = new HashMap<String, String>();
        } else {
            resultMap = (Map<String, String>) JsonUtil.toBean(attributes, Map.class);
            if (resultMap == null) {
                return attributes;
            }
        }
        resultMap.putAll(attributeMap);
        return JsonUtil.toString(resultMap);
    }

    /**
     * 将attributes键值对字符串转换成Map
     * 
     * @param attributes attributes
     * @return map
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> toMap(String attributes) {
        if (attributes == null || attributes.isEmpty()) {
            return null;
        }
        return (Map<String, String>) JsonUtil.toBean(attributes, Map.class);
    }

    /**
     * 将Map转换成attributes键值对字符串
     * 
     * @param attributeMap attributeMap
     * @return string
     */
    public static String toAttributes(Map<String, String> attributeMap) {
        if (attributeMap == null) {
            return null;
        }
        return JsonUtil.toString(attributeMap);
    }

}
