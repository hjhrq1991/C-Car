package com.hjhrq1991.car.Event;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author hjhrq1991 created at 1/12/16 21 46.
 * @Package com.hjhrq1991.car.Event
 * @Description:
 */
public class OilPriceEvent {

    private boolean isSuccess;
    private int errorCode;
    private String errorMsg;
    /**
     * 城市
     */
    private String prov;
    /**
     * 柴油
     */
    private float p0;
    /**
     * 90号(国标89)汽油
     */
    private float p90;
    /**
     * 93号(国标92)汽油
     */
    private float p93;
    /**
     * 97号(国标95)汽油
     */
    private float p97;

    public OilPriceEvent(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.has("showapi_res_code")) {
                    if (jsonObject.has("showapi_res_body")) {
                        this.isSuccess = true;
                        JSONArray jsonArray = jsonObject.optJSONObject("showapi_res_body").optJSONArray("list");
                        if (jsonArray != null && jsonArray.length() > 0) {
                            JSONObject mJson = jsonArray.optJSONObject(0);
                            this.prov = mJson.optString("prov");
                            String p0 = mJson.optString("p0");
                            String p90 = mJson.optString("p90");
                            String p93 = mJson.optString("p93");
                            String p97 = mJson.optString("p97");
                            this.p0 = Float.valueOf(p0);
                            if (prov.equals("北京") || prov.equals("上海")) {
                                this.p90 = Float.valueOf(p90.substring(0, p90.indexOf("(")));
                                this.p93 = Float.valueOf(p93.substring(0, p93.indexOf("(")));
                                this.p97 = Float.valueOf(p97.substring(0, p97.indexOf("(")));
                            } else {
                                this.p90 = Float.valueOf(p90);
                                this.p93 = Float.valueOf(p93);
                                this.p97 = Float.valueOf(p97);
                            }
                        }
                    } else {
                        this.isSuccess = false;
                    }
                } else {
                    this.isSuccess = false;
                    this.errorCode = jsonObject.optInt("errNum");
                    this.errorMsg = jsonObject.optString("errMsg");
                }

            } catch (JSONException e) {
                this.isSuccess = false;
                this.errorMsg = "服务器异常.";
            }
        } else {
            this.isSuccess = false;
            this.errorMsg = "服务器异常.";
        }
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public String getProv() {
        return prov;
    }

    public float getP0() {
        return p0;
    }

    public float getP90() {
        return p90;
    }

    public float getP93() {
        return p93;
    }

    public float getP97() {
        return p97;
    }
}
