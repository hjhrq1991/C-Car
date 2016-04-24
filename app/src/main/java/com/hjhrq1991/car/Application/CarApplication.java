package com.hjhrq1991.car.Application;

import android.content.Context;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.app.Application;
import com.alibaba.sdk.android.feedback.impl.FeedbackAPI;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.hjhrq1991.car.Config.Config;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;
import com.umeng.update.UmengUpdateAgent;

/**
 * @author hjhrq1991 created at 1/10/16 11 13.
 * @Package com.hjhrq1991.car.Application
 * @Description:
 */
public class CarApplication extends Application {

    private static CarApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;
        ApiStoreSDK.init(this, Config.api_key);
        ActiveAndroid.initialize(this);

        //禁用自动升级SDK信息检测功能，2.6.1会一直提示res未完全拷贝
        UmengUpdateAgent.setUpdateCheckConfig(false);

        //第二个参数是appkey，就是百川应用创建时候的appkey
        FeedbackAPI.initAnnoy(this, Config.baichuan_key);

        refWatcher = LeakCanary.install(this);
    }

    private RefWatcher refWatcher;

    public static RefWatcher getRefWatcher(Context context) {
        return instance.refWatcher;
    }

    public static CarApplication getInstance() {
        return instance;
    }

}
