package com.hjhrq1991.car.Business;

import android.content.Context;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.hjhrq1991.car.Base.Command;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.Event.OilPriceEvent;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.db.CacheDB;
import com.hjhrq1991.tool.Util.TimeUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @author hjhrq1991 created at 1/12/16 21 43.
 * @Package com.hjhrq1991.car.Business
 * @Description:
 */
public class OilPriceBusiness extends Command {

    public OilPriceBusiness(Parameters parameters) {
        super(parameters);
    }

    @Override
    public void execute(Context mContext) {
        String url = mContext.getString(R.string.get_oil_price_request);
        ApiStoreSDK.execute(url, ApiStoreSDK.GET, parameters, new ApiCallBack() {
            @Override
            public void onSuccess(int status, String responseString) {
//                System.out.println("hrq-----" + status + "---" + responseString);
                OilPriceEvent event = new OilPriceEvent(responseString);

                if (event.isSuccess()) {
                    CacheDB cacheDB = CacheDB.getCache(CustomConstant.business_oil);

                    if (cacheDB != null) {
                        cacheDB.content = responseString;
                        cacheDB.time = TimeUtils.getTime();
                        cacheDB.save();
                    } else {
                        cacheDB = new CacheDB();
                        cacheDB.content = responseString;
                        cacheDB.time = TimeUtils.getTime();
                        cacheDB.type = CustomConstant.business_oil;
                        cacheDB.save();
                    }

                    EventBus.getDefault().post(event);
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
