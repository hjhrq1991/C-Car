//package com.hjhrq1991.car.Activity.MainActivity;
//
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.drawable.BitmapDrawable;
//import android.os.Bundle;
//import android.os.Handler;
//import android.support.design.widget.FloatingActionButton;
//import android.support.v7.widget.Toolbar;
//import android.text.SpannableString;
//import android.text.Spanned;
//import android.text.style.UnderlineSpan;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AbsListView;
//import android.widget.AdapterView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.hjhrq1991.car.Activity.CalculateActivity.CalculateActivity;
//import com.hjhrq1991.car.Activity.CityActivity.CityActivity;
//import com.hjhrq1991.car.Activity.DetailActivity.DetailActivity;
//import com.hjhrq1991.car.Constant.CustomConstant;
//import com.hjhrq1991.car.Controller.Controller;
//import com.hjhrq1991.car.Event.DataChangeEvent;
//import com.hjhrq1991.car.Model.SelectModel;
//import com.hjhrq1991.car.R;
//import com.hjhrq1991.car.Util.CityUtil;
//import com.hjhrq1991.car.View.PopUpWindow.PopUpWin;
//import com.hjhrq1991.car.db.CityDB;
//import com.hjhrq1991.car.db.ConsumeDB;
//import com.hjhrq1991.tool.Base.BaseActivity;
//import com.hjhrq1991.tool.Widget.PullToRefreshView;
//import com.qoocc.pull_to_refresh_expandablelist.CommonFooterView;
//import com.qoocc.pull_to_refresh_expandablelist.PullToRefreshListView;
//
//import org.greenrobot.eventbus.EventBus;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import butterknife.Bind;
//import butterknife.OnClick;
//
//public class MainActivity extends BaseActivity implements AdapterView.OnItemClickListener,
//        PullToRefreshView.OnRefreshListener, AbsListView.OnScrollListener, OnloadFinishListener, PopUpWin.OnPopWindowListener {
//
//    @Bind(R.id.pull_to_refresh)
//    PullToRefreshView mRefreshView;
//
//    @Bind(R.id.list_view)
//    PullToRefreshListView mListView;
//
//    @Bind(R.id.toolbar)
//    Toolbar toolbar;
//
//    @Bind(R.id.fab)
//    FloatingActionButton fab;
//
////    @Bind(R.id.empty_tv)
////    TextView mEmptyView;
//
//    @Bind(R.id.title)
//    TextView mTitleTv;
//
//    ListAdapter mAdapter;
//
//    private int type;
//    private int pageSize = 20;
//
//    private long date = -1;
//
//    private boolean isLoadMore = false;
//    private boolean mHasNextPage = false;
//    private IRequest request;
//
//    private String[] mTitle;
//
//    private Controller mController;
//
//    @Override
//    public int getLayoutResource() {
//        return R.layout.activity_main;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setSupportActionBar(toolbar);
//
//        if (!EventBus.getDefault().isRegistered(this))
//            EventBus.getDefault().register(this);
//        mTitle = getResources().getStringArray(R.array.title_text);
//
//        mAdapter = new ListAdapter(this);
//        mListView.setAdapter(mAdapter);
//        mRefreshView.setOnRefreshListener(this);
//        mListView.setOnScrollListener(this);
//        mListView.setState(CommonFooterView.State.HIDE);
//        mListView.setOnItemClickListener(this);
//        request = new RequestImpl();
//        type = CustomConstant.type_all;
//
//        mController = new Controller(this);
////        mController.getOilPrice("广东");
////        mController.getWeather("电白");
//        setTitle();
//        loadData();
//
//        List<CityDB> mCityList = CityDB.getAll();
//        if (mCityList == null || mCityList.size() <= 0) {
////            System.out.println("hrq------获取列表");
//            CityUtil.getList(this);
//        } else {
////            省列表34-----市422-----区2574
////            System.out.println("hrq------不获取列表" + mCityList.size());
//        }
//    }
//
//    @OnClick(R.id.fab)
//    public void onClick() {
//        DetailActivity.launch(this, -1);
//    }
//
//    private void loadData() {
//        request.loadItem(this, type, pageSize, date);
//    }
//
//    private void refresh() {
//        date = -1;
//        isLoadMore = false;
//        loadData();
//    }
//
//    public void onEventMainThread(DataChangeEvent event) {
//        refresh();
//    }
//
//    @Override
//    public void loadFinished(List<ConsumeDB> models) {
//        mRefreshView.setRefreshing(false);
//        if (!isLoadMore) {
//            if (models != null && models.size() > 0) {
//                mRefreshView.setVisibility(View.VISIBLE);
////                mEmptyView.setVisibility(View.GONE);
//                if (models.size() < pageSize) {
//                    mHasNextPage = false;
//                    mListView.setState(CommonFooterView.State.HIDE);
//                } else {
//                    date = models.get(models.size() - 1).date;
//                    mHasNextPage = true;
//                    mListView.setState(CommonFooterView.State.RESET);
//                }
//                models.add(0, new ConsumeDB(CustomConstant.type_title));
//                mAdapter.replaceAll(models);
//            } else {
//                mRefreshView.setVisibility(View.GONE);
////                mEmptyView.setVisibility(View.VISIBLE);
//            }
//        } else {
//            if (models != null && models.size() > 0) {
//                mRefreshView.setVisibility(View.VISIBLE);
////                mEmptyView.setVisibility(View.GONE);
//                mAdapter.addAll(models);
//                if (models.size() < pageSize) {
//                    mHasNextPage = false;
//                    mListView.setState(CommonFooterView.State.HIDE);
//                } else {
//                    date = models.get(models.size() - 1).date;
//                    mHasNextPage = true;
//                    mListView.setState(CommonFooterView.State.RESET);
//                }
//            } else {
//                mListView.setState(CommonFooterView.State.HIDE);
//            }
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_calculate) {
//            CalculateActivity.launch(this);
//            return true;
////        } else if (id == R.id.action_form) {
////            CityDB.deletAll();
////            return true;
//        } else if (id == R.id.action_settings) {
//            CityActivity.launch(this);
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (position-- <= 0) return;
//        ConsumeDB consumeDB = mAdapter.getItem(position);
//
//        DetailActivity.launch(this, consumeDB.getId());
//    }
//
//    @Override
//    public void onRefresh() {
//        refresh();
//    }
//
//    @Override
//    public void onScrollStateChanged(AbsListView view, int scrollState) {
//        if (view.getFirstVisiblePosition() == 0) {
//            mRefreshView.setTop(true);
//        } else {
//            mRefreshView.setTop(false);
//        }
//        if (view.getLastVisiblePosition() == (view.getCount() - 1)
//                && !mHasNextPage) {
//            mListView.setState(CommonFooterView.State.HIDE);
//            return;
//        }
//        if (view.getLastVisiblePosition() == view.getCount() - 1
//                && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
//            if (mHasNextPage) {
//                isLoadMore = true;
//                mListView.setState(CommonFooterView.State.LOADING);
//                loadData();
//            } else {
//                mListView.setState(CommonFooterView.State.HIDE);
//            }
//        }
//    }
//
//    @Override
//    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//
//    }
//
//    @OnClick(R.id.title)
//    public void onTitleClick(View v) {
//        showWindow(mTitleTv, mTitle, v.getId());
//    }
//
//    private void showWindow(View v, String[] str, int type) {
//        List<SelectModel> list = new ArrayList<>();
//        for (int i = 0; i < str.length; i++) {
//            SelectModel model = new SelectModel(str[i], i);
//            list.add(model);
//        }
//        showPopUpWindow(v, list, type);
//    }
//
//    private void showPopUpWindow(View v, List<SelectModel> list, int type) {
//        int width = v.getRight() - v.getLeft();
//        // 创建PopupWindow对象
//        PopUpWin pop = new PopUpWin(this, list, type, width * 2, this);
//        // 需要设置一下此参数，点击外边可消失
//        pop.setBackgroundDrawable(new BitmapDrawable());
//        //设置点击窗口外边窗口消失
//        pop.setOutsideTouchable(true);
//        // 设置此参数获得焦点，否则无法点击
//        pop.setFocusable(true);
//        pop.showAsDropDown(v, -(width / 2), 0);
//    }
//
//    @Override
//    public void onItemClick(int type, SelectModel db) {
//        this.type = db.getIndex();
//        if (db.getIndex() == CustomConstant.type_gas) {
//            mAdapter.setGas(true);
//        } else {
//            mAdapter.setGas(false);
//        }
//        setTitle();
//        refresh();
//    }
//
//    private void setTitle() {
////        String string = String.format(getString(R.string.title), mTitle[type]);
//        String string = mTitle[type];
//        int startIndex = string.indexOf(String.valueOf(mTitle[type]));
//        int endIndex = startIndex + String.valueOf(mTitle[type]).length();
//        SpannableString spannableString = new SpannableString(string);
//        spannableString.setSpan(new UnderlineSpan(), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//
//        mTitleTv.setText(spannableString);
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        EventBus.getDefault().unregister(this);
//    }
//
//    private boolean scrollVisible = false;
//    private Handler mScrollHandler = new Handler();
//    private Runnable callBack = new Runnable() {
//        public void run() {
//            scrollVisible = false;
//        }
//    };
//
//
//    @Override
//    public void onBackPressed() {
//        if (!toastVisible) {
//            Toast.makeText(this, getString(R.string.exit_app), Toast.LENGTH_SHORT).show();
//            toastVisible = true;
//            mExitHandler.postDelayed(mCallBack, 2000);
//            return;
//        }
//        mExitHandler.removeCallbacks(mCallBack);
//        super.onBackPressed();
//        finish();
//        System.exit(0);
//        // 杀死当前进程，否则会一直增加内存
//        android.os.Process.killProcess(android.os.Process.myPid());
//    }
//
//    /**
//     * 返回键监听
//     */
//    private boolean toastVisible = false;
//    private Handler mExitHandler = new Handler();
//    private Runnable mCallBack = new Runnable() {
//        public void run() {
//            toastVisible = false;
//        }
//    };
//
//
//    public static void launch(Context context) {
//        context.startActivity(new Intent(context, MainActivity.class));
//    }
//}
