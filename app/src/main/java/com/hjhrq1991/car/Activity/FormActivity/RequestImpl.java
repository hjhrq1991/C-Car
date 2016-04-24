package com.hjhrq1991.car.Activity.FormActivity;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.db.ConsumeDB;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hjhrq1991 created at 2/18/16 11 01.
 * @Package com.hjhrq1991.car.Activity.FormActivity
 * @Description:
 */
public class RequestImpl implements IRequest {

    @Override
    public void loadItem(OnloadFinishList onloadFinishList, int type) {
        if (type == CustomConstant.type_pie) {
            onloadFinishList.loadPieFinished(loadPieData());
        } else {
            onloadFinishList.loadBarFinished(loadBarData());
        }
    }

    @Override
    public void loadItem(OnloadFinishList onloadFinishList, long startTime, long endTime, int type) {
        if (type == CustomConstant.type_pie) {
            onloadFinishList.loadPieFinished(loadPieData(startTime, endTime));
        } else {
            onloadFinishList.loadBarFinished(loadBarData(startTime, endTime));
        }
    }

    /**
     * 筛选所有数据
     *
     * @return
     */
    private List<Entry> loadPieData() {
        List<Entry> mList = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            List<ConsumeDB> consumeDBs = ConsumeDB.getFromType(i);
            float value = 0f;
            if (consumeDBs != null && consumeDBs.size() > 0) {
                for (ConsumeDB consumeDB : consumeDBs) {
                    value += consumeDB.total;
                }
                //只显示有数据的类型
                mList.add(new Entry(value, i - 1));
            }
        }
        return mList;
    }

    /**
     * 筛选所有数据
     *
     * @return
     */
    private List<BarEntry> loadBarData() {
        List<BarEntry> mList = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            List<ConsumeDB> consumeDBs = ConsumeDB.getFromType(i);
            float value = 0f;
            if (consumeDBs != null && consumeDBs.size() > 0) {
                for (ConsumeDB consumeDB : consumeDBs) {
                    value += consumeDB.total;
                }
                //只显示有数据的类型
                mList.add(new BarEntry(value, i - 1));
            }
        }
        return mList;
    }

    /**
     * 筛选某个时间段内的数据
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private List<Entry> loadPieData(long startTime, long endTime) {
        List<Entry> mList = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            List<ConsumeDB> consumeDBs = ConsumeDB.getFromType(i, startTime, endTime);
            float value = 0f;
            if (consumeDBs != null && consumeDBs.size() > 0) {
                for (ConsumeDB consumeDB : consumeDBs) {
                    value += consumeDB.total;
                }
                //只显示有数据的类型
                mList.add(new Entry(value, i - 1));
            }
        }
        return mList;
    }

    /**
     * 筛选某个时间段内的数据
     *
     * @param startTime
     * @param endTime
     * @return
     */
    private List<BarEntry> loadBarData(long startTime, long endTime) {
        List<BarEntry> mList = new ArrayList<>();

        for (int i = 1; i <= 9; i++) {
            List<ConsumeDB> consumeDBs = ConsumeDB.getFromType(i, startTime, endTime);
            float value = 0f;
            if (consumeDBs != null && consumeDBs.size() > 0) {
                for (ConsumeDB consumeDB : consumeDBs) {
                    value += consumeDB.total;
                }
                //只显示有数据的类型
                mList.add(new BarEntry(value, i - 1));
            }
        }
        return mList;
    }
}
