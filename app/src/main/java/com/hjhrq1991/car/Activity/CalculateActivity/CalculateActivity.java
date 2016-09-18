package com.hjhrq1991.car.Activity.CalculateActivity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.db.ConsumeDB;
import com.hjhrq1991.tool.Base.BaseActivity;
import com.umeng.analytics.MobclickAgent;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author hjhrq1991 created at 1/10/16 18 40.
 * @Package com.hjhrq1991.car.Activity.CalculateActivity
 * @Description:
 */
public class CalculateActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    TextView mTitleTv;
    TextView mTotalTv;
    TextView mMileageTv;
    TextView mOilTv;
    TextView mCalculateTv;
    TextView mAveragePriceTv;
    TextView mSpendTv;
    TextView mCalculateTypeTv;
    RadioGroup mCalculateType;
    TextView mEmptyView;

    private List<ConsumeDB> list;//
    private float mileage = 0f;//总公里数
    private float total = 0f;//总花费
    private float totalOil = 0f;//总加油量
    private float oilPrice = 0f;//油平均单价
    private int count = 0;//统计次数
    private float average = 0f;//每公里平均油耗

    @Override
    public int getLayoutResource() {
        return R.layout.activity_calculate_layout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        loadData();
    }

    private void initView() {
        mTitleTv = (TextView) findViewById(R.id.title_tv);
        mTotalTv = (TextView) findViewById(R.id.total_tv);
        mMileageTv = (TextView) findViewById(R.id.mileage_tv);
        mOilTv = (TextView) findViewById(R.id.oil_tv);
        mCalculateTv = (TextView) findViewById(R.id.calculate_tv);
        mAveragePriceTv = (TextView) findViewById(R.id.average_price_tv);
        mSpendTv = (TextView) findViewById(R.id.spend_tv);
        mCalculateTypeTv = (TextView) findViewById(R.id.calculate_type_tv);
        mEmptyView = (TextView) findViewById(R.id.empty_tv);
        mCalculateType = (RadioGroup) findViewById(R.id.calculate_type);

    }

    private void loadData() {
        list = ConsumeDB.selectGasType();

        if (list == null || list.size() <= 1) {
            mTitleTv.setVisibility(View.GONE);
            mTotalTv.setVisibility(View.GONE);
            mMileageTv.setVisibility(View.GONE);
            mCalculateTv.setVisibility(View.GONE);
            mOilTv.setVisibility(View.GONE);
            mAveragePriceTv.setVisibility(View.GONE);
            mSpendTv.setVisibility(View.GONE);
            mCalculateTypeTv.setVisibility(View.GONE);
            mCalculateType.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        } else {
            mTitleTv.setVisibility(View.VISIBLE);
            mTotalTv.setVisibility(View.VISIBLE);
            mMileageTv.setVisibility(View.VISIBLE);
            mCalculateTv.setVisibility(View.VISIBLE);
            mOilTv.setVisibility(View.VISIBLE);
            mAveragePriceTv.setVisibility(View.VISIBLE);
            mSpendTv.setVisibility(View.VISIBLE);
            mCalculateTypeTv.setVisibility(View.VISIBLE);
            mCalculateType.setVisibility(View.VISIBLE);
            mEmptyView.setVisibility(View.GONE);

            mCalculateType.setOnCheckedChangeListener(this);

            mileage = list.get(list.size() - 1).mileage - list.get(0).mileage;
            total = 0f;
            totalOil = 0f;
            oilPrice = 0f;
            count = 0;
            for (int i = 0; i < list.size() - 1; i++) {
                ConsumeDB db = list.get(i);
                total += db.total;
                totalOil += db.oil;
                oilPrice += db.price;
                count++;
            }
            average = totalOil / mileage;
            setScoreAnimation(mTotalTv, new BigDecimal(total).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_total);
            setScoreAnimation(mMileageTv, new BigDecimal(mileage).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_mileage);
            setScoreAnimation(mOilTv, new BigDecimal(totalOil).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_oil);
            setScoreAnimation(mAveragePriceTv, new BigDecimal(oilPrice / count).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_average_price);

            //通过设置的属性选择默认算法
            boolean isTrue = PreferenceManager.getDefaultSharedPreferences(this).getBoolean(CustomConstant.SETTINGS_CALCULATE, true);
            if (isTrue) {
                ((RadioButton) mCalculateType.findViewById(R.id.weighted_average)).setChecked(true);
            } else {
                ((RadioButton) mCalculateType.findViewById(R.id.nomal_average)).setChecked(true);
            }
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        average = 0f;
        switch (checkedId) {
            case R.id.nomal_average:
                average = totalOil / mileage;
                setScoreAnimation(mCalculateTv, new BigDecimal(average * 100).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_average);
                setScoreAnimation(mSpendTv, new BigDecimal(average * (oilPrice / count)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_spend);
                break;
            case R.id.weighted_average:
//                平均油耗＝第1次油耗×（第1次加油量÷总加油量）+第2次油耗×（第2次加油量÷总加油量）+...+第n次油耗×（第n次加油量÷总加油量）
                for (int i = 0; i < list.size(); i++) {
                    if (i + 1 < list.size()) {
                        ConsumeDB db = list.get(i);
                        ConsumeDB next = list.get(i + 1);
                        average += (db.oil / (next.mileage - db.mileage) * (db.oil / totalOil));
                    }
                }
                setScoreAnimation(mCalculateTv, new BigDecimal(average * 100).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_average);
                setScoreAnimation(mSpendTv, new BigDecimal(average * (oilPrice / count)).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue(), R.string.calculate_spend);

                break;
        }
    }

    /**
     * 设置分数的动画
     *
     * @param textView
     * @param score
     */
    public void setScoreAnimation(final TextView textView, float score, final int stringId) {
        ValueAnimator scoreAnima = ValueAnimator.ofFloat(0f, score);
        scoreAnima.setDuration(2000);
        scoreAnima
                .addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        String value = String.format("%.2f", (Float) animation.getAnimatedValue());

                        String totalString = String.format(getString(stringId), value);
                        int startIndex = totalString.indexOf(String.valueOf(value));
                        int endIndex = startIndex + String.valueOf(value).length();
                        SpannableString spannableString = new SpannableString(totalString);
                        spannableString.setSpan(new RelativeSizeSpan(2.0f), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorPrimary)), startIndex, endIndex, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

                        textView.setText(spannableString);

                    }
                });
        scoreAnima.start();
    }

    public static void launch(Context context) {
        context.startActivity(new Intent(context, CalculateActivity.class));
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