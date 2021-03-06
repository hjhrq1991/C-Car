package com.hjhrq1991.car.Activity.FormActivity;

/**
 * @author hjhrq1991 created at 2/18/16 11 01.
 * @Package com.hjhrq1991.car.Activity.FormActivity
 * @Description:
 */
public interface IRequest {

    void loadItem(OnloadFinishList onloadFinishList, int type);

    /**
     *
     * @param onloadFinishList
     * @param startTime
     * @param endTime
     */
    void loadItem(OnloadFinishList onloadFinishList, long startTime, long endTime, int type);
}
