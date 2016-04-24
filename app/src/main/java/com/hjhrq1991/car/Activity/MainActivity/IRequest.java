package com.hjhrq1991.car.Activity.MainActivity;

/**
 * @author hjhrq1991 created at 1/10/16 10 55.
 * @Package com.hjhrq1991.car.Activity.MainActivity
 * @Description:
 */
public interface IRequest {
    void loadItem(OnloadFinishListener onloadFinishListener, int type, int pageSize, long date);
}
