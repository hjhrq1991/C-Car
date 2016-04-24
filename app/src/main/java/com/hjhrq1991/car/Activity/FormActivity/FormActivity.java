package com.hjhrq1991.car.Activity.FormActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.hjhrq1991.car.Activity.TimeFormActivity.TimeFormActivity;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.Util.DatePickerUtils;
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
public class FormActivity extends BaseActivity implements OnloadFinishList, DatePickerDialog.OnDateSetListener {

    @Bind(R.id.chart_pie)
    PieChart mPieChart;

    @Bind(R.id.chart_bar)
    BarChart mBarChart;

    @Bind(R.id.date_start)
    EditText mStartEdit;

    @Bind(R.id.date_end)
    EditText mEndEdit;

    @Bind(R.id.empty_tv)
    TextView mEmptyView;

    private IRequest request;

    /**
     * 图表类型
     */
    private int mChartType = CustomConstant.type_pie;

    private String[] mType;

    private long startDate = 0;

    private long endDate = 0;

    @Override
    public int getLayoutResource() {
        return R.layout.activity_form_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mType = getResources().getStringArray(R.array.type);

        request = new RequestImpl();
        request.loadItem(this, mChartType);
    }

    @OnClick({R.id.view_start, R.id.view_end})
    public void onClick(View v) {
        String date;
        switch (v.getId()) {
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

    private void showDatePicker(String date, int tag) {
        DatePickerUtils.initTime(getFragmentManager(), this, date, tag);
    }

    @Override
    public void loadPieFinished(List<Entry> mList) {
        System.out.println("hrq-----回调了");
        if (mList != null && mList.size() > 0) {
            mPieChart.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            setPieData(mList);
        } else {
            mPieChart.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void loadBarFinished(List<BarEntry> mList) {
        if (mList != null && mList.size() > 0) {
            System.out.println("hrq-----回调了");

            mBarChart.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);
            setBarData(mList);
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
    private void setBarData(List<BarEntry> mList) {
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
        leftAxis.setLabelCount(9, false);
        leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
        leftAxis.setSpaceTop(8f);
        leftAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        YAxis rightAxis = mBarChart.getAxisRight();
        rightAxis.setDrawGridLines(false);
        rightAxis.setLabelCount(8, false);
        rightAxis.setSpaceTop(8f);
        rightAxis.setAxisMinValue(0f); // this replaces setStartAtZero(true)

        Legend l = mBarChart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        l.setForm(Legend.LegendForm.SQUARE);
        l.setFormSize(8f);
        l.setTextSize(11f);
        l.setXEntrySpace(2f);

        ArrayList<String> xVals = new ArrayList<>();

        ArrayList<BarEntry> yVals1 = new ArrayList<>();

        for (int i = 0; i < mList.size(); i++) {
            xVals.add(mType[mList.get(i).getXIndex()]);
            yVals1.add(new BarEntry(mList.get(i).getVal(), i));
        }

        BarDataSet set1 = new BarDataSet(yVals1, "费用");
        set1.setBarSpacePercent(10f);

        ArrayList<IBarDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        BarData data = new BarData(xVals, dataSets);
        data.setValueTextSize(10f);

        mBarChart.setData(data);
        mBarChart.invalidate();

        mBarChart.animateY(2500);
    }

    /**
     * 饼状图
     *
     * @param mList
     */
    private void setPieData(List<Entry> mList) {

        mPieChart.setUsePercentValues(true);
        mPieChart.setDescription("");
        mPieChart.setExtraOffsets(5, 10, 5, 5);

        mPieChart.setDragDecelerationFrictionCoef(0.95f);

        mPieChart.setDrawHoleEnabled(true);
        mPieChart.setHoleColorTransparent(true);

        mPieChart.setTransparentCircleColor(Color.WHITE);
        mPieChart.setTransparentCircleAlpha(110);

        mPieChart.setHoleRadius(58f);
        mPieChart.setTransparentCircleRadius(61f);

        mPieChart.setDrawCenterText(true);

        mPieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        mPieChart.setRotationEnabled(false);
        mPieChart.setHighlightPerTapEnabled(true);

        // add a selection listener
//        mPieChart.setOnChartValueSelectedListener(this);

        ArrayList<String> xVals = new ArrayList<>();

        float totalValue = 0f;

        for (int i = 0; i < mList.size(); i++) {
            totalValue += mList.get(i).getVal();
            xVals.add(mType[mList.get(i).getXIndex()]);
        }

        mPieChart.setCenterText("总费用：" + totalValue + "元");

        PieDataSet dataSet = new PieDataSet(mList, "记账类型");
        dataSet.setSliceSpace(2f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors
        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(10f);
        data.setValueTextColor(Color.WHITE);
        mPieChart.setData(data);

        // undo all highlights
        mPieChart.highlightValues(null);

        mPieChart.invalidate();

        mPieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

        Legend l = mPieChart.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(4f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
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
                    request.loadItem(this, startDate, endDate, mChartType);
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
                    request.loadItem(this, startDate, endDate, mChartType);
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_pie) {
            if (mChartType != CustomConstant.type_pie) {
                mPieChart.setVisibility(View.VISIBLE);
                mBarChart.setVisibility(View.GONE);
                mChartType = CustomConstant.type_pie;

                if (startDate != 0 && endDate != 0) {
                    request.loadItem(this, startDate, endDate, mChartType);
                } else {
                    request.loadItem(this, mChartType);
                }
            }
            return true;
        } else if (id == R.id.action_bar) {
            if (mChartType != CustomConstant.type_bar) {
                mPieChart.setVisibility(View.GONE);
                mBarChart.setVisibility(View.VISIBLE);
                mChartType = CustomConstant.type_bar;

                if (startDate != 0 && endDate != 0) {
                    request.loadItem(this, startDate, endDate, mChartType);
                } else {
                    request.loadItem(this, mChartType);
                }
            }
            return true;
        } else if (id == R.id.action_time) {
            TimeFormActivity.launch(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
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
        context.startActivity(new Intent(context, FormActivity.class));
    }
}
