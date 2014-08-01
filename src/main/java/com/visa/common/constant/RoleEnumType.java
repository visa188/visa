package com.visa.common.constant;

import java.util.HashMap;
import java.util.Map;

/**
 * 角色的可枚举类型
 * 
 * @author user
 */
public enum RoleEnumType {

    /**
     * 管理员
     */
    ADMIN(1, "管理员"),
    /**
     * 销售
     */
    SALESMAN(2, "销售"),
    /**
     * 经理
     */
    MANAGER(3, "经理"),
    /**
     * 操作员
     */
    OPERATOR(4, "操作员"),
    /**
     * 财务
     */
    FINANCE(5, "财务");

    private int id;
    private String name;

    /**
     * idNameMap
     */
    public static final Map<Integer, RoleEnumType> ROLE_MAP = new HashMap<Integer, RoleEnumType>();

    static {
        for (RoleEnumType type : values()) {
            ROLE_MAP.put(type.id, type);
        }
    }

    private RoleEnumType(int id, String name) {
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
