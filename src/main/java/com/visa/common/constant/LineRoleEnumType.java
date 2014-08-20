package com.visa.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色的可枚举类型
 * 
 * @author user
 */
public enum LineRoleEnumType {

    /**
     * 管理员
     */
    ADMIN(6, "管理员"),
    /**
     * 销售
     */
    SALESMAN(7, "销售"),
    /**
     * 经理
     */
    MANAGER(8, "经理"),
    /**
     * 操作员
     */
    OPERATOR(9, "操作员"),
    /**
     * 财务
     */
    FINANCE(10, "财务"),
    /**
     * 送签员
     */
    VISAOPER(11, "送签员");

    private int id;
    private String name;

    /**
     * idNameMap
     */
    public static final Map<Integer, LineRoleEnumType> ROLE_MAP = new HashMap<Integer, LineRoleEnumType>();

    static {
        for (LineRoleEnumType type : values()) {
            ROLE_MAP.put(type.id, type);
        }
    }

    private LineRoleEnumType(int id, String name) {
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
