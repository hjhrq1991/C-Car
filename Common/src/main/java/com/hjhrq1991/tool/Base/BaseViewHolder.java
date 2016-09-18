package com.hjhrq1991.tool.Base;

import android.view.View;

/**
 * ViewHolder 基类
 * Created by yanghailong on 15/1/31.
 */
public abstract class BaseViewHolder {
    public BaseViewHolder(View converView) {
        converView.setTag(this);
    }

    public abstract void render(int position);
}
