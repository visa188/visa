package com.visa.common.util;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.visa.common.constant.Constant;
import com.visa.common.constant.ContinentEnum;
import com.visa.common.constant.LineRoleEnumType;
import com.visa.common.constant.PriceStatusEnum;
import com.visa.common.constant.RoleEnumType;
import com.visa.common.constant.YshkStatusEnum;

/**
 * Velocity Tool Box Util.
 * 
 * @author user
 */
public class VelocityToolbox {

    /**
     * 主要用于select下拉列表，根据参数生成当前默认选中项
     * 
     * @param selectedValue selectedValue
     * @param currentValue currentValue
     * @return selected
     */
    public String selected(Object selectedValue, Object currentValue) {
        if (currentValue == null || selectedValue == null) {
            return "";
        }
        if (selectedValue.toString().equals(currentValue.toString())) {
            return " selected";
        }
        return "";
    }

    /**
     * 主要用于radio单选项，根据参数生成当前默认选中项
     * 
     * @param checkedValue checkedValue
     * @param currentValue currentValue
     * @return checked
     */
    public String checked(String checkedValue, String currentValue) {
        if (checkedValue != null && checkedValue.equals(currentValue)) {
            return " checked";
        }
        return "";
    }

    /**
     * @param date date
     * @param formater formater
     * @return 格式化后的日期格式
     */
    public String format(Date date, String formater) {
        return DateUtil.format(date, formater);
    }

    /**
     * @param json json
     * @param key key
     * @return value
     */
    @SuppressWarnings("unchecked")
    public String getJsonValue(String json, String key) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        Map<String, String> map = JsonUtil.toBean(json, Map.class);
        if (map.containsKey(key)) {
            String value = map.get(key);
            if (StringUtils.isEmpty(value)) {
                return null;
            }
            return value;
        }
        return null;
    }

    /**
     * @param price price
     * @return 如果为整数度数，显示整数，否则显示小数 36.0元，显示 36元
     */
    public String disPrice(Float price) {
        if (price == null) {
            return "";
        }
        Float fp = new Float(price.intValue());
        if (fp.floatValue() == price.floatValue()) {
            return String.valueOf(fp.intValue());
        }
        return String.valueOf(price);
    }

    /**
     * @param roleId roleId
     * @return role name
     */
    public String getRole(Integer roleId) {
        return RoleEnumType.ROLE_MAP.get(roleId).getName();
    }

    /**
     * @param continentId continentId
     * @return continent name
     */
    public String getContinent(Integer continentId) {
        return ContinentEnum.CONTINENT_MAP.get(continentId).getName();
    }

    /**
     * @param statusId statusId
     * @return role name
     */
    public String getPriceStatus(Integer statusId) {
        if (statusId != null && statusId != 0) {
            return PriceStatusEnum.PRICESTATUS_MAP.get(statusId).getName();
        } else {
            return PriceStatusEnum.PRICESTATUS_MAP.get(1).getName();
        }
    }

    /**
     * @param statusId statusId
     * @return role name
     */
    public String getYshkStatus(Integer statusId) {
        if (statusId != null && statusId != 0) {
            return YshkStatusEnum.YSHKSTATUS_MAP.get(statusId).getName();
        } else {
            return YshkStatusEnum.YSHKSTATUS_MAP.get(1).getName();
        }
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public Map<Integer, RoleEnumType> roleOptions() {
        return RoleEnumType.ROLE_MAP;
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public Map<Integer, LineRoleEnumType> lineRoleOptions() {
        return LineRoleEnumType.ROLE_MAP;
    }

    /**
     * @return Map<Integer, ContinentEnum>
     */
    public Map<Integer, ContinentEnum> continentOptions() {
        return ContinentEnum.CONTINENT_MAP;
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getSalesmanRoleId() {
        return RoleEnumType.SALESMAN.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getOperatorRoleId() {
        return RoleEnumType.OPERATOR.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getManagerRoleId() {
        return RoleEnumType.MANAGER.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getFinanceRoleId() {
        return RoleEnumType.FINANCE.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getAdminRoleId() {
        return RoleEnumType.ADMIN.getId();
    }

    public int getLineSalesmanRoleId() {
        return LineRoleEnumType.SALESMAN.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getLineOperatorRoleId() {
        return LineRoleEnumType.OPERATOR.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getLineManagerRoleId() {
        return LineRoleEnumType.MANAGER.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getLineFinanceRoleId() {
        return LineRoleEnumType.FINANCE.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getLineAdminRoleId() {
        return LineRoleEnumType.ADMIN.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public boolean isAdminRoleId(int roleId) {
        if (RoleEnumType.ADMIN.getId() == roleId || roleId == Constant.SUPER_ADMIN_ROLE_ID) {
            return true;
        }
        return false;
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public boolean isLineAdminRoleId(int roleId) {
        if (LineRoleEnumType.ADMIN.getId() == roleId || roleId == Constant.SUPER_ADMIN_ROLE_ID) {
            return true;
        }
        return false;
    }

    /**
     * 用于截字，超长的用".."表示
     */
    public String shortenStr(String str, int length) {
        if (StringUtils.isEmpty(str) || str.length() <= length) {
            return str;
        }

        return str.substring(0, length) + "..";
    }
}
