package com.visa.common.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import com.visa.common.constant.LineSrviceEnumType;
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
        StringBuffer result1 = new StringBuffer();
        try {
            if (lineOrder != null && lineOrderDB != null) {
                result.append(compareChange(lineOrder, lineOrderDB));
            }
            List<LinesSrvice> linesSrviceList = lineOrder.getLineOrderService();
            if (linesSrviceList != null) {
                for (LinesSrvice linesSrvice : linesSrviceList) {
                    if (linesSrvice.getServiceType() != LineSrviceEnumType.BX.getId()) {
                        if (serviceListDB != null
                                && serviceListDB.get(linesSrvice.getServiceId()) != null) {
                            String log = compareChange(linesSrvice,
                                    serviceListDB.get(linesSrvice.getServiceId()));
                            if (!StringUtils.isEmpty(log)) {
                                result.append("修改：").append(linesSrvice.getServiceName())
                                        .append("<br/>");
                                result.append(log);
                            }
                            serviceListDB.remove(linesSrvice.getServiceId());
                        } else {
                            result.append("新增：").append(linesSrvice.getServiceName())
                                    .append("<br/>");
                        }
                    }
                }
                if (serviceListDB != null) {
                    for (Entry<Integer, LinesSrvice> entry : serviceListDB.entrySet()) {
                        if (entry.getValue().getServiceType() != LineSrviceEnumType.BX.getId()) {
                            result.append("删除：").append(entry.getValue().getServiceName())
                                    .append("<br/>");
                        }
                    }
                }
            }

            List<LineNameList> linesNameList = lineOrder.getLineCustomList();
            if (linesNameList != null) {
                for (LineNameList nameList : linesNameList) {
                    if (nameListDB != null && nameListDB.get(nameList.getId()) != null) {
                        String log = compareChange(nameList, nameListDB.get(nameList.getId()));
                        if (!StringUtils.isEmpty(log)) {
                            result.append("修改客户：").append(nameList.getName()).append("<br/>");
                            result.append(log);
                        }
                        nameListDB.remove(nameList.getId());
                    } else {
                        result.append("新增客户：").append(nameList.getName()).append("<br/>");
                    }
                }
                if (nameListDB != null) {
                    for (Entry<Integer, LineNameList> entry : nameListDB.entrySet()) {
                        result.append("删除客户：").append(entry.getValue().getName()).append("<br/>");
                    }
                }
            }

            if (!StringUtils.isEmpty(result.toString())) {
                result1.append("修改订单，编号：").append(lineOrder.getOrderSeq()).append("<br/>")
                        .append(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result1.toString();
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

                if (String.class.getName().equals(rtnTypeName)) {
                    String str1 = (String) mth.invoke(db);
                    String str2 = (String) mth.invoke(o);

                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        if (fieldDes.indexOf("#") == -1) {
                            result += "将" + fieldDes + "由\"" + str1 + "\"修改为\"" + str2 + "\"";
                            result += "<br/>";
                        } else {
                            if (o instanceof LinesSrvice) {
                                Integer serviceType = ((LinesSrvice) o).getServiceType();
                                String t1 = "", t2 = "", des = "";
                                String[] array = fieldDes.split("#");
                                for (String temp : array) {
                                    String[] array1 = temp.split("\\*");
                                    if (array1[0].split("&")[0].startsWith(serviceType.toString())) {
                                        des = array1[0].split("&")[1];

                                        for (String temp2 : array1[1].split("@")) {
                                            if (str1.toString().equals(temp2.split("&")[0])) {
                                                t1 = temp2.split("&")[1];
                                            }
                                            if (str2.toString().equals(temp2.split("&")[0])) {
                                                t2 = temp2.split("&")[1];
                                            }
                                        }

                                        if (!StringUtils.isEmpty(t1) || !StringUtils.isEmpty(t2)) {
                                            result += "将" + des.replace("tab", "") + "由\"" + t1
                                                    + "\"修改为\"" + t2 + "\"";
                                            result += "<br/>";
                                        } else {
                                            result += "将" + des.replace("tab", "") + "由\"" + str1
                                                    + "\"修改为\"" + str2 + "\"";
                                            result += "<br/>";
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    }
                } else if ("java.lang.Integer".equals(rtnTypeName) || "int".equals(rtnTypeName)) {
                    Integer str1 = (Integer) mth.invoke(o);
                    Integer str2 = (Integer) mth.invoke(db);
                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        if (fieldDes.indexOf("#") == -1) {
                            result += "将" + fieldDes + "由\"" + str1 + "\"修改为\"" + str2 + "\"";
                            result += "<br/>";
                        } else {
                            String t1 = "", t2 = "";
                            String[] array = fieldDes.split("#")[1].split("@");
                            for (String temp : array) {
                                if (str1.toString().equals(temp.split("&")[0])) {
                                    t1 = temp.split("&")[1];
                                }
                                if (str2.toString().equals(temp.split("&")[0])) {
                                    t2 = temp.split("&")[1];
                                }
                            }
                            result += "将" + fieldDes.split("#")[0] + "由\"" + t1 + "\"修改为\"" + t2
                                    + "\"";
                            result += "<br/>";
                        }
                    }
                } else if ("java.util.Date".equals(rtnTypeName)) {
                    Date str1 = (Date) mth.invoke(o);
                    Date str2 = (Date) mth.invoke(db);
                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        result += "将" + fieldDes + "由\""
                                + DateUtil.format(str1, DateUtil.FORMAT_DATE) + "\"修改为\""
                                + DateUtil.format(str2, DateUtil.FORMAT_DATE) + "\"";
                        result += "<br/>";
                    }
                } else if ("java.math.BigDecimal".equals(rtnTypeName)) {
                    BigDecimal str1 = (BigDecimal) mth.invoke(o);
                    BigDecimal str2 = (BigDecimal) mth.invoke(db);
                    if (str1 != null && str2 != null && str1.compareTo(str2) != 0) {
                        result += "将" + fieldDes + "由\"" + str1.toString() + "\"修改为\""
                                + str2.toString() + "\"";
                        result += "<br/>";
                    }
                }

                fieldDes = "";
            }
        }
        return result;
    }

    public static String toUtf8String(String s) {

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c >= 0 && c <= 255) {
                sb.append(c);
            } else {
                byte[] b;
                try {
                    b = Character.toString(c).getBytes("utf-8");
                } catch (Exception ex) {
                    b = new byte[0];
                }
                for (byte element : b) {
                    int k = element;
                    if (k < 0) {
                        k += 256;
                    }
                    sb.append("%" + Integer.toHexString(k).toUpperCase());
                }
            }
        }
        return sb.toString();
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
