package com.hjhrq1991.car.Activity.MainActivityV3;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.UnderlineSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.hjhrq1991.car.Activity.CalculateActivity.CalculateActivity;
import com.hjhrq1991.car.Activity.DetailActivity.DetailActivity;
import com.hjhrq1991.car.Activity.FormActivity.FormActivity;
import com.hjhrq1991.car.Activity.MainActivity.IRequest;
import com.hjhrq1991.car.Activity.MainActivity.OnloadFinishListener;
import com.hjhrq1991.car.Activity.MainActivity.RequestImpl;
import com.hjhrq1991.car.Activity.SettingsActivity.SettingsActivity;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.Controller.Controller;
import com.hjhrq1991.car.Event.DataChangeEvent;
import com.hjhrq1991.car.Event.GasSettingChangedEvent;
import com.hjhrq1991.car.Event.OilPriceEvent;
import com.hjhrq1991.car.Event.WeatherEvent;
import com.hjhrq1991.car.Model.BasicModel;
import com.hjhrq1991.car.Model.NowWeatherModel;
import com.hjhrq1991.car.Model.SelectModel;
import com.hjhrq1991.car.Model.SuggestionModel;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.CityUtil;
import com.hjhrq1991.car.Util.JpushUtil;
import com.hjhrq1991.car.Util.LocationUtil;
import com.hjhrq1991.car.Util.SharePreferenceUtil;
import com.hjhrq1991.car.View.PopUpWindow.PopUpWin;
import com.hjhrq1991.car.db.CacheDB;
import com.hjhrq1991.car.db.CityDB;
import com.hjhrq1991.car.db.ConsumeDB;
import com.hjhrq1991.commonview.View.CommonFooterView;
import com.hjhrq1991.commonview.widget.CommonFooterListView;
import com.hjhrq1991.commonview.widget.StrokeTextView;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.hjhrq1991.tool.Util.TimeUtils;
import com.umeng.analytics.MobclickAgent;
import com.umeng.update.UmengUpdateAgent;
import com.zuck.swipe.hitblockrefresh.view.FunGameRefreshView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

public class MainActivityV3 extends BaseActivity implements AdapterView.OnItemClickListener, OnloadFinishListener, PopUpWin.OnPopWindowListener,
        LocationUtil.OnLocationListener, FunGameRefreshView.FunGameRefreshListener, AbsListView.OnScrollListener, CommonFooterView.OnFooterClickListener {

    @Bind(R.id.refresh_view)
    FunGameRefreshView mRefreshView;

    @Bind(R.id.list_view)
    CommonFooterListView mListView;

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.title)
    TextView mTitleTv;

    @Bind(R.id.fab)
    FloatingActionButton mFABtn;

    TextView mTmpTv;
    ImageView mWeather;
    TextView mLocationTv;
    StrokeTextView mOilTv;
    TextView mHumTv;
    TextView mSuggestTv;
    ListAdapter mAdapter;

    private int type;
    private int pageSize = 10;

    private long date = -1;

    private boolean isGasSettingChanged = false;
    private boolean isLoadMore = false;
    private boolean mHasNextPage = false;
    private boolean isFirst = true;
    private IRequest request;

    private String[] mTitle;

    private String aid = "";

    private Controller mController;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_main_v3;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);

        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
        mTitle = getResources().getStringArray(R.array.title_text);

        View mHeader = View.inflate(this, R.layout.main_listview_header, null);
        mTmpTv = (TextView) mHeader.findViewById(R.id.tmp_tv);
        mWeather = (ImageView) mHeader.findViewById(R.id.weather);
        mLocationTv = (TextView) mHeader.findViewById(R.id.location_tv);
        mOilTv = (StrokeTextView) mHeader.findViewById(R.id.oil);
        mHumTv = (TextView) mHeader.findViewById(R.id.hum_tv);
        mSuggestTv = (TextView) mHeader.findViewById(R.id.suggest_tv);

        mListView.addHeaderView(mHeader);
        mListView.setState(CommonFooterView.State.HIDE);

        mListView.setOnFooterClickListener(this);

        mListView.setOnScrollListener(this);
        mListView.setOnItemClickListener(this);

        mAdapter = new ListAdapter(this);
        mListView.setAdapter(mAdapter);

        request = new RequestImpl();
        type = CustomConstant.type_all;
        mController = new Controller(this);

        mLocationUtil = LocationUtil.getIntance(this, this);
        mLocationUtil.startLocation(null);
        //反射获取id
//        bg_id = ctx.getResources().getIdentifier("icon_wea_" + event.getWeatherPictureType(), "drawable", ctx.getPackageName());

        mRefreshView.setOnRefreshListener(this);

        setTitle();
        initView();
        loadData();
        initData();

        alertDialog();

        UmengUpdateAgent.update(this);

        JpushUtil.setJPush(getApplicationContext());
    }

    @Override
    public void onRefreshing() {
        refresh();
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (view.getLastVisiblePosition() == (view.getCount() - 1)
                && !mHasNextPage) {
            mListView.setState(CommonFooterView.State.HIDE);
            return;
        }
        if (view.getLastVisiblePosition() == view.getCount() - 1
                && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
            loadMore();
        }
    }

    @Override
    public void onNotNetWorkClick(View view) {

    }

    @Override
    public void onLoadMoreClick(View view) {
        loadMore();
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    private void alertDialog() {
        if (!SharePreferenceUtil.getEvaluate(this)) {

            long mLoadTime = SharePreferenceUtil.getFirstLoadTime(this);
            if (mLoadTime <= 0l) {
                SharePreferenceUtil.setFirstLoadTime(this, TimeUtils.getcurrentTimeMillis());
            } else {
                //首次启动后超过3天时间就弹出评价界面
                if ((TimeUtils.getcurrentTimeMillis() - mLoadTime) >= (3 * 24 * 60 * 60)) {
                    new AlertDialog.Builder(this)
                            .setTitle("感谢您的使用！")
                            .setMessage("小C写程序不容易，给来个留言评分呗，我会努力做得更好！")
                            .setPositiveButton("五星好评", (dialog, which) -> {
                                SharePreferenceUtil.setEvaluate(getApplicationContext(), true);
                                try {
                                    Uri uri = Uri.parse("market://details?id=" + getPackageName());
                                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    Toast.makeText(getApplicationContext(), "未检测到应用市场！", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("暂不评论", (dialog, which) -> {
                                SharePreferenceUtil.setEvaluate(getApplicationContext(), true);
                            })
                            .show();
                }
            }
        }
    }

    LocationUtil mLocationUtil;

    @Override
    public void locationSuccess(AMapLocation aMapLocation) {
        System.out.println("hrq-----" + aMapLocation.getCity());
        mLocationUtil.stopLocation();
    }

    private void initView() {
        String string = "天气：--\n温度：--℃";

        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new RelativeSizeSpan(1.33f), string.indexOf("：") + 1, string.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        mTmpTv.setText(spannableString);
    }

    private void initData() {
        List<CityDB> mCityList = CityDB.getAll();
        if (mCityList == null || mCityList.size() <= 0) {
            CityUtil.getList(this);
        }

        aid = SharePreferenceUtil.getCity(this);
        if (!TextUtils.isEmpty(aid) && aid.length() == 9) {
            String pid = aid.substring(0, 5);
            String cid = aid.substring(0, 7);

            CityDB prov = CityDB.getProvbyPid(pid);
//            CityDB city = CityDB.getCityByCid(cid);
            CityDB area = CityDB.getAreaByAid(aid);

            if (area != null) {
                mLocationTv.setText("当前城市：" + area.name);
                mController.getWeather(area.name);
            } else {
                mLocationTv.setText("当前城市：北京");
                mController.getWeather("北京");
            }
            if (prov != null) {
                mController.getOilPrice(prov.name);
            } else {
                mController.getOilPrice("北京");
            }
        } else {
            mLocationTv.setText("当前城市：北京");
            mController.getWeather("北京");
            mController.getOilPrice("北京");
        }
    }

    @OnClick(R.id.fab)
    public void onClick() {
        DetailActivity.launch(this, -1);
    }

    private void loadData() {
        request.loadItem(this, type, pageSize, date);

        CacheDB oilDb = CacheDB.getCache(CustomConstant.business_oil);
        if (oilDb != null) {
            getOilPriceSuccess(new OilPriceEvent(oilDb.content));
        }

        CacheDB weatherDb = CacheDB.getCache(CustomConstant.business_weather);
        if (weatherDb != null) {
            getWeatherSuccess(new WeatherEvent(weatherDb.content));
        }
    }

    private void refresh() {
        date = -1;
        isLoadMore = false;
        loadData();
    }

    private void loadMore() {
        if (mHasNextPage) {
            isLoadMore = true;
            mListView.setState(CommonFooterView.State.LOADING);
            request.loadItem(this, type, pageSize, date);
        } else {
            mListView.setState(CommonFooterView.State.HIDE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataChangeEvent(DataChangeEvent event) {
        refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onWeatherEvent(WeatherEvent event) {
        getWeatherSuccess(event);
    }

    private void getWeatherSuccess(WeatherEvent event) {
        if (event.isSuccess()) {
            BasicModel basicModel = event.getBasic();
            NowWeatherModel nowWeatherModel = event.getNow();
            SuggestionModel suggestionModel = event.getSuggestion();

            if (nowWeatherModel != null) {
                String string = "天气：" + nowWeatherModel.getTxt() + "\n温度：" + nowWeatherModel.getTmp() + "℃";

                SpannableString spannableString = new SpannableString(string);
                spannableString.setSpan(new RelativeSizeSpan(1.33f), string.indexOf("：") + 1, string.indexOf("\n"), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                mTmpTv.setText(spannableString);

                int resId = getResources().getIdentifier("w_" + nowWeatherModel.getCode(), "mipmap", getPackageName());
                if (resId <= 0) {
                    resId = getResources().getIdentifier("w_999", "drawable", getPackageName());
                }
                mWeather.setImageResource(resId);

                mHumTv.setText("相对湿度：" + nowWeatherModel.getHum() + "%");
            }

            if (suggestionModel != null) {
                mSuggestTv.setText("洗车指数：" + suggestionModel.getCwbrf());
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOilPriceEvent(OilPriceEvent event) {
        getOilPriceSuccess(event);
    }

    private void getOilPriceSuccess(OilPriceEvent event) {
        if (event.isSuccess()) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            String secondValue = sharedPreferences.getString(CustomConstant.SETTINGS_SECOND_GAS, CustomConstant.second_gas_97);
            boolean isUseGB = sharedPreferences.getBoolean(CustomConstant.SETTINGS_USE_GB, true);

            StringBuffer string = new StringBuffer();
            string.append(String.format(isUseGB ? getString(R.string.gas_92) : getString(R.string.gas_93), event.getP93()));

            switch (secondValue) {
                case CustomConstant.second_gas_0:
                    string.append("\n" + String.format(getString(R.string.gas_0), event.getP0()));
                    break;
                case CustomConstant.second_gas_90:
                    string.append("\n" + String.format(isUseGB ? getString(R.string.gas_89) : getString(R.string.gas_90), event.getP90()));
                    break;
                case CustomConstant.second_gas_97:
                    string.append("\n" + String.format(isUseGB ? getString(R.string.gas_95) : getString(R.string.gas_97), event.getP97()));
                    break;
            }
            mOilTv.setText(string);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onGasChangeEvent(GasSettingChangedEvent event) {
        isGasSettingChanged = true;
    }

    @Override
    public void loadFinished(List<ConsumeDB> models) {
        if (models != null && models.size() > 0) {
            mListView.setVisibility(View.VISIBLE);
            if (models.size() < pageSize) {
                mHasNextPage = false;
                mListView.setState(CommonFooterView.State.HIDE);
            } else {
                mHasNextPage = true;
                mListView.setState(CommonFooterView.State.RESET);
            }
            date = models.get(models.size() - 1).date;
            if (!isLoadMore) {
                models.add(0, new ConsumeDB(CustomConstant.type_title));
                mAdapter.replaceAll(models);
            } else {
                mAdapter.addAll(models);
            }
        } else {
            if (!isLoadMore) {
                mListView.setVisibility(View.GONE);
            }
            mListView.setState(CommonFooterView.State.HIDE);
        }
        if (!isFirst) {
            //延时3秒执行回滚操作模拟正式网络请求
            mRefreshView.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mRefreshView.finishRefreshing();
                }
            }, 3 * 1000);
        } else {
            isFirst = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_v3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        switch (id) {
            case R.id.action_calculate:
                CalculateActivity.launch(this);
                break;
            case R.id.action_form:
                FormActivity.launch(this);
                break;
            case R.id.action_settings:
                SettingsActivity.launch(this);
                break;
//            case R.id.action_location:
////                CalculateActivity.launch(this);
//                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position-- <= 0) return;
        ConsumeDB consumeDB = mAdapter.getItem(position);

        DetailActivity.launch(this, consumeDB.getId());
    }

    @OnClick(R.id.title)
    public void onTitleClick(View v) {
        showWindow(mTitleTv, mTitle, v.getId());
    }

    private void showWindow(View v, String[] str, int type) {
        List<SelectModel> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            SelectModel model = new SelectModel(str[i], i);
            list.add(model);
        }
        showPopUpWindow(v, list, type);
    }

    private void showPopUpWindow(View v, List<SelectModel> list, int type) {
        int width = v.getRight() - v.getLeft();
        // 创建PopupWindow对象
        PopUpWin pop = new PopUpWin(this, list, type, width * 2, this);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.showAsDropDown(v, -(width / 2), 0);
    }

    @Override
    public void onItemClick(int type, SelectModel db) {
        this.type = db.getIndex();
        if (db.getIndex() == CustomConstant.type_gas) {
            mAdapter.setGas(true);
        } else {
            mAdapter.setGas(false);
        }
        setTitle();
//        mListView.smoothScrollToPosition(0);
        refresh();
    }

    private void setTitle() {
        String string = mTitle[type];
        int startIndex = string.indexOf(String.valueOf(mTitle[type]));
        int endIndex = startIndex + String.valueOf(mTitle[type]).length();
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        mTitleTv.setText(spannableString);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onBackPressed() {
        if (!toastVisible) {
            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
            toastVisible = true;
            mExitHandler.postDelayed(mCallBack, 2000);
            return;
        }
        mExitHandler.removeCallbacks(mCallBack);
        super.onBackPressed();
        finish();
        System.exit(0);
        // 杀死当前进程，否则会一直增加内存
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    /**
     * 返回键监听
     */
    private boolean toastVisible = false;
    private Handler mExitHandler = new Handler();
    private Runnable mCallBack = new Runnable() {
        public void run() {
            toastVisible = false;
        }
    };


    public static void launch(Context context) {
        context.startActivity(new Intent(context, MainActivityV3.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        String newAid = SharePreferenceUtil.getCity(this);
        if (!TextUtils.isEmpty(newAid) && !newAid.equals(aid)) {
            initData();
        }

        if (isGasSettingChanged) {
            CacheDB oilDb = CacheDB.getCache(CustomConstant.business_oil);
            if (oilDb != null) {
                getOilPriceSuccess(new OilPriceEvent(oilDb.content));
            }
            isGasSettingChanged = false;
        }
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
