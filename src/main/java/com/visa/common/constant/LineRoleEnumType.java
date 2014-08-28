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
     * 销售经理
     */
    SALEMAN_MANAGER(71, "销售经理"),
    /**
     * 操作员
     */
    OPERATOR(8, "操作员"),
    /**
     * 操作经理
     */
    OPERATOR_MANAGER(81, "操作经理"),
    /**
     * 财务
     */
    FINANCE(9, "财务"),
    /**
     * 财务经理
     */
    FINANCE_MANAGER(91, "财务经理"),
    /**
     * 送签员
     */
    VISAOPER(10, "送签员"),
    /**
     * 送签经理
     */
    VISAOPER_MANAGER(101, "送签经理");

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
