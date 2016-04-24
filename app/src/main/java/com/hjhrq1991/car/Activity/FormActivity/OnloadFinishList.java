package com.hjhrq1991.car.Activity.FormActivity;

import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;

import java.util.List;

/**
 * @author hjhrq1991 created at 2/18/16 11 01.
 * @Package com.hjhrq1991.car.Activity.FormActivity
 * @Description:
 */
public interface OnloadFinishList {

    /**
     * @param mList
     */
    void loadPieFinished(List<Entry> mList);

    /**
     * @param mList
     */
    void loadBarFinished(List<BarEntry> mList);

}
