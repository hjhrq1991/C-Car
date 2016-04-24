package com.hjhrq1991.car.Util;

import android.util.Log;

import com.hjhrq1991.car.Config.Config;

/**
 * @author hjhrq1991 created at 3/30/16 15 05.
 * @Package com.hjhrq1991.car.Util
 * @Description:
 */
public class LogUtil {

    public static void logi(String tag, String msg) {
        if (Config.isDebug) {
            Log.i(tag, msg);
        }
    }

    public static void loge(String tag, String msg) {
        if (Config.isDebug) {
            Log.e(tag, msg);
        }
    }
}
