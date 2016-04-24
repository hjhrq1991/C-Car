package com.hjhrq1991.car.Base;

import android.content.Context;

import com.baidu.apistore.sdk.network.Parameters;

/**
 * @author hjhrq1991 created at 1/12/16 21 39.
 * @Package com.hjhrq1991.car.Base
 * @Description:
 */
public abstract class Command {

    protected Parameters parameters;

    public Command(Parameters parameters) {
        this.parameters = parameters;
    }

    public abstract void execute(Context mContext);
}
