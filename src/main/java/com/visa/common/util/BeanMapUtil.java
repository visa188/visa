package com.visa.common.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * Bean Util
 * 
 * @author haosun
 */
public final class BeanMapUtil {

    private BeanMapUtil() {
    }

    /**
     * 根据bean的属性得到对应的Map
     * 
     * @param bean bean
     * @param properties properties
     * @return map
     */
    public static Map<String, Object> beanPropertiesToMap(Object bean, String... properties) {
        return beanPropertiesToMap(bean, null, properties);
    }

    /**
     * 根据bean的属性得到对应的Map，dateFormat日期转换的格式
     * 
     * @param bean bean
     * @param dateFormat 日期转换的格式
     * @param properties properties
     * @return map
     */
    public static Map<String, Object> beanPropertiesToMap(Object bean, String dateFormat, String[] properties) {
        if (bean == null || properties == null || properties.length == 0) {
            throw new IllegalArgumentException("参数不能为空");
        }
        Map<String, Object> propsMap = new LinkedHashMap<String, Object>();
        BeanWrapper wrapper = new BeanWrapperImpl(bean);
        for (String property : properties) {
            if (property == null || property.isEmpty()) {
                continue;
            }
            Object value = wrapper.getPropertyValue(property);
            if (value != null) {
                if (value instanceof Date && StringUtils.isNotEmpty(dateFormat)) {
                    // 如果value是Date类型，则转换为yyyy-MM-dd HH:mm:ss格式字符串
                    value = DateUtil.format((Date) value, dateFormat);
                }
                propsMap.put(property, value);
            }
        }
        return propsMap;
    }

    /**
     * 根据bean list的属性得到对应的Map list
     * 
     * @param list list
     * @param properties properties
     * @return map
     */
    public static List<Map<String, Object>> beanListToMapList(List<?> list, String... properties) {
        if (list == null || properties == null || properties.length == 0) {
            throw new IllegalArgumentException("参数不能为空");
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Object object : list) {
            result.add(beanPropertiesToMap(object, properties));
        }
        return result;
    }

    /**
     * 根据bean list的属性得到对应的Map list，dateFormat日期转换的格式
     * 
     * @param list list
     * @param dateFormat 日期转换的格式
     * @param properties properties
     * @return map
     */
    public static List<Map<String, Object>> beanListToMapList(List<?> list, String dateFormat, String[] properties) {
        if (list == null || properties == null || properties.length == 0) {
            throw new IllegalArgumentException("参数不能为空");
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Object object : list) {
            result.add(beanPropertiesToMap(object, dateFormat, properties));
        }
        return result;
    }

    /**
     * list to map 根据MappedKeyGetter中对bean进行的处理，及返回的Key
     * 
     * @param <K> K
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @return beans map
     */
    public static <K, T> Map<K, T> listToMap(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter) {
        if (mappedKeyGetter == null) {
            throw new IllegalArgumentException();
        }
        Map<K, T> beansMap = new LinkedHashMap<K, T>();
        if (list == null || list.isEmpty()) {
            return beansMap;
        }

        for (T bean : list) {
            if (bean == null) {
                continue;
            }
            K key = mappedKeyGetter.getKey(bean);
            if (key == null) {
                continue;
            }
            beansMap.put(key, bean);
        }
        return beansMap;
    }

    /**
     * 将list按照key进行分组
     * 
     * @param <K> K
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @return map
     */
    public static <K, T> Map<K, List<T>> groupList(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter) {
        if (mappedKeyGetter == null) {
            throw new IllegalArgumentException();
        }
        Map<K, List<T>> map = new HashMap<K, List<T>>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        groupMap(map, list, mappedKeyGetter);
        return map;
    }

    /**
     * 将list按照key进行分组，并指定key的排序规则
     * 
     * @param <K> K
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @param keyComparator keyComparator
     * @return map
     */
    public static <K, T> Map<K, List<T>> groupList(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter,
            Comparator<K> keyComparator) {
        if (mappedKeyGetter == null) {
            throw new IllegalArgumentException();
        }
        Map<K, List<T>> map = new TreeMap<K, List<T>>(keyComparator);
        if (list == null || list.isEmpty()) {
            return map;
        }
        groupMap(map, list, mappedKeyGetter);
        return map;
    }

    private static <K, T> void groupMap(Map<K, List<T>> map, List<T> list, MappedKeyGetter<K, T> mappedKeyGetter) {
        for (T one : list) {
            K key = mappedKeyGetter.getKey(one);
            if (key == null) {
                continue;
            }
            List<T> oneList = map.get(key);
            if (oneList == null) {
                oneList = new ArrayList<T>();
                map.put(key, oneList);
            }
            oneList.add(one);
        }
    }

    /**
     * 将list按照key进行分组，并组装新对象
     * 
     * @param <K> K
     * @param <V> V
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @param buildCallBack buildCallBack
     * @return map
     */
    public static <K, V, T> Map<K, List<V>> groupListAndBuild(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter,
            BuildCallBack<V, T> buildCallBack) {
        if (mappedKeyGetter == null || buildCallBack == null) {
            throw new IllegalArgumentException();
        }
        Map<K, List<V>> map = new HashMap<K, List<V>>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        groupMapAndBuild(map, list, mappedKeyGetter, buildCallBack);
        return map;
    }

    /**
     * 将list按照key进行分组，并组装新对象，按照指定的排序规则排序key
     * 
     * @param <K> K
     * @param <V> V
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @param keyComparator keyComparator
     * @param buildCallBack buildCallBack
     * @return map
     */
    public static <K, V, T> Map<K, List<V>> groupListAndBuild(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter,
            Comparator<K> keyComparator, BuildCallBack<V, T> buildCallBack) {
        if (mappedKeyGetter == null || buildCallBack == null) {
            throw new IllegalArgumentException();
        }
        Map<K, List<V>> map = new TreeMap<K, List<V>>(keyComparator);
        if (list == null || list.isEmpty()) {
            return map;
        }
        groupMapAndBuild(map, list, mappedKeyGetter, buildCallBack);
        return map;
    }

    private static <K, V, T> void groupMapAndBuild(Map<K, List<V>> map, List<T> list,
            MappedKeyGetter<K, T> mappedKeyGetter, BuildCallBack<V, T> buildCallBack) {
        for (T one : list) {
            K key = mappedKeyGetter.getKey(one);
            if (key == null) {
                continue;
            }
            V v = buildCallBack.build(one);
            if (v == null) {
                continue;
            }
            List<V> oneList = map.get(key);
            if (oneList == null) {
                oneList = new ArrayList<V>();
                map.put(key, oneList);
            }
            oneList.add(v);
        }
    }

    /**
     * 将list按照key set进行分组
     * 
     * @param <K> K
     * @param <T> T
     * @param list list
     * @param mappedKeySetGetter mappedKeySetGetter
     * @return map
     */
    public static <K, T> Map<K, List<T>> groupList(List<T> list, MappedKeySetGetter<K, T> mappedKeySetGetter) {
        if (mappedKeySetGetter == null) {
            throw new IllegalArgumentException();
        }
        Map<K, List<T>> map = new TreeMap<K, List<T>>();
        if (list == null || list.isEmpty()) {
            return map;
        }
        for (T one : list) {
            Set<K> keySet = mappedKeySetGetter.getKey(one);
            if (keySet == null) {
                continue;
            }
            for (K key : keySet) {
                List<T> oneList = map.get(key);
                if (oneList == null) {
                    oneList = new ArrayList<T>();
                    map.put(key, oneList);
                }
                oneList.add(one);
            }
        }
        return map;
    }

    /**
     * 将list的key分组为Set
     * 
     * @param <K> K
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @return set
     */
    public static <K, T> Set<K> groupKeySet(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter) {
        return groupKeySet(list, mappedKeyGetter, null);
    }

    /**
     * 将list的key分组为Set
     * 
     * @param <K> K
     * @param <T> T
     * @param list list
     * @param mappedKeyGetter mappedKeyGetter
     * @param keyComparator keyComparator
     * @return set
     */
    public static <K, T> Set<K> groupKeySet(List<T> list, MappedKeyGetter<K, T> mappedKeyGetter,
            Comparator<K> keyComparator) {
        if (mappedKeyGetter == null) {
            throw new IllegalArgumentException();
        }
        Set<K> set = new TreeSet<K>(keyComparator);
        if (list == null || list.isEmpty()) {
            return set;
        }
        for (T one : list) {
            K key = mappedKeyGetter.getKey(one);
            if (key == null) {
                continue;
            }
            set.add(key);
        }
        return set;
    }

    /**
     * 提取bean的key，并可对bean做相应处理
     * 
     * @author haosun
     */
    public interface MappedKeyGetter<K, T> {

        /**
         * 设置List to Map时使用的key，并可对bean做相应处理
         * 
         * @param bean bean
         * @return key
         */
        K getKey(T bean);
    }

    /**
     * 提取bean的KeySet，并可对bean做相应处理
     * 
     * @author haosun
     */
    public interface MappedKeySetGetter<K, T> {

        /**
         * groupList时使用的KeySet，并可对bean做相应处理
         * 
         * @param bean bean
         * @return key set
         */
        Set<K> getKey(T bean);
    }

    /**
     * groupListAndBuild中用于组装新对象的回调
     * 
     * @author haosun
     */
    public interface BuildCallBack<V, T> {

        /**
         * groupListAndBuild中用于组装新对象的回调
         * 
         * @param bean bean
         * @return V 新对象
         */
        V build(T bean);
    }

}
