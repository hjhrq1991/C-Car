package com.hjhrq1991.car.Util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

public class AppUtil {
    public static void exit(Context context) {
        int iSdk = getSDK();
        if (iSdk > 7) {
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(startMain);
            System.exit(0);// �?��程序
        } else {
            ActivityManager am = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            am.restartPackage(context.getPackageName());
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    /* 获取sdk版本
    * @return
    */
    static public int getSDK() {
        return Integer.valueOf(android.os.Build.VERSION.SDK);
    }

    public static String getVersionName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "1.0.0";
        }
    }

    public static int getVersionCode(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return pi.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 普通安装
     */
    public static void installApk(Context context, File file) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /**
     * 静默安装
     *
     * @param file
     * @return
     */
    public static boolean slientInstall(Context context, File file) {
        return slientInstall(context, file, null);
    }

    /**
     * 静默安装
     *
     * @param file
     * @return
     */
    public static boolean slientInstall(Context context, File file, OnInstallListener onInstallListener) {
        boolean result = false;
        Process process = null;
        OutputStream out = null;
        try {
            process = Runtime.getRuntime().exec("su");
            out = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(out);
            dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
            dataOutputStream.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " +
                    file.getPath());
            // 提交命令
            dataOutputStream.flush();
            // 关闭流操作
            dataOutputStream.close();
            out.close();
            int value = process.waitFor();
            // 代表成功
            if (value == 0) {
                result = false;
            } else if (value == 1) { // 失败
                result = false;
            } else { // 未知情况
                result = false;
            }

        } catch (IOException e) {
            e.printStackTrace();
            result = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
            result = false;
        }
        // 静默安装失败时启动普通安装
        if (!result) {
            if (onInstallListener != null)
                onInstallListener.onInstallFail();
            installApk(context, file);
        } else {
            if (onInstallListener != null)
                onInstallListener.onInstallSuccess();
        }
        return result;
    }

    public interface OnInstallListener {

        void onInstallSuccess();

        void onInstallFail();
    }

    /**
     * 获取IMEI，如果获取不到则获取mac地址作为唯一标识
     *
     * @param context
     * @return
     */
    public static String getIMEI(Context context) {
        String UUID = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();

        if (TextUtils.isEmpty(UUID)) {
            WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            UUID = info.getMacAddress();
        }

        return UUID;
    }

    /**
     * 获取UUID
     *
     * @return
     */
    public static String getUUID(Context context) {
        TelephonyManager tm = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);

        String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        return deviceUuid.toString();
    }


    public static boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        if (!TextUtils.isEmpty(currentPackageName) && currentPackageName.equals(context.getPackageName())) {
            return true;
        }

        return false;
    }

}
