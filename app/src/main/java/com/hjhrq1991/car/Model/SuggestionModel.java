package com.hjhrq1991.car.Model;

import org.json.JSONObject;

/**
 * @author hjhrq1991 created at 1/28/16 10 31.
 * @Package com.hjhrq1991.car.Model
 * @Description:生活指数
 */
public class SuggestionModel {

    private String comfbrf;//普通描述
    private String comftxt;

    private String cwbrf;//洗车指数
    private String cwtxt;

    private String drsgbrf;//穿衣指数
    private String drsgtxt;

    private String flubrf;//感冒指数
    private String flutxt;

    private String sportbrf;//运动指数
    private String sporttxt;

    private String travbrf;//旅游指数
    private String travtxt;

    private String uvbrf;//紫外线指数
    private String uvtxt;

    public SuggestionModel() {
    }

    public SuggestionModel(JSONObject mJson) {
        if (mJson != null) {
            if (mJson.has("comf")) {
                this.comfbrf = mJson.optJSONObject("comf").optString("brf");
                this.comftxt = mJson.optJSONObject("comf").optString("txt");
            }
            if (mJson.has("cw")) {
                this.cwbrf = mJson.optJSONObject("cw").optString("brf");
                this.cwtxt = mJson.optJSONObject("cw").optString("txt");
            }
            if (mJson.has("drsg")) {
                this.drsgbrf = mJson.optJSONObject("drsg").optString("brf");
                this.drsgtxt = mJson.optJSONObject("drsg").optString("txt");
            }
            if (mJson.has("flu")) {
                this.flubrf = mJson.optJSONObject("flu").optString("brf");
                this.flutxt = mJson.optJSONObject("flu").optString("txt");
            }
            if (mJson.has("sport")) {
                this.sportbrf = mJson.optJSONObject("sport").optString("brf");
                this.sporttxt = mJson.optJSONObject("sport").optString("txt");
            }
            if (mJson.has("trav")) {
                this.travbrf = mJson.optJSONObject("trav").optString("brf");
                this.travtxt = mJson.optJSONObject("trav").optString("txt");
            }
            if (mJson.has("uv")) {
                this.uvbrf = mJson.optJSONObject("uv").optString("brf");
                this.uvtxt = mJson.optJSONObject("uv").optString("txt");
            }
        }
    }

    public String getComfbrf() {
        return comfbrf;
    }

    public String getComftxt() {
        return comftxt;
    }

    public String getCwbrf() {
        return cwbrf;
    }

    public String getCwtxt() {
        return cwtxt;
    }

    public String getDrsgbrf() {
        return drsgbrf;
    }

    public String getDrsgtxt() {
        return drsgtxt;
    }

    public String getFlubrf() {
        return flubrf;
    }

    public String getFlutxt() {
        return flutxt;
    }

    public String getSportbrf() {
        return sportbrf;
    }

    public String getSporttxt() {
        return sporttxt;
    }

    public String getTravbrf() {
        return travbrf;
    }

    public String getTravtxt() {
        return travtxt;
    }

    public String getUvbrf() {
        return uvbrf;
    }

    public String getUvtxt() {
        return uvtxt;
    }
}
