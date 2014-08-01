package com.visa.common.util;

import org.apache.commons.lang.StringUtils;

public class StringUtil {
	/**
	 * 为字符串左侧添加0
	 * */
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
}
