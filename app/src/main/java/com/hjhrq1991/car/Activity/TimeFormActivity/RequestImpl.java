package com.hjhrq1991.car.Activity.TimeFormActivity;

import com.github.mikephil.charting.data.BarEntry;
import com.hjhrq1991.car.db.ConsumeDB;
import com.hjhrq1991.tool.Util.TimeUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hjhrq1991 created at 2/18/16 11 01.
 * @Package com.hjhrq1991.car.Activity.FormActivity
 * @Description:
 */
public class RequestImpl implements IRequest {

    @Override
    public void loadItem(OnloadFinishList onloadFinishList, long startTime, long endTime, int type) {
        List<ConsumeDB> consumeDBs;
        if (type == 0) {
            consumeDBs = ConsumeDB.getData(startTime, endTime);
        } else {
            consumeDBs = ConsumeDB.getDataByType(startTime, endTime, type);
        }

        List<String> mKeys = new ArrayList<>();
        Map<String, Float> mList = new HashMap<>();
        List<BarEntry> mDatas = new ArrayList<>();

        if (consumeDBs != null && consumeDBs.size() > 0) {
            for (ConsumeDB consumeDB : consumeDBs) {
                String key = TimeUtils.getYearMonth(consumeDB.date);

                //处理数据源
                if (mKeys != null && mKeys.size() > 0) {
                    if (mList.containsKey(key)) {
                        mList.put(key, mList.get(key) + consumeDB.total);
                    } else {
                        mKeys.add(key);
                        mList.put(key, consumeDB.total);
                    }
                } else {
                    mList.put(key, consumeDB.total);
                    mKeys.add(key);
                }
            }

            if (mKeys != null && mKeys.size() > 0) {
                for (int i = 0; i < mKeys.size(); i++) {
                    mDatas.add(new BarEntry((float) (Math.round(mList.get(mKeys.get(i)) * 100) / 100), i));
                }
            }

        }

        onloadFinishList.loadFinished(mDatas, mKeys);
    }

}
