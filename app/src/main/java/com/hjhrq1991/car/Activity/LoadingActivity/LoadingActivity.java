package com.hjhrq1991.car.Activity.LoadingActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.hjhrq1991.car.Activity.MainActivityV3.MainActivityV3;
import com.hjhrq1991.car.Activity.PermissionsActivity.PermissionsActivity;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.LogUtil;
import com.hjhrq1991.car.Util.PermissionsChecker;
import com.hjhrq1991.tool.Base.BaseActivity;

/**
 * @author hjhrq1991 created at 1/10/16 20 40.
 * @Package com.hjhrq1991.car.Activity
 * @Description:
 */
public class LoadingActivity extends BaseActivity {

    private static final int REQUEST_CODE = 0; // 请求码

    // 所需的全部权限
    static final String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.SYSTEM_ALERT_WINDOW,
    };

    private PermissionsChecker mPermissionsChecker; // 权限检测器


    @Override
    public int getLayoutResource() {
        return R.layout.activity_loading_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkPermissions();
    }

    protected void checkPermissions() {
        LogUtil.logi("hrq====", "" + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mPermissionsChecker = new PermissionsChecker(this);
            // 缺少权限时, 进入权限配置页面
            if (mPermissionsChecker.lacksPermissions(PERMISSIONS)) {
                startPermissionsActivity();
                return;
            }
        }

        handler.postDelayed(mCallBack, 2000);
    }

    private Runnable mCallBack = new Runnable() {
        public void run() {
            MainActivityV3.launch(LoadingActivity.this);
            finish();
        }
    };

    @Override
    public void onBackPressed() {
        handler.removeCallbacks(mCallBack);
        super.onBackPressed();
    }

    private void startPermissionsActivity() {
        PermissionsActivity.startActivityForResult(this, REQUEST_CODE, PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 拒绝时, 关闭页面, 缺少主要权限, 无法运行
        if (requestCode == REQUEST_CODE && resultCode == PermissionsActivity.PERMISSIONS_DENIED) {

        } else {
            MainActivityV3.launch(this);
        }
        finish();
    }
}
