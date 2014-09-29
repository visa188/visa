package com.visa.common.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.visa.common.constant.Constant;
import com.visa.common.constant.ContinentEnum;
import com.visa.common.constant.LineRoleEnumType;
import com.visa.common.constant.LineSrviceEnumType;
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
    public String disPrice(BigDecimal price) {
        if (price == null) {
            return "0";
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
        return RoleEnumType.ROLE_MAP.get(roleId) != null ? RoleEnumType.ROLE_MAP.get(roleId)
                .getName() : LineRoleEnumType.ROLE_MAP.get(roleId).getName();
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

    public int getLineSalesmanManagerRoleId() {
        return LineRoleEnumType.SALEMAN_MANAGER.getId();
    }

    public boolean isLineSalesmanRole(int roleId) {
        if (LineRoleEnumType.SALESMAN.getId() == roleId
                || LineRoleEnumType.SALEMAN_MANAGER.getId() == roleId) {
            return true;
        }
        return false;
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getLineOperatorRoleId() {
        return LineRoleEnumType.OPERATOR.getId();
    }

    public int getLineOperatorManagerRoleId() {
        return LineRoleEnumType.OPERATOR_MANAGER.getId();
    }

    public boolean isLineOperatorRole(int roleId) {
        if (LineRoleEnumType.OPERATOR.getId() == roleId
                || LineRoleEnumType.OPERATOR_MANAGER.getId() == roleId) {
            return true;
        }
        return false;
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getLineFinanceRoleId() {
        return LineRoleEnumType.FINANCE.getId();
    }

    public int getLineFinanceManagerRoleId() {
        return LineRoleEnumType.FINANCE_MANAGER.getId();
    }

    public boolean isLineFinanceRole(int roleId) {
        if (LineRoleEnumType.FINANCE.getId() == roleId
                || LineRoleEnumType.FINANCE_MANAGER.getId() == roleId) {
            return true;
        }
        return false;
    }

    public int getLineVisaoperRoleId() {
        return LineRoleEnumType.VISAOPER.getId();
    }

    public int getLineVisaoperManagerRoleId() {
        return LineRoleEnumType.VISAOPER_MANAGER.getId();
    }

    public boolean isLineVisaoperRole(int roleId) {
        if (LineRoleEnumType.VISAOPER.getId() == roleId
                || LineRoleEnumType.VISAOPER_MANAGER.getId() == roleId) {
            return true;
        }
        return false;
    }

    public int getLineSignoperRoleId() {
        return LineRoleEnumType.SIGNOPERATOR.getId();
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
    public boolean isLineAdminRole(int roleId) {
        if (LineRoleEnumType.ADMIN.getId() == roleId || roleId == Constant.SUPER_ADMIN_ROLE_ID) {
            return true;
        }
        return false;
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getBsServicTypeId() {
        return LineSrviceEnumType.DJBS.getId();
    }

    /**
     * @return Map<Integer, RoleEnumType>
     */
    public int getSjdyServicTypeId() {
        return LineSrviceEnumType.DJSJDY.getId();
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

    /**
     * 用于截字，超长的用".."表示
     */
    public String getOperate(int operateType) {
        if (Constant.OPERATOR_TYPE_UPDATE == operateType) {
            return "修改";
        } else if (Constant.OPERATOR_TYPE_ADD == operateType) {
            return "新增";
        } else if (Constant.OPERATOR_TYPE_DELETE == operateType) {
            return "删除";
        } else {
            return "";
        }
    }

    public String getFileName(String fileUrl) {
        if (!StringUtils.isEmpty(fileUrl)) {
            return fileUrl.replace("/upload/", "");
        }
        return "";
    }

    public String isReadOnly(int userRoleId) {
        return isReadOnly(userRoleId, 0);
    }

    /**
     * @param userRoleId
     * @param type 0 text 1 select 2 datepicker
     * @return
     */
    public String isReadOnly(int userRoleId, int type) {
        if (isLineAdminRole(userRoleId)) {
            return "aa";
        }
        if (userRoleId == getLineSalesmanRoleId()) {
            return "bb";
        }
        switch (type) {
        case 0:
            return " readonly";
        case 1:
            return " onfocus=\"this.defaultIndex=this.selectedIndex;\" onchange=\"this.selectedIndex=this.defaultIndex;\"";
        case 2:
            return "cc";
        default:
            return "dd";
        }
    }

    public String isDisabled(int userRoleId) {
        if (isLineAdminRole(userRoleId)) {
            return "";
        }
        if (userRoleId == getLineSalesmanRoleId()) {
            return "";
        }
        return "disabled";
    }

    public String isDisplay(int userRoleId) {
        return isDisplay(userRoleId, getLineSalesmanRoleId());
    }

    public String isDisplay(int userRoleId, int role) {
        if (isLineAdminRole(userRoleId)) {
            return "";
        }
        if (userRoleId == role) {
            return "";
        }
        return "style = \"display:none\"";
    }

    public String getVisaTypeName(String visaType) {
        if ("2".equals(visaType)) {
            return "归国保证金";
        }
        if ("3".equals(visaType)) {
            return "联保";
        }
        return "";
    }
    public int getNext(int roleId){
    	switch (roleId) {
			case 7:return 71;
			case 71:return 81; //销售经理 通过
			case 711:return 81; //销售副总经理 通过
			case 6:return 81; //总经理 通过
			case 81:return 8; //操作经理 通过
			case 811:return 8; //操作副总经理 通过
			case 8:return 101;
			case 101:return 10;
			case 10:return 9;
			case 9:return 91;
			default:return -1;
		}
    }
}
