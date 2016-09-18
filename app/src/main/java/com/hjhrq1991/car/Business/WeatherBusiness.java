package com.hjhrq1991.car.Business;

import android.content.Context;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.hjhrq1991.car.Base.Command;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.Event.WeatherEvent;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.LogUtil;
import com.hjhrq1991.car.db.CacheDB;
import com.hjhrq1991.tool.Util.TimeUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author hjhrq1991 created at 1/12/16 21 41.
 * @Package com.hjhrq1991.car.Business
 * @Description:
 */
public class WeatherBusiness extends Command {

    public WeatherBusiness(Parameters parameters) {
        super(parameters);
    }

    @Override
    public void execute(Context mContext) {
        String url = mContext.getString(R.string.get_weather_request);
        ApiStoreSDK.execute(url, ApiStoreSDK.GET, parameters, new ApiCallBack() {
            @Override
            public void onSuccess(int status, String responseString) {
                LogUtil.logi("Result", status + "===" + responseString);
                WeatherEvent weatherEvent = new WeatherEvent(responseString);

                if (weatherEvent.isSuccess()) {
                    CacheDB cacheDB = CacheDB.getCache(CustomConstant.business_weather);

                    if (cacheDB != null) {
                        cacheDB.content = responseString;
                        cacheDB.time = TimeUtils.getTime();
                        cacheDB.save();
                    } else {
                        cacheDB = new CacheDB();
                        cacheDB.content = responseString;
                        cacheDB.time = TimeUtils.getTime();
                        cacheDB.type = CustomConstant.business_weather;
                        cacheDB.save();
                    }
                    EventBus.getDefault().post(weatherEvent);
                }
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onError(int status, String responseString, Exception e) {
            }

        });
    }
}
