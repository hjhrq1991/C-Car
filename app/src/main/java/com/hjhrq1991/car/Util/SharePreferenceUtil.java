package com.hjhrq1991.car.Util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author hjhrq1991 created at 1/10/16 22 26.
 * @Package com.hjhrq1991.car.Util
 * @Description:
 */
public class SharePreferenceUtil {

    private static String FILENAME = "SettingsPref.xml";
    /**
     * 是否首次登陆
     */
    private static String ISFIRST = "isFirst";
    /**
     * 汽油量是否自动换算
     */
    private static String isAutoTransitionGas = "isAutoTransitionGas";
    /**
     *
     */
    private static String city = "city";

    /**
     * 首次启动时间，用于评价
     */
    private static String firstLoadTime = "date";
    /**
     * 是否评价
     */
    private static String hasEvaluated = "evaluate";

    /**
     * 设置是否首次登陆
     *
     * @param context
     * @param value
     */
    public static void setIsFirst(Context context, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(ISFIRST, value).commit();
    }

    /**
     * 是否是否首次登陆
     *
     * @param context
     * @return
     */
    public static boolean getIsFirst(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getBoolean(ISFIRST, true);
    }

    /**
     * 设置是否自动换算
     *
     * @param context
     * @param value
     */
    public static void setIsAutoTransitionGas(Context context, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(isAutoTransitionGas, value).commit();
    }

    /**
     * 是否自动换算汽油量
     *
     * @param context
     * @return
     */
    public static boolean getIsAutoTransitionGas(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getBoolean(isAutoTransitionGas, true);
    }

    /**
     * 设置城市
     *
     * @param context
     * @param value
     */
    public static void setCity(Context context, String value) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sp.edit().putString(city, value).commit();
    }

    /**
     * 获取默认城市
     *
     * @param context
     * @return
     */
    public static String getCity(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getString(city, "");
    }

    /**
     * @param context
     * @param value
     */
    public static void setFirstLoadTime(Context context, long value) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sp.edit().putLong(firstLoadTime, value).commit();
    }

    /**
     * @param context
     * @return
     */
    public static long getFirstLoadTime(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getLong(firstLoadTime, 0);
    }

    /**
     * 设定是否评价
     *
     * @param context
     * @param value
     */
    public static void setEvaluate(Context context, boolean value) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(hasEvaluated, value).commit();
    }

    /**
     * 获取是否评价
     *
     * @param context
     * @return
     */
    public static boolean getEvaluate(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        return sp.getBoolean(hasEvaluated, false);
    }

}
