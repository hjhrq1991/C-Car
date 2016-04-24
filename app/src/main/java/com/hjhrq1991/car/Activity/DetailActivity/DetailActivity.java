package com.hjhrq1991.car.Activity.DetailActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.Event.DataChangeEvent;
import com.hjhrq1991.car.Model.SelectModel;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.DatePickerUtils;
import com.hjhrq1991.car.Util.SharePreferenceUtil;
import com.hjhrq1991.car.View.PopUpWindow.PopUpWin;
import com.hjhrq1991.car.db.ConsumeDB;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.hjhrq1991.tool.Util.TimeUtils;
import com.umeng.analytics.MobclickAgent;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author hjhrq1991 created at 1/10/16 10 19.
 * @Package com.hjhrq1991.car.Activity.DetailActivity
 * @Description:
 */
public class DetailActivity extends BaseActivity implements DatePickerDialog.OnDateSetListener
        , PopUpWin.OnPopWindowListener {

    @Bind(R.id.total_layout)
    TextInputLayout mTotalLayout;
    @Bind(R.id.price_layout)
    TextInputLayout mPriceLayout;
    @Bind(R.id.mileage_layout)
    TextInputLayout mMileageLayout;
    @Bind(R.id.type_layout)
    TextInputLayout mTypeLayout;
    @Bind(R.id.num_layout)
    TextInputLayout mNumLayout;
    @Bind(R.id.date_layout)
    TextInputLayout mDateLayout;
    @Bind(R.id.remark_layout)
    TextInputLayout mRemarkLayout;

    @Bind(R.id.delete)
    Button mDeleteBtn;

    private long id;

    private ConsumeDB consumeDB;

    private int type = 1;

    private String[] mType;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_detail_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isAnimAndSwipeBack = true;
        mType = getResources().getStringArray(R.array.type);
        init();
    }

    private void init() {
        id = getIntent().getLongExtra("id", -1);
        if (id == -1) {
            consumeDB = new ConsumeDB();
            consumeDB.type = type;
            consumeDB.name = mType[type - 1];
            mDateLayout.getEditText().setText(TimeUtils.getDate());
            consumeDB.date = TimeUtils.stringParseTimestamp(mDateLayout.getEditText().getText().toString().trim());
            mDeleteBtn.setVisibility(View.GONE);
        } else {
            consumeDB = ConsumeDB.getFromId(id);
            type = consumeDB.type;
            mDateLayout.getEditText().setText(TimeUtils.getDate(consumeDB.date));
            mTotalLayout.getEditText().setText(consumeDB.total + "");
            mPriceLayout.getEditText().setText(consumeDB.price + "");
            mMileageLayout.getEditText().setText(consumeDB.mileage + "");
            mRemarkLayout.getEditText().setText(consumeDB.remark);
            mNumLayout.getEditText().setText(consumeDB.oil + "");
            mDeleteBtn.setVisibility(View.VISIBLE);
        }
        mTypeLayout.getEditText().setText(consumeDB.name);
        initView();
        if (SharePreferenceUtil.getIsAutoTransitionGas(this)) {
            addTextWatch();
        }
    }

    private void addTextWatch() {
        mTotalLayout.getEditText().addTextChangedListener(new OnTextWatchImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (type == CustomConstant.type_gas) {
                    autoUpdata();
                }
            }
        });

        mPriceLayout.getEditText().addTextChangedListener(new OnTextWatchImpl() {
            @Override
            public void afterTextChanged(Editable s) {
                if (type == CustomConstant.type_gas) {
                    autoUpdata();
                }
            }
        });
    }

    /**
     * 自动更新加油量
     *
     * @TODO 根据设定是否自动更新
     */
    private void autoUpdata() {
        String totalString = mTotalLayout.getEditText().getText().toString().trim();
        String priceString = mPriceLayout.getEditText().getText().toString().trim();
        if (!TextUtils.isEmpty(totalString) && !TextUtils.isEmpty(priceString)) {
            float total = Float.valueOf(totalString);
            float price = Float.valueOf(priceString);

            float oil = new BigDecimal(total / price).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();

            mNumLayout.getEditText().setText(oil + "");
            consumeDB.oil = oil;
        }
    }

    private void initView() {
        if (consumeDB.type == CustomConstant.type_gas) {
            mMileageLayout.setVisibility(View.VISIBLE);
            mPriceLayout.setVisibility(View.VISIBLE);
            mNumLayout.setVisibility(View.VISIBLE);
        } else {
            mNumLayout.setVisibility(View.GONE);
            mMileageLayout.setVisibility(View.GONE);
            mPriceLayout.setVisibility(View.GONE);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @OnClick({R.id.view_type, R.id.view_date, R.id.delete})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.view_type:
                showWindow(mTypeLayout, mType, v.getId());
                break;
            case R.id.view_date:
                showDatePicker();
                break;
            case R.id.delete:
                delete();
                break;
        }
    }

    private void delete() {
        Snackbar.make(mTotalLayout, getString(R.string.delete_or_not), Snackbar.LENGTH_SHORT)
                .setAction(getString(R.string.ensure_delete), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ConsumeDB.delecteFromId(id);
                        EventBus.getDefault().post(new DataChangeEvent());
                        finish();
                    }
                }).show();
    }

    private void showWindow(View v, String[] str, int type) {
        List<SelectModel> list = new ArrayList<>();
        for (int i = 0; i < str.length; i++) {
            SelectModel model = new SelectModel(str[i], i + 1);
            list.add(model);
        }
        showPopUpWindow(v, list, type);
    }

    private void showPopUpWindow(View v, List<SelectModel> list, int type) {
        // 创建PopupWindow对象
        PopUpWin pop = new PopUpWin(this, list, type, v.getRight() - v.getLeft(), this);
        // 需要设置一下此参数，点击外边可消失
        pop.setBackgroundDrawable(new BitmapDrawable());
        //设置点击窗口外边窗口消失
        pop.setOutsideTouchable(true);
        // 设置此参数获得焦点，否则无法点击
        pop.setFocusable(true);
        pop.showAsDropDown(v, 0, 0);
    }

    @Override
    public void onItemClick(int type, SelectModel db) {
        mTypeLayout.getEditText().setText(db.getName());
        this.type = db.getIndex();
        consumeDB.type = db.getIndex();
        consumeDB.name = db.getName();
        initView();
    }

    private void showDatePicker() {
        String date = mDateLayout.getEditText().getText().toString().trim();
        DatePickerUtils.initTime(getFragmentManager(), this, date, 1);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int tag) {
        String date = TimeUtils.toStringData(year, monthOfYear, dayOfMonth);
        Calendar now = Calendar.getInstance();
        String nowString = TimeUtils.toStringData(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
        long diff = TimeUtils.compareDate(nowString, date);
        if (diff < 0) {
            Snackbar.make(mTotalLayout, getString(R.string.date_error), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_reset), new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            showDatePicker();
                        }
                    }).show();
            return;
        }
        mDateLayout.getEditText().setText(date);
        consumeDB.date = TimeUtils.stringParseTimestamp(date);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {
            save();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * 保存数据库前先校验，记账类型、日期、金额不能为空，当type为 {@link CustomConstant} type_gas时里程也不能为空
     */
    private void save() {
        String total = mTotalLayout.getEditText().getText().toString().trim();
        String remark = mRemarkLayout.getEditText().getText().toString().trim();

        if (TextUtils.isEmpty(consumeDB.name)) {
            Snackbar.make(mTotalLayout, getString(R.string.snackbar_type), Snackbar.LENGTH_SHORT)
                    .setAction(getString(R.string.dismiss), null).show();
            return;
        }

        if (TextUtils.isEmpty(total)) {
            mTotalLayout.setError(getString(R.string.total_empty));
            return;
        }
        mTotalLayout.setErrorEnabled(false);

        if (type == CustomConstant.type_gas) {
            String mileage = mMileageLayout.getEditText().getText().toString().trim();
            String price = mPriceLayout.getEditText().getText().toString().trim();
            String oil = mNumLayout.getEditText().getText().toString().trim();

            if (TextUtils.isEmpty(mileage)) {
                mMileageLayout.setError(getString(R.string.mileage_empty));
                return;
            }
            mMileageLayout.setErrorEnabled(false);

            if (TextUtils.isEmpty(oil)) {
                mNumLayout.setError(getString(R.string.oil_empty));
                return;
            }
            mNumLayout.setErrorEnabled(false);

            if (TextUtils.isEmpty(price)) {
                mPriceLayout.setError(getString(R.string.price_empty));
                return;
            }
            mPriceLayout.setErrorEnabled(false);

            consumeDB.mileage = Float.valueOf(mileage);
            consumeDB.oil = Float.valueOf(oil);
            consumeDB.price = Float.valueOf(price);

        }
        consumeDB.total = Float.valueOf(total);
        if (!TextUtils.isEmpty(remark)) {
            consumeDB.remark = remark;
        }
        consumeDB.save();
        EventBus.getDefault().post(new DataChangeEvent());
        finish();
    }

    public static void launch(Context context, long id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("id", id);
        context.startActivity(intent);
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
