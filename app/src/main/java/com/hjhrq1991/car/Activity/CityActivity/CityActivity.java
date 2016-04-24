package com.hjhrq1991.car.Activity.CityActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.SharePreferenceUtil;
import com.hjhrq1991.car.db.CityDB;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import java.util.List;

import butterknife.Bind;

/**
 * @author hjhrq1991 created at 1/13/16 21 48.
 * @Package com.hjhrq1991.car.Activity.CityActivity
 * @Description:
 */
public class CityActivity extends BaseActivity implements AdapterView.OnItemSelectedListener {

    @Bind(R.id.prov)
    Spinner mProv;
    @Bind(R.id.city)
    Spinner mCity;
    @Bind(R.id.area)
    Spinner mArea;
    private CityAdapter mProvAdapter;
    private CityAdapter mCityAdapter;
    private CityAdapter mAreaAdapter;

    private String aid;
    private String cid;
    private String pid;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_city_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        aid = SharePreferenceUtil.getCity(this);
        if (!TextUtils.isEmpty(aid) && aid.length() >= 7) {
            pid = aid.substring(0, 5);
            cid = aid.substring(0, 7);
        }

        init();

        mProv.setOnItemSelectedListener(this);
        mCity.setOnItemSelectedListener(this);
        mArea.setOnItemSelectedListener(this);
    }

    private void init() {
        mProvAdapter = new CityAdapter(this);
        mProv.setAdapter(mProvAdapter);
        mCityAdapter = new CityAdapter(this);
        mCity.setAdapter(mCityAdapter);
        mAreaAdapter = new CityAdapter(this);
        mArea.setAdapter(mAreaAdapter);

        getProv();
    }

    boolean hasSet;

    private void getProv() {
        List<CityDB> mProvList = CityDB.getProv();
//        CityDB cityDB = new CityDB("请选择", "0", "0", "0", 0);
//        mProvList.add(0, cityDB);
        mProvAdapter.replaceAll(mProvList);

        if (!hasSet) {
            if (!TextUtils.isEmpty(pid)) {
                for (int i = 0; i < mProvList.size(); i++) {
                    CityDB cityDB = mProvList.get(i);
                    if (cityDB.pid.equals(pid)) {
                        mProv.setSelection(i);
                    }
                }
            } else {
                mProv.setSelection(0);
            }
        }
    }

    private void getCity(String pid) {
        List<CityDB> mCityList = CityDB.getCity(pid);
        CityDB cityDB = new CityDB("请选择", "0", "0", "0", 0);
        mCityList.add(0, cityDB);
        mCityAdapter.replaceAll(mCityList);

        if (!hasSet) {
            if (!TextUtils.isEmpty(cid)) {
                for (int i = 0; i < mCityList.size(); i++) {
                    CityDB city = mCityList.get(i);
                    if (city.cid.equals(cid)) {
                        mCity.setSelection(i);
                    }
                }
            } else {
                mCity.setSelection(1);
            }
        }
    }

    private void getArea(String cid) {
        List<CityDB> mAreaList = CityDB.getArea(cid);
        CityDB cityDB = new CityDB("请选择", "0", "0", "0", 0);
        mAreaList.add(0, cityDB);
        mAreaAdapter.replaceAll(mAreaList);

        if (!hasSet) {
            if (!TextUtils.isEmpty(aid)) {
                for (int i = 0; i < mAreaList.size(); i++) {
                    CityDB city = mAreaList.get(i);
                    if (city.aid.equals(aid)) {
                        mArea.setSelection(i);
                    }
                }
            } else {
                mArea.setSelection(1);
            }
            hasSet = true;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()) {
            case R.id.prov:
                mCity.setSelection(0);
                getCity(mProvAdapter.getItem(position).pid);
                break;
            case R.id.city:
                mArea.setSelection(0);
                getArea(mCityAdapter.getItem(position).cid);
                break;
            case R.id.area:
                if (!mAreaAdapter.getItem(position).name.equals("请选择"))
                    SharePreferenceUtil.setCity(this, mAreaAdapter.getItem(position).aid);
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    public static void launch(Context context) {
        context.startActivity(new Intent(context, CityActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
