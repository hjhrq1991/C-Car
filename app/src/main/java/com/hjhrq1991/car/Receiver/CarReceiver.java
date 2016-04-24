package com.hjhrq1991.car.Receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.hjhrq1991.car.Activity.CityActivity.CityActivity;
import com.hjhrq1991.car.Activity.MainActivity.MainActivityV2;
import com.hjhrq1991.car.Activity.SettingsActivity.SettingsActivity;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.Util.AppUtil;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * @author hjhrq1991 created at 3/30/16 16 24.
 * @Package com.hjhrq1991.car.Receiver
 * @Description:
 */
public class CarReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";


    @Override
    public void onReceive(Context context, Intent intent) {

        Bundle bundle = intent.getExtras();
        Log.d(TAG, "[CarReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "[CarReceiver] 接收Registration Id : " + regId);

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[CarReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            Log.d(TAG, "[CarReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[CarReceiver] 接收到推送下来的通知的ID: " + notifactionId);
            String extra = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[CarReceiver] 用户点击打开了通知");
            String extra = intent.getStringExtra(JPushInterface.EXTRA_EXTRA);

            if (extra != null) {
                try {
                    JSONObject mJson = new JSONObject(extra);
                    int type = mJson.optInt("type");
                    String url = mJson.optString("url");
                    switch (type) {
                        case CustomConstant.receiver_wenzhang:
                            if (!TextUtils.isEmpty(url)) {
                                //这里打开一个webview
                                Intent intent1 = new Intent(context, SettingsActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent1);
                            }
                            break;
                        case CustomConstant.receiver_ad:
                            String imgUrl = mJson.optString("img");
                            if (!TextUtils.isEmpty(url)) {
                                //在这里打开一个全局的广告图
                                Intent intent1 = new Intent(context, CityActivity.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent1);
                            }
                            break;
                        case CustomConstant.receiver_updata:
                            int verisonCode = mJson.optInt("version");
                            int oldCode = AppUtil.getVersionCode(context);

                            if (verisonCode > oldCode) {
                                //这里进行版本升级
                                Intent intent1 = new Intent(context, MainActivityV2.class);
                                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent1);
                            }

                            break;
                        default:
                            break;
                    }

                } catch (JSONException e) {

                }
                System.out.println("hrq------" + extra);
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[CarReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            //在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity， 打开一个网页等..

        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            Log.w(TAG, "[CarReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            Log.d(TAG, "[CarReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 打印所有的 intent extra 数据
    private static String printBundle(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}
