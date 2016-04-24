package com.hjhrq1991.tool.Util;

import android.text.TextUtils;
import android.text.format.DateUtils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import static android.text.format.DateUtils.FORMAT_NUMERIC_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_DATE;
import static android.text.format.DateUtils.FORMAT_SHOW_YEAR;
import static android.text.format.DateUtils.MINUTE_IN_MILLIS;


public class TimeUtils {

    //private final static SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //private final static SimpleDateFormat dateFormater2 = new SimpleDateFormat("yyyy-MM-dd");
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };


    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    /**
     * 将字符串转位日期类型
     *
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 以友好的方式显示时间
     *
     * @param sdate
     * @return
     */
    public static String friendly_time(String sdate) {
        Date time = toDate(sdate);
        if (time == null) {
            return "Unknown";
        }
        String ftime = "";
        Calendar cal = Calendar.getInstance();

        //判断是否是同一天
        String curDate = dateFormater2.get().format(cal.getTime());
        String paramDate = dateFormater2.get().format(time);
        if (curDate.equals(paramDate)) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
            return ftime;
        }

        long lt = time.getTime() / 86400000;
        long ct = cal.getTimeInMillis() / 86400000;
        int days = (int) (ct - lt);
        if (days == 0) {
            int hour = (int) ((cal.getTimeInMillis() - time.getTime()) / 3600000);
            if (hour == 0)
                ftime = Math.max((cal.getTimeInMillis() - time.getTime()) / 60000, 1) + "分钟前";
            else
                ftime = hour + "小时前";
        } else if (days == 1) {
            ftime = "昨天";
        } else if (days == 2) {
            ftime = "前天";
        } else if (days > 2 && days <= 10) {
            ftime = days + "天前";
        } else if (days > 10) {
            ftime = dateFormater2.get().format(time);
        }
        return ftime;
    }

    /**
     * 判断给定字符串时间是否为今日
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 判断给定的两个字符串时间是否为同一天
     *
     * @param sdate
     * @return boolean
     */
    public static boolean isDateEqual(String sdate, String ndate) {
        boolean b = false;
        Date time = toDate(sdate);
        Date newdate = toDate(ndate);
        if (time != null) {
            String newDate = dateFormater2.get().format(newdate);
            String timeDate = dateFormater2.get().format(time);
            if (newDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }


    /**
     * Get relative time for date
     *
     * @param date
     * @return relative time
     */
    public static CharSequence getRelativeTime(final Date date) {
        long now = System.currentTimeMillis();
        if (Math.abs(now - date.getTime()) > 60000)
            return DateUtils.getRelativeTimeSpanString(date.getTime(), now,
                    MINUTE_IN_MILLIS, FORMAT_SHOW_DATE | FORMAT_SHOW_YEAR
                            | FORMAT_NUMERIC_DATE);
        else
            return "just now";
    }

    /**
     * 时间比较(天)
     *
     * @param newDate
     * @param oldDate
     * @return hjhrq1991 2014-3-21上午9:59:52
     * @description
     */
    public static long compareDateDay(String newDate, String oldDate) {
        if (TextUtils.isEmpty(newDate) || TextUtils.isEmpty(oldDate)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newD = df.parse(newDate);
            Date oldD = df.parse(oldDate);
            long diff = newD.getTime() - oldD.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            return days;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 时间比较(小时)
     *
     * @param newDate
     * @param oldDate
     * @return hjhrq1991 2014-3-21上午9:59:52
     * @description
     */
    public static long compareDateHour(String newDate, String oldDate) {
        if (TextUtils.isEmpty(newDate) || TextUtils.isEmpty(oldDate)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date newD = df.parse(newDate);
            Date oldD = df.parse(oldDate);
            long diff = newD.getTime() - oldD.getTime();
            long days = diff / (1000 * 60 * 60);
            return days;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 时间比较
     *
     * @param nowDate 当前时间
     * @param oldDate 要比较的时间
     * @return hjhrq1991 2014-3-21上午9:59:52
     * @description
     */
    public static long compareTime(String nowDate, String oldDate) {
        if (TextUtils.isEmpty(nowDate) || TextUtils.isEmpty(oldDate)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date nowD = df.parse(nowDate);
            Date oldD = df.parse(oldDate);
            long diff = nowD.getTime() - oldD.getTime();
            return diff;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 时间比较是否超出今天
     *
     * @param nowDate 当前时间
     * @param oldDate 要比较的时间
     * @return hjhrq1991 2014-3-21上午9:59:52
     * @description
     */
    public static long compareDate(String nowDate, String oldDate) {
        if (TextUtils.isEmpty(nowDate) || TextUtils.isEmpty(oldDate)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date nowD = df.parse(nowDate);
            Date oldD = df.parse(oldDate);
            long diff = nowD.getTime() - oldD.getTime();
            return diff;
        } catch (Exception e) {
            return 0;
        }

    }

    /**
     * 获取当前系统时间(北京时间)
     *
     * @return
     */
    public static String getTimeNow() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Date());
    }

    /**
     * 获取当前系统时间(北京时间)
     *
     * @return
     */
    public static String getTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Date());
    }

    /**
     * 根据时间戳转换时间
     *
     * @param timestamp
     * @return
     */
    public static String getTime(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Timestamp(timestamp));
    }

    /**
     * 根据时间戳转换时间
     *
     * @param timestamp
     * @return
     */
    public static String getDateToString(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Timestamp(timestamp));
    }

    /**
     * 根据时间戳转换时间
     *
     * @param timestamp
     * @return
     */
    public static String getDate(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Timestamp(timestamp));
    }

    /**
     * 根据时间戳转换时间
     *
     * @param timestamp
     * @return
     */
    public static String getYearMonth(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Timestamp(timestamp));
    }

    /**
     * 根据时间戳转换时间
     *
     * @param timestamp
     * @return
     */
    public static String getYear(long timestamp) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Timestamp(timestamp));
    }

    public static long stringParseTimestamp(String date) {
        if (TextUtils.isEmpty(date)) {
            return -1;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date diff = df.parse(date);
            return diff.getTime();
        } catch (ParseException e) {
            return 0;
        }

    }

    public static String tranTime(int timestamp) {
        String format = "mm:ss";
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Timestamp(timestamp));
    }

    /**
     * 获取当前系统日期(北京时间)
     *
     * @return
     */
    public static String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        formatter.setTimeZone(TimeZone.getTimeZone("GMT+08:00"));
        return formatter.format(new Date());
    }

    public static long getcurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 转化为字符串日期
     *
     * @param year
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static String toStringData(int year, int monthOfYear, int dayOfMonth) {
        String monthString = monthOfYear < 10 ? "0" + monthOfYear : "" + monthOfYear;
        String dayString = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        return year + "-" + monthString + "-" + dayString;
    }
} 