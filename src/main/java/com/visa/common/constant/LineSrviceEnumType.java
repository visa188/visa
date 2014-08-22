package com.visa.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单服务的可枚举类型
 * 
 * @author user
 */
public enum LineSrviceEnumType {

    /**
     * 领队服务
     */
    LD(1, "领队服务"),
    /**
     * 签证服务
     */
    QZ(2, "签证服务"),
    /**
     * 机票服务
     */
    JP(3, "机票服务"),
    /**
     * 地接服务-巴士
     */
    DJBS(41, "地接服务"),
    /**
     * 地接服务-司机兼导游
     */
    DJSJDY(42, "地接服务"),
    /**
     * 其他服务
     */
    QT(3, "机票服务");

    private int id;
    private String name;

    /**
     * idNameMap
     */
    public static final Map<Integer, LineSrviceEnumType> LINE_SRVICE_MAP = new HashMap<Integer, LineSrviceEnumType>();

    static {
        for (LineSrviceEnumType type : values()) {
            LINE_SRVICE_MAP.put(type.id, type);
        }
    }

    private LineSrviceEnumType(int id, String name) {
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
