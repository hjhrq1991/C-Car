package com.hjhrq1991.car.Util;

import android.content.Context;
import android.support.annotation.Nullable;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;

/**
 * @author hjhrq1991 created at 3/28/16 17 51.
 * @Package com.hjhrq1991.car.Util
 * @Description:
 */
public class LocationUtil {

    private static AMapLocationClient mLocationClient = null;

    private volatile static LocationUtil mLocationUtil;

    public LocationUtil(Context context, OnLocationListener mOnLocationListener) {
        initLocation(context, mOnLocationListener);
    }

    /**
     * @param context
     * @param mOnLocationListener
     * @return
     */
    public static LocationUtil getIntance(Context context, @Nullable OnLocationListener mOnLocationListener) {
        if (mLocationUtil == null) {
            synchronized (LocationUtil.class) {
                if (mLocationUtil == null) {
                    mLocationUtil = new LocationUtil(context, mOnLocationListener);
                }
            }
        }

        return mLocationUtil;
    }

//    public boolean isClientInit() {
//
//        return true;
//    }

    private void initLocation(Context context, OnLocationListener mOnLocationListener) {
        //初始化定位
        mLocationClient = new AMapLocationClient(context.getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(aMapLocation -> {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                if (mOnLocationListener != null)
                    mOnLocationListener.locationSuccess(aMapLocation);
            }
        });

        // 初始化定位参数
        AMapLocationClientOption mLocationOption = new AMapLocationClientOption();
        //设置定位模式Hight_Accuracy为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(false);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    /**
     * 开始定位
     *
     * @param mLocationOption 设置定位属性，可为空
     */
    public void startLocation(@Nullable AMapLocationClientOption mLocationOption) {
        if (mLocationOption != null) {
            mLocationClient.setLocationOption(mLocationOption);
        }
        if (mLocationClient != null) {
            mLocationClient.startLocation();
        }
    }

    /**
     * 关闭定位
     */
    public void stopLocation() {
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
            mLocationClient = null;
        }
    }

    public interface OnLocationListener {
        void locationSuccess(AMapLocation aMapLocation);
    }


//    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//    amapLocation.getLatitude();//获取纬度
//    amapLocation.getLongitude();//获取经度
//    amapLocation.getAccuracy();//获取精度信息
//    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    Date date = new Date(amapLocation.getTime());
//    df.format(date);//定位时间
//    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//    amapLocation.getCountry();//国家信息
//    amapLocation.getProvince();//省信息
//    amapLocation.getCity();//城市信息
//    amapLocation.getDistrict();//城区信息
//    amapLocation.getStreet();//街道信息
//    amapLocation.getStreetNum();//街道门牌号信息
//    amapLocation.getCityCode();//城市编码
//    amapLocation.getAdCode();//地区编码
//    amapLocation.getAoiName();//获取当前定位点的AOI信息

}
