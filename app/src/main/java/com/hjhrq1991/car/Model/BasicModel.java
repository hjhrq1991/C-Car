package com.hjhrq1991.car.Model;

import org.json.JSONObject;

/**
 * @author hjhrq1991 created at 1/28/16 10 32.
 * @Package com.hjhrq1991.car.Model
 * @Description:城市基本信息
 */
public class BasicModel {

    private String city;//城市名
    private String cnty;//城市所在国家名
    private String id;//城市ID
    private String lat;//维度
    private String lon;//经度
    private String loc;//数据更新的当地时间
    private String utc;//数据更新的UTC时间

    public BasicModel() {
    }

    public BasicModel(JSONObject mJson) {
        if (mJson != null) {
            this.city = mJson.optString("city");
            this.cnty = mJson.optString("cnty");
            this.id = mJson.optString("id");
            this.lat = mJson.optString("lat");
            this.lon = mJson.optString("lon");

            if (mJson.has("update")) {
                this.loc = mJson.optJSONObject("update").optString("loc");
                this.utc = mJson.optJSONObject("update").optString("utc");
            }

        }
    }

    public String getCity() {
        return city;
    }

    public String getCnty() {
        return cnty;
    }

    public String getId() {
        return id;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getLoc() {
        return loc;
    }

    public String getUtc() {
        return utc;
    }
}
