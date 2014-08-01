package com.visa.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

/**
 * 日期处理器
 * 
 * @author zxwu
 */
public final class DateUtil {
    private DateUtil() {

    }

    // 标准日期时间格式
    /**
     * yyyy-MM
     */
    public static final String FORMAT_MONTH = "yyyy-MM";
    /**
     * yyyy-MM-dd
     */
    public static final String FORMAT_DATE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm
     */
    public static final String FORMAT_DATE_MINUTE = "yyyy-MM-dd HH:mm";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FORMAT_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * HH:mm:ss
     */
    public static final String FORMAT_TIME = "HH:mm:ss";
    /**
     * HH:mm
     */
    public static final String FORMAT_MINUTE = "HH:mm";

    // 无符号格式
    /**
     * yyyyMM
     */
    public static final String FORMAT_MONTH_UNSIGNED = "yyyyMM";
    /**
     * yyyyMMdd
     */
    public static final String FORMAT_DATE_UNSIGNED = "yyyyMMdd";
    /**
     * yyyyMMddHHmm
     */
    public static final String FORMAT_DATE_MINUTE_UNSIGNED = "yyyyMMddHHmm";
    /**
     * yyyyMMddHHmmss
     */
    public static final String FORMAT_DATE_TIME_UNSIGNED = "yyyyMMddHHmmss";

    /**
     * HHmmss
     */
    public static final String FORMAT_TIME_UNSIGNED = "HHmmss";
    /**
     * HHmm
     */
    public static final String FORMAT_MINUTE_UNSIGNED = "HHmm";

    /**
     * 按指定格式格式化时期时间
     * 
     * @param date date
     * @param format format
     * @return string.
     */
    public static String format(Date date, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        return date == null ? "" : formater.format(date);
    }

    /**
     * 按指定格式解析字符串，将字符串转为日期时间格式
     * 
     * @param str string
     * @param format format
     * @return date
     */
    public static Date parse(String str, String format) {
        SimpleDateFormat formater = new SimpleDateFormat(format);
        try {
            return formater.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    private static final long HOUR_MILLISECONDS = 3600000;
    private static final long DAY_MILLISECONDS = 24 * HOUR_MILLISECONDS;
    private static final long WEEK_MILLISECONDS = 7 * DAY_MILLISECONDS;

    /**
     * 将输入的日期字符串根据模式解析成智能的日期显示字符串 ，可能的结果： "2011年1月1日" "去年1月1日" "1月1日" "昨天"
     * 
     * @param str 日期字符串
     * @param format 模式
     * @return smart字符串
     */
    public static String parseToSmartDate(String str, String format) {
        if (!StringUtils.isEmpty(str)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();

        Date date = parse(str, format);

        long offsetMs = System.currentTimeMillis() - date.getTime();
        if (offsetMs > 0) {
            if (offsetMs < HOUR_MILLISECONDS) {
                // 1小时以内
                return sb.append(offsetMs / 60000).append("分钟前").toString();
            } else if (offsetMs < WEEK_MILLISECONDS) {
                // 1周以内
                Calendar cal = Calendar.getInstance();
                cal.setTime(date);

                Calendar currentCal = Calendar.getInstance();
                currentCal.set(Calendar.HOUR_OF_DAY, 0);
                currentCal.set(Calendar.MINUTE, 0);
                currentCal.set(Calendar.SECOND, 0);
                currentCal.set(Calendar.MILLISECOND, 0);

                if (cal.before(currentCal)) {
                    // 今天以前
                    currentCal.add(Calendar.DAY_OF_MONTH, -1);
                    if (cal.before(currentCal)) {
                        // 昨天以前
                        currentCal.add(Calendar.DAY_OF_MONTH, -1);
                        if (cal.before(currentCal)) {
                            // 前天以前
                            return sb.append(offsetMs / DAY_MILLISECONDS).append("天前").toString();
                        } else {
                            // 前天
                            return "前天";
                        }
                    } else {
                        // 昨天
                        return "昨天";
                    }
                } else {
                    // 今天
                    return sb.append(offsetMs / HOUR_MILLISECONDS).append("小时前").toString();
                }
            } else {
                // 超过1周
                return format(date, FORMAT_DATE);
            }
        } else {
            // 超过当前时间，可能是服务器时间有问题，返回标准时间格式，容错处理
            return format(date, FORMAT_DATE);
        }
    }

    /**
     * 获取今天的日期字符串
     * 
     * @param pattern 日期模式
     * @return 日期字符串
     */
    public static String getTodayDateTime(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 获取某个日期N天前后的日期
     * 
     * @param date 日期字符串，复合pattern
     * @param pattern 日期字符串模式
     * @param interval 以天为单位的时间间隔，正数为几天后，负数为几天前
     * @return N天前的日期字符串
     */
    public static String addDate(Date date, String pattern, int interval) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, interval);
        return format(cal.getTime(), pattern);
    }

    /**
     * 获取某个日期N天前后的日期
     * 
     * @param date 日期字符串，复合pattern
     * @param pattern 日期字符串模式
     * @param interval 以天为单位的时间间隔，正数为几天后，负数为几天前
     * @return N天前的日期字符串
     */
    public static String addDate(String date, String pattern, int interval) {
        try {
            return addDate(parse(date, pattern), pattern, interval);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            return "";
        }
    }

    /**
     * 获取今天N天前后的日期
     * 
     * @param pattern 日期字符串模式
     * @param interval 以天为单位的时间间隔，正数为几天后，负数为几天前
     * @return N天前的日期字符串
     */
    public static String addDate(String pattern, int interval) {
        return addDate(new Date(), pattern, interval);
    }

    /**
     * @return 今天0:0:0的Calender
     */
    public static Calendar startOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c;
    }

    /**
     * @return 今天23:59:59的Calender
     */
    public static Calendar endOfToday() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c;
    }
}
