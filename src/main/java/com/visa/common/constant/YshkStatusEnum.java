package com.visa.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 付款状态的可枚举类型
 * 
 * @author user
 */
public enum YshkStatusEnum {

    /**
     * 未付款
     */
    NOTYET(1, "未收款"),
    /**
     * 部分付款
     */
    PARTISION(2, "部分收款"),
    /**
     * 已完款
     */
    DONE(3, "已收全款");

    private int id;
    private String name;

    /**
     * idNameMap
     */
    public static final Map<Integer, YshkStatusEnum> YSHKSTATUS_MAP = new HashMap<Integer, YshkStatusEnum>();

    static {
        for (YshkStatusEnum type : values()) {
            YSHKSTATUS_MAP.put(type.id, type);
        }
    }

    private YshkStatusEnum(int id, String name) {
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
