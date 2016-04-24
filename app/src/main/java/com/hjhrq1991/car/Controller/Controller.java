package com.hjhrq1991.car.Controller;

import android.content.Context;

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.hjhrq1991.car.Base.Command;
import com.hjhrq1991.car.Business.OilPriceBusiness;
import com.hjhrq1991.car.Business.WeatherBusiness;
import com.hjhrq1991.car.R;

/**
 * @author hjhrq1991 created at 1/12/16 21 31.
 * @Package com.hjhrq1991.car.Controller
 * @Description:
 */
public class Controller {

    private Context mContext;

    public Controller(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取天气、生活指数等信息
     *
     * @param city 城市名称
     */
    public void getWeather(String city) {
        Parameters para = new Parameters();
        para.put("city", city);
        Command command = new WeatherBusiness(para);
        command.execute(mContext);
    }

    /**
     * 获取某地区油价
     *
     * @param prov 省份
     */
    public void getOilPrice(String prov) {
        Parameters para = new Parameters();
        para.put("prov", prov);
        Command command = new OilPriceBusiness(para);
        command.execute(mContext);
    }
}
