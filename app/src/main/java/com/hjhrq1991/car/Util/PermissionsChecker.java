package com.hjhrq1991.car.Util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;

/**
 * @author hjhrq1991 created at 2/20/16 16 59.
 * @Package com.hjhrq1991.car.Util
 * @Description:
 */
public class PermissionsChecker {
    private Context mContext;

    private String[] mDangerousPermissions = {
            Manifest.permission.WRITE_CONTACTS,
            Manifest.permission.GET_ACCOUNTS,
            Manifest.permission.READ_CONTACTS,
            Manifest.permission.READ_CALL_LOG,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_CALL_LOG,
            Manifest.permission.USE_SIP,
            Manifest.permission.PROCESS_OUTGOING_CALLS,
            Manifest.permission.READ_CALENDAR,
            Manifest.permission.WRITE_CALENDAR,
            Manifest.permission.CAMERA,
            Manifest.permission.BODY_SENSORS,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.READ_SMS,
            Manifest.permission.RECEIVE_WAP_PUSH,
            Manifest.permission.RECEIVE_MMS,
            Manifest.permission.RECEIVE_SMS,
            Manifest.permission.SEND_SMS
//            Manifest.permission.READ_CELL_BROADCASTS
    };

    public PermissionsChecker(Context context) {
        mContext = context.getApplicationContext();
    }

    // 判断权限集合
    public boolean lacksPermissions(String... permissions) {
        for (String permission : permissions) {
            if (lacksPermission(permission)) {
                return true;
            }
        }
        return false;
    }

    // 判断是否缺少权限
    private boolean lacksPermission(String permission) {
        for (String dangerousPermission : mDangerousPermissions) {
            if (dangerousPermission.equals(permission)) {
                LogUtil.logi("hrq====", ContextCompat.checkSelfPermission(mContext.getApplicationContext(), permission) + "==" + (ContextCompat.checkSelfPermission(mContext, permission) == PackageManager.PERMISSION_DENIED));
                return ContextCompat.checkSelfPermission(mContext.getApplicationContext(), permission) == PackageManager.PERMISSION_DENIED;
            }
        }
        return false;
    }
}