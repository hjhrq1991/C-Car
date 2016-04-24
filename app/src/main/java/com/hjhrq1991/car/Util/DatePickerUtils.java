package com.hjhrq1991.car.Util;

import android.app.FragmentManager;
import android.text.TextUtils;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.Date;

/**
 * @author hjhrq1991 created at 12/25/15 16 34.
 * @Package com.qoocc.xite.community.Utils
 * @Description: 日期选择器工具类
 */
public class DatePickerUtils {

    /**
     * 初始化默认时间，若为空则取当前时间为准
     *
     * @param manager
     * @param callBack 回调
     * @param date     初始化当前显示的日期
     * @param tag      标识
     */
    public static void initTime(FragmentManager manager, DatePickerDialog.OnDateSetListener callBack, String date, int tag) {
        int year = 0;
        int month = 0;
        int day = 0;
        Calendar calendar = Calendar.getInstance();
        if (!TextUtils.isEmpty(date)) {
            calendar.setTime(new Date(date.replace(date.substring(4, 5), "/")));
        }
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setDate(manager, callBack, year, month, day, tag);
    }

    /**
     * 唤起日期选择器
     *
     * @param manager
     * @param callBack
     * @param year
     * @param month
     * @param day
     * @param tag      标识
     */
    public static void setDate(FragmentManager manager, DatePickerDialog.OnDateSetListener callBack, int year, int month, int day, int tag) {
        DatePickerDialog dpd;
        dpd = DatePickerDialog.newInstance(callBack, year, month, day, tag);
        dpd.show(manager, "Datepickerdialog");
    }

}
