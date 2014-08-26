package com.visa.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.visa.po.line.LineNameList;
import com.visa.po.line.LineOrder;
import com.visa.po.line.LinesSrvice;
import com.visa.vo.line.LineOrderVo;

public class StringUtil {
    /**
     * 为字符串左侧添加0
     */
    public static String paddingZeroToLeft(String str, int length) {
        int strLen = str.length();
        if (strLen < length) {
            return StringUtils.leftPad(str, length, "0");
        } else if (strLen == length) {
            return str;
        } else {
            return str.substring(strLen - length);
        }
    }

    public static String generateAddOperLog(LineOrderVo lineOrder) {
        StringBuffer result = new StringBuffer();
        result.append("新增订单，编号：").append(lineOrder.getOrderSeq());
        return result.toString();
    }

    public static String generateUpdateOperLog(LineOrderVo lineOrder, LineOrder lineOrderDB,
            Map<Integer, LinesSrvice> serviceListDB, Map<Integer, LineNameList> nameListDB) {
        StringBuffer result = new StringBuffer();
        result.append("修改订单，编号：").append(lineOrder.getOrderSeq()).append("。");
        try {
            result.append(compareChange(lineOrder, lineOrderDB));
            List<LinesSrvice> linesSrviceList = lineOrder.getLineOrderService();
            for (LinesSrvice linesSrvice : linesSrviceList) {
                result.append(compareChange(linesSrvice,
                        serviceListDB.get(linesSrvice.getServiceId())));
            }
            List<LineNameList> linesNameList = null;
            for (LineNameList nameList : linesNameList) {
                result.append(compareChange(nameList, nameListDB.get(nameList.getId())));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString();
    }

    public static String generateDeleteOperLog(LineOrder lineOrder) {
        StringBuffer result = new StringBuffer();
        result.append("删除订单，编号：").append(lineOrder.getOrderSeq());
        return result.toString();
    }

    private static String compareChange(Object o, Object db) throws IllegalArgumentException,
            IllegalAccessException, InvocationTargetException {
        String result = "", mthName = "", rtnTypeName = "", fieldDes = "";
        Class<?> cls = o.getClass();
        Method[] mths = cls.getMethods();
        for (Method mth : mths) {
            mthName = mth.getName();
            rtnTypeName = mth.getReturnType().getName();
            if (mthName != null && mthName.contains("get")
                    && mth.isAnnotationPresent(FieldDes.class)) {
                // 获取该方法的MyAnnotation注解实例
                FieldDes myAnnotation = mth.getAnnotation(FieldDes.class);
                // 获取myAnnotation
                fieldDes = myAnnotation.fieldDes();

                if ("java.lang.String".equals(rtnTypeName)) {
                    String str1 = (String) mth.invoke(db);
                    String str2 = (String) mth.invoke(o);

                    if (str1 != null && str2 != null && !str1.equals(str2)) {
                        result += "将" + fieldDes + "由\"" + str1 + "\"修改为\"" + str2 + "\"";
                        result += "<br/>";
                    }
                } else if ("java.lang.Integer".equals(rtnTypeName) || "int".equals(rtnTypeName)) {
                    Integer str1 = (Integer) mth.invoke(o);
                    Integer str2 = (Integer) mth.invoke(db);
                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        result += "将" + fieldDes + "由\"" + str1 + "\"修改为\"" + str2 + "\"";
                        result += "<br/>";
                    }
                } else if ("java.util.Date".equals(rtnTypeName)) {
                    Date str1 = (Date) mth.invoke(o);
                    Date str2 = (Date) mth.invoke(db);
                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        result += "将" + fieldDes + "由\"" + str1 + "\"修改为\"" + str2 + "\"";
                        result += "<br/>";
                    }
                } else if ("java.math.BigDecimal".equals(rtnTypeName)) {
                    BigDecimal str1 = (BigDecimal) mth.invoke(o);
                    BigDecimal str2 = (BigDecimal) mth.invoke(db);
                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        result += "将" + fieldDes + "由\"" + str1 + "\"修改为\"" + str2 + "\"";
                        result += "<br/>";
                    }
                }

                fieldDes = "";
            }
        }
        return result;
    }

    public static void main(String[] args) {
        LineOrder lineOrder = new LineOrder();
        LineOrderVo lineOrderDB = new LineOrderVo();
        lineOrder.setOrderDate(new Date());
        lineOrderDB.setOrderDate(DateUtil.parse("2008-05-16", DateUtil.FORMAT_DATE));
        try {
            System.out.println(compareChange(lineOrder, lineOrderDB));
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
