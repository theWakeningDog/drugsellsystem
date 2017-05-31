package com.sellsystem.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.springframework.util.StringUtils;

import static java.util.Calendar.getInstance;

public class DateUtil {
    public static final String DATE_FORMAT_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
    public static final String DATE_FORMAT_YMDHMSS = "yyyyMMddHHmmssSSS";
    public static final long nd = 86400000L;
    public static final long nh = 3600000L;
    public static final long nm = 60000L;

    public DateUtil() {
    }

    /**
     * 获取当前时间
     * @return
     */
    public static String getCurrentTime() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar c1 = getInstance();
        return sFormat.format(c1.getTime());
    }

    /**
     * 当前日期
     * @return
     */
    public static String getCurrentDay() {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c1 = getInstance();
        return sFormat.format(c1.getTime());
    }

    /**
     * 字符串转日期
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date stringToDate(String dateStr) throws ParseException {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sFormat.parse(dateStr);
    }

    /**
     * 字符串转时间
     * @param dateStr
     * @return
     * @throws ParseException
     */
    public static Date stringToDateTime(String dateStr) throws ParseException {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sFormat.parse(dateStr);
    }

    public static Date getCurrentDayDate() {
        try {
            return stringToDate(getCurrentDay());
        } catch (ParseException var1) {
            var1.printStackTrace();
            return null;
        }
    }

    /**
     * 日期转字符串
     * @param appointBeginDate
     * @return
     */
    public static String dateToString(Date appointBeginDate) {
        SimpleDateFormat sFormat = new SimpleDateFormat("yyyy-MM-dd");
        return sFormat.format(appointBeginDate);
    }

    /**
     * 日期转字符串，特定格式
     * @param appointBeginDate
     * @param format
     * @return
     */
    public static String dateToString(Date appointBeginDate, String format) {
        SimpleDateFormat sFormat = new SimpleDateFormat(format);
        return sFormat.format(appointBeginDate);
    }

    /**
     * 比较
     * @param start
     * @param end
     * @param index
     * @return
     */
    public static Integer getTimeDifference(Date start, Date end, String index) {
        if(start != null && end != null && !StringUtils.isEmpty(index)) {
            Integer result = Integer.valueOf(0);
            Calendar c1 = getInstance();
            Calendar c2 = getInstance();
            c1.setTime(start);
            c2.setTime(end);
            if(index.equals("year")) {
                result = Integer.valueOf(c2.get(1) - c1.get(1));
            } else if(index.equals("month")) {
                result = Integer.valueOf(c2.get(2) - c1.get(2));
            } else if(index.equals("day")) {
                Calendar diff = getInstance();
                Calendar eCalendar = getInstance();
                diff.setTime(start);
                eCalendar.setTime(end);
                int days = 0;
                while(diff.before(eCalendar)) {
                    ++days;
                    diff.add(6, 1);
                }
                result = Integer.valueOf(days);
            } else {
                long var9;
                if(index.equals("hour")) {
                    var9 = end.getTime() - start.getTime();
                    result = Integer.valueOf((int)(var9 / 3600000L));
                } else if(index.equals("minute")) {
                    var9 = end.getTime() - start.getTime();
                    result = Integer.valueOf((int)(var9 / 60000L));
                } else {
                    if(!index.equals("second")) {
                        return null;
                    }
                    result = Integer.valueOf(c2.get(13) - c1.get(13));
                }
            }
            return result;
        } else {
            return null;
        }
    }

    /**
     * 日期相加
     * @param date
     * @param num
     * @param index
     * @return
     */
    public static Date timeToAdd(Date date, Integer num, String index) {
        if(date != null && num != null && index != null && index != "") {
            Calendar cd = getInstance();
            cd.setTime(date);
            if(index.equals("second")) {
                cd.add(13, num.intValue());
            } else if(index.equals("minute")) {
                cd.add(12, num.intValue());
            } else if(index.equals("hour")) {
                cd.add(10, num.intValue());
            } else if(index.equals("day")) {
                cd.add(5, num.intValue());
            } else if(index.equals("month")) {
                cd.add(2, num.intValue());
            } else {
                if(!index.equals("year")) {
                    return null;
                }
                cd.add(1, num.intValue());
            }
            return cd.getTime();
        } else {
            return date;
        }
    }
}
