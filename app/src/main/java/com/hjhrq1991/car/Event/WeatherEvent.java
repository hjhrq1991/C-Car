package com.hjhrq1991.car.Event;

import android.text.TextUtils;

import com.hjhrq1991.car.Model.BasicModel;
import com.hjhrq1991.car.Model.NowWeatherModel;
import com.hjhrq1991.car.Model.SuggestionModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;

/**
 * @author hjhrq1991 created at 1/28/16 11 13.
 * @Package com.hjhrq1991.car.Event
 * @Description:
 */
public class WeatherEvent {

    private String errorCode;
    private boolean isSuccess;

    private BasicModel basic;

    private NowWeatherModel now;

    private SuggestionModel suggestion;

    public WeatherEvent() {
    }

    public WeatherEvent(String json) {
        if (!TextUtils.isEmpty(json)) {
            try {
                JSONObject jsonObject = new JSONObject(json);

                Iterator<String> keys = jsonObject.keys();
                String key = keys.next();
                JSONArray jsonArray = jsonObject.optJSONArray(key);
                if (jsonArray != null && jsonArray.length() > 0) {
                    JSONObject mJson = jsonArray.optJSONObject(0);
                    this.errorCode = mJson.optString("status");
                    if (errorCode.equals("ok")) {
                        this.isSuccess = true;
                        this.basic = new BasicModel(mJson.optJSONObject("basic"));
                        this.now = new NowWeatherModel(mJson.optJSONObject("now"));
                        this.suggestion = new SuggestionModel(mJson.optJSONObject("suggestion"));
                    } else {
                        this.isSuccess = false;
                    }
                }

            } catch (JSONException e) {
                this.isSuccess = false;
            }
        } else {
            this.isSuccess = false;
        }
    }

    public String getErrorCode() {
        return errorCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public BasicModel getBasic() {
        return basic;
    }

    public NowWeatherModel getNow() {
        return now;
    }

    public SuggestionModel getSuggestion() {
        return suggestion;
    }
}
