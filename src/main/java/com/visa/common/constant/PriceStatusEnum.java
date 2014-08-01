package com.visa.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 付款状态的可枚举类型
 * 
 * @author user
 */
public enum PriceStatusEnum {

    /**
     * 未付款
     */
    NOTYET(1, "未付款"),
    /**
     * 部分付款
     */
    PARTISION(2, "部分付款"),
    /**
     * 已完款
     */
    DONE(3, "已付全款");

    private int id;
    private String name;

    /**
     * idNameMap
     */
    public static final Map<Integer, PriceStatusEnum> PRICESTATUS_MAP = new HashMap<Integer, PriceStatusEnum>();

    static {
        for (PriceStatusEnum type : values()) {
            PRICESTATUS_MAP.put(type.id, type);
        }
    }

    private PriceStatusEnum(int id, String name) {
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
