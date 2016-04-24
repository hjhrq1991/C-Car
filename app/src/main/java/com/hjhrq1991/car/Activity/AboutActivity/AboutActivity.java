package com.hjhrq1991.car.Activity.AboutActivity;

import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.AppUtil;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.umeng.update.UpdateStatus;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author hjhrq1991 created at 2/19/16 15 42.
 * @Package com.hjhrq1991.car.Activity.AboutActivity
 * @Description:
 */
public class AboutActivity extends BaseActivity implements UmengUpdateListener {

    @Bind(R.id.about_version)
    TextView mVersionTv;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_about_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVersionTv.setText(String.format(getString(R.string.about_version), AppUtil.getVersionName(this)));
        UmengUpdateAgent.setUpdateListener(this);
    }

    @OnClick({R.id.about_check, R.id.about_group, R.id.about_author, R.id.about_feedback})
    public void onClick(View v) {
        ClipboardManager cmb = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        switch (v.getId()) {
            case R.id.about_check:
                //手动检查版本
                UmengUpdateAgent.forceUpdate(getApplicationContext());
                break;
            case R.id.about_group:
                cmb.setText(getString(R.string.about_group).replace("官方群：", ""));
                Toast.makeText(this, getString(R.string.copy_tips), Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_author:
                cmb.setText(getString(R.string.about_author_qq));
                Toast.makeText(this, getString(R.string.copy_tips2), Toast.LENGTH_SHORT).show();
                break;
            case R.id.about_feedback:
                FeedbackAPI.openFeedbackActivity(getApplicationContext());
                break;
        }
    }

    @Override
    public void onUpdateReturned(int updateStatus, UpdateResponse updateResponse) {
        switch (updateStatus) {
            case UpdateStatus.Yes: // has update
                UmengUpdateAgent.showUpdateDialog(this, updateResponse);
                break;
            case UpdateStatus.No: // has no update
                Toast.makeText(getApplicationContext(), "已是最新版本!", Toast.LENGTH_SHORT).show();
                break;
            case UpdateStatus.NoneWifi: // none wifi
                Toast.makeText(getApplicationContext(), "请在在WIFI下更新!", Toast.LENGTH_SHORT).show();
                break;
            case UpdateStatus.Timeout: // time out
                Toast.makeText(getApplicationContext(), "连接超时，请稍后重试!", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UmengUpdateAgent.setUpdateListener(null);
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, AboutActivity.class));
    }

}
