package com.hjhrq1991.car.Activity.MainActivityV3;

import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.db.ConsumeDB;

import java.util.List;

/**
 * @author hjhrq1991 created at 1/10/16 10 55.
 * @Package com.hjhrq1991.car.Activity.MainActivity
 * @Description:
 */
public class RequestImpl implements IRequest {

    @Override
    public void loadItem(OnloadFinishListener onloadFinishListener, int type, int pageSize, long date) {
        onloadFinishListener.loadFinished(load(type, pageSize, date));
    }

    private List<ConsumeDB> load(int type, int pageSize, long date) {
        if (type == CustomConstant.type_all) {
            return ConsumeDB.getAll(pageSize, date);
        } else {
            return ConsumeDB.getFromType(type, pageSize, date);
        }
    }
}
