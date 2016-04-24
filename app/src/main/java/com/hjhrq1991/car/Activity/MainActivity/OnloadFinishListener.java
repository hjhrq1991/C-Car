package com.hjhrq1991.car.Activity.MainActivity;

import com.hjhrq1991.car.db.ConsumeDB;

import java.util.List;

/**
 * @author hjhrq1991 created at 1/10/16 10 53.
 * @Package com.hjhrq1991.car.Activity.MainActivity
 * @Description:
 */
public interface OnloadFinishListener {

    void loadFinished(List<ConsumeDB> models);
}
