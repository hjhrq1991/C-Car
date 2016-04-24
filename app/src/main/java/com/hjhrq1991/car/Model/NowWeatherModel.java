package com.hjhrq1991.car.Model;

import org.json.JSONObject;

/**
 * @author hjhrq1991 created at 1/28/16 10 31.
 * @Package com.hjhrq1991.car.Model
 * @Description:实时天气
 */
public class NowWeatherModel {

    private String code;//天气代码
    private String txt;//天气描述
    private String fl;//体感温度
    private String hum;//湿度(%)
    private String pcpn;//降雨量(mm)
    private String pres;//气压
    private String tmp;//当前温度(摄氏度)
    private String vis;//能见度(km)
    private String deg;//风向角度
    private String dir;//风向方向
    private String sc;//风力等级
    private String spd;//风速(Kmph)

    public NowWeatherModel() {
    }

    public NowWeatherModel(JSONObject mJson) {
        if (mJson != null) {
            if (mJson.has("cond")) {
                this.code = mJson.optJSONObject("cond").optString("code");
                this.txt = mJson.optJSONObject("cond").optString("txt");
            }

            this.fl = mJson.optString("fl");
            this.hum = mJson.optString("hum");
            this.pcpn = mJson.optString("pcpn");
            this.pres = mJson.optString("pres");
            this.tmp = mJson.optString("tmp");
            this.vis = mJson.optString("vis");

            if (mJson.has("wind")) {
                this.deg = mJson.optJSONObject("wind").optString("deg");
                this.dir = mJson.optJSONObject("wind").optString("dir");
                this.sc = mJson.optJSONObject("wind").optString("sc");
                this.spd = mJson.optJSONObject("wind").optString("spd");
            }
        }
    }

    public String getCode() {
        return code;
    }

    public String getTxt() {
        return txt;
    }

    public String getFl() {
        return fl;
    }

    public String getHum() {
        return hum;
    }

    public String getPcpn() {
        return pcpn;
    }

    public String getPres() {
        return pres;
    }

    public String getTmp() {
        return tmp;
    }

    public String getVis() {
        return vis;
    }

    public String getDeg() {
        return deg;
    }

    public String getDir() {
        return dir;
    }

    public String getSc() {
        return sc;
    }

    public String getSpd() {
        return spd;
    }
}
