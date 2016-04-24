package com.hjhrq1991.car.Constant;

/**
 * @author hjhrq1991 created at 1/10/16 10 37.
 * @Package com.hjhrq1991.car.Constant
 * @Description:
 */
public class CustomConstant {

//    1.加油，2.洗车，3.停车，4.过路费，5.违章，6.保险，7.维修，8.贷款，9.其他

    public static final int type_title = -1;

    public static final int type_all = 0;
    /**
     * 加油
     */
    public static final int type_gas = 1;
    /**
     * 洗车
     */
    public static final int type_wash = 2;
    /**
     * 停车
     */
    public static final int type_park = 3;
    /**
     * 过路费
     */
    public static final int type_toll = 4;
    /**
     * 违章
     */
    public static final int type_fine = 5;
    /**
     * 保险
     */
    public static final int type_insure = 6;
    /**
     * 维修
     */
    public static final int type_service = 7;
    /**
     * 贷款
     */
    public static final int type_loan = 8;
    /**
     * 其他
     */
    public static final int type_other = 9;


    /**
     * 饼状图
     */
    public static final int type_pie = 0;
    /**
     * 条形图
     */
    public static final int type_bar = 1;
    /**
     * 按类型
     */
    public static final int type_leixing = 0;
    /**
     * 按时间
     */
    public static final int type_time = 1;

    /**
     * 汽油价格请求
     */
    public static final int business_oil = 1;
    /**
     * 天气查询请求
     */
    public static final int business_weather = 2;

    public static final String SETTINGS_USE_GB = "setting_use_GB";
    public static final String SETTINGS_SECOND_GAS = "setting_second_gas";
    public static final String SETTINGS_CALCULATE = "setting_calculate";
    public static final String SETTINGS_SET_CITY = "setting_set_city";
    public static final String SETTINGS_ABOUT = "setting_about";

    public static final String second_gas_null = "0";
    public static final String second_gas_0 = "1";
    public static final String second_gas_90 = "2";
    public static final String second_gas_97 = "3";
    
    //文章，webview加载
    public static final int receiver_wenzhang = 1;
    //ad广告
    public static final int receiver_ad = 2;
    //app升级
    public static final int receiver_updata = 3;
    //预留
    public static final int receiver_extra = 4;

}
