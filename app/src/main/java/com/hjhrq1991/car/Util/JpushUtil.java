package com.hjhrq1991.car.Util;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.hjhrq1991.car.Application.CarApplication;
import com.hjhrq1991.car.Config.Config;
import com.hjhrq1991.car.R;

import cn.jpush.android.api.CustomPushNotificationBuilder;
import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

/**
 * @author hjhrq1991 created at 3/30/16 16 53.
 * @Package com.hjhrq1991.car.Util
 * @Description:Jpush工具类，注册、设置别名、设置通知栏
 */
public class JpushUtil {

    /**
     * 设置自定义显示样式
     */
    public static void setJPush(Context mContext) {
        JPushInterface.setDebugMode(Config.isDebug);
        JPushInterface.init(mContext);
        if (Config.isDebug) {
            JPushInterface.setAlias(mContext, "debug", mAliasCallback);
        }

        CustomPushNotificationBuilder builder = new CustomPushNotificationBuilder(mContext,
                R.layout.customer_notitfication_layout, R.id.icon, R.id.title, R.id.text);  // 指定定制的 Notification Layout
        builder.statusBarDrawable = R.mipmap.ic_notification;      // 指定最顶层状态栏小图标
        builder.layoutIconDrawable = R.mipmap.ic_launcher;   // 指定下拉状态栏时显示的通知图标
        JPushInterface.setDefaultPushNotificationBuilder(builder);
    }

    private static final String TAG = "JPush";
    private static final TagAliasCallback mAliasCallback = (code, alias, tags) -> {
        String logs;
        switch (code) {
            case 0:
                LogUtil.logi(TAG, "RegisterID:" + JPushInterface.getRegistrationID(CarApplication.getInstance()));
                logs = "Set tag and alias success";
                LogUtil.logi(TAG, logs);
                break;

            case 6002:
                logs = "Failed to set alias and tags due to timeout. Try again after 60s.";
                LogUtil.loge(TAG, logs);
                break;

            default:
                logs = "Failed with errorCode = " + code;
                LogUtil.loge(TAG, logs);
        }
    };
}
