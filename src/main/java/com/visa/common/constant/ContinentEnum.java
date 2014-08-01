package com.visa.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 大洲枚举
 */
public enum ContinentEnum {

    /**
     * 欧洲
     */
    Europe(1, "欧洲"),
    /**
     * 澳洲
     */
    Australia(2, "澳洲"),
    /**
     * 美洲
     */
    America(3, "美洲"),
    /**
     * 亚洲
     */
    Asian(4, "亚洲"),
    /**
     * 非洲
     */
    Africa(5, "非洲");

    private int id;
    private String name;

    /**
     * idNameMap
     */
    public static final Map<Integer, ContinentEnum> CONTINENT_MAP = new HashMap<Integer, ContinentEnum>();

    static {
        for (ContinentEnum type : values()) {
            CONTINENT_MAP.put(type.id, type);
        }
    }

    private ContinentEnum(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
