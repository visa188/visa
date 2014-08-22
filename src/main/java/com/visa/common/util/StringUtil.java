package com.visa.common.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;

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
            List<LinesSrvice> serviceListDB) {
        StringBuffer result = new StringBuffer();
        result.append("新增订单，编号：").append(lineOrder.getOrderSeq());
        return result.toString();
    }
}
