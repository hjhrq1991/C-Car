package com.hjhrq1991.car.Activity.TimeFormActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.hjhrq1991.car.Model.SelectModel;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.DatePickerUtils;
import com.hjhrq1991.car.View.PopUpWindow.PopUpWin;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.hjhrq1991.tool.Util.TimeUtils;
import com.umeng.analytics.MobclickAgent;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * @author hjhrq1991 created at 2/18/16 10 47.
 * @Package com.hjhrq1991.car.Activity.FormActivity
 * @Description:
 */
public class TimeFormActivity extends BaseActivity implements OnloadFinishList, DatePickerDialog.OnDateSetListener
        , PopUpWin.OnPopWindowListener {

    @Bind(R.id.form_type)
    EditText mFormType;

    @Bind(R.id.chart_bar)
    BarChart mBarChart;

    @Bind(R.id.date_start)
    EditText mStartEdit;

    @Bind(R.id.date_end)
    EditText mEndEdit;

    @Bind(R.id.empty_tv)
    TextView mEmptyView;

    private IRequest request;

    private long startDate = 0;

    private long endDate = 0;

    private int type = 0;

    String[] mTypeList;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_month_form_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mTypeList = getResources().getStringArray(R.array.title_text);

        request = new RequestImpl();
        request.loadItem(this, startDate, endDate, type);
    }

    @OnClick({R.id.view_type, R.id.view_start, R.id.view_end})
    public void onClick(View v) {
        String date;
        switch (v.getId()) {
            case R.id.view_type:
                showWindow(mFormType, mTypeList, v.getId());
                break;
            case R.id.view_start:
                date = mStartEdit.getText().toString().trim();
                if (date.equals("请选择")) {
                    date = "";
                }
                showDatePicker(date, v.getId());

                break;
            case R.id.view_end:
                date = mEndEdit.getText().toString().trim();
                if (date.equals("请选择")) {
                    date = "";
                }
                showDatePicker(date, v.getId());
                break;
        }
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
        type = db.getIndex();
        mFormType.setText(db.getName());
        request.loadItem(this, startDate, endDate, type);
    }

    private void showDatePicker(String date, int tag) {
        DatePickerUtils.initTime(getFragmentManager(), this, date, tag);
    }

    @Override
    public void loadFinished(List<BarEntry> mList, List<String> mKeys) {
        if (mList != null && mList.size() > 0) {

            mBarChart.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            setBarData(mList, mKeys);
        } else {
            mBarChart.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 柱状图
     *
     * @param mList
     */
    private void setBarData(List<BarEntry> mList, List<String> mKeys) {
//        mBarChart.setOnChartValueSelectedListener(this);

        mBarChart.setDrawBarShadow(false);
        mBarChart.setDrawValueAboveBar(true);

        mBarChart.setDescription("");
        // if more than 60 entries are displayed in the chart, no values will be drawn
        mBarChart.setMaxVisibleValueCount(60);

        // scaling can now only be done on x- and y-axis separately
        mBarChart.setPinchZoom(false);

        mBarChart.setDrawGridBackground(false);

        XAxis xAxis = mBarChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceBetweenLabels(1);

        YAxis leftAxis = mBarChart.getAxisLeft();
//        leftAxis.setLabelCount(9, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(8f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mBarChart.getAxisRight();
//        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(8f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(8f);
        l.setTextSize(11f);
        l.setXEntrySpace(2f);

//        ArrayList<String> xVals = new ArrayList<>();

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < mList.size(); i++) {
//            xVals.add(mKeys.get(mList.get(i).getXIndex()));
            yVals1.add(new BarEntry(mList.get(i).getVal(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "费用");
        set1.setBarSpacePercent(10f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(mKeys, dataSets);
        data.setValueTextSize(10f);

        mBarChart.setData(data);
        mBarChart.invalidate();

        mBarChart.animateY(2500);
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int tag) {
        String date = TimeUtils.toStringData(year, monthOfYear, dayOfMonth);
        Calendar now = Calendar.getInstance();
        String nowString = TimeUtils.toStringData(now.get(Calendar.YEAR), now.get(Calendar.MONTH) + 1, now.get(Calendar.DAY_OF_MONTH));
        long diff = TimeUtils.compareDate(nowString, date);
        if (diff < 0) {
            Snackbar.make(mStartEdit, getString(R.string.date_error), Snackbar.LENGTH_LONG)
                    .setAction(getString(R.string.snackbar_reset), null).show();
            return;
        }

        switch (tag) {
            case R.id.view_start:
                if (endDate != 0) {
                    if (TimeUtils.stringParseTimestamp(date) > endDate) {
                        Toast.makeText(this, getString(R.string.start_end_date_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                startDate = TimeUtils.stringParseTimestamp(date);
                mStartEdit.setText(date);

                if (startDate != 0 && endDate != 0) {
                    request.loadItem(this, startDate, endDate, type);
                }

                break;
            case R.id.view_end:
                if (startDate != 0) {
                    if (startDate > TimeUtils.stringParseTimestamp(date)) {
                        Toast.makeText(this, getString(R.string.start_end_date_error), Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                endDate = TimeUtils.stringParseTimestamp(date);
                mEndEdit.setText(date);

                if (startDate != 0 && endDate != 0) {
                    request.loadItem(this, startDate, endDate, type);
                }
                break;
        }
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

    public static void launch(Context context) {
        context.startActivity(new Intent(context, TimeFormActivity.class));
    }
}
