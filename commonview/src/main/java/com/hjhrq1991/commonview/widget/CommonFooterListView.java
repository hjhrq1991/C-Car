package com.hjhrq1991.commonview.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ListView;

import com.hjhrq1991.commonview.View.CommonFooterView;

/**
 * @author hjhrq1991 created at 4/24/16 09 33.
 * @Package com.hjhrq1991.car.View
 * @Description:通用底部加载更多listivew
 */
public class CommonFooterListView extends ListView {

    private CommonFooterView mLoadingFooter;

    public CommonFooterListView(Context context) {
        super(context);
        addLoadMoreFooter();
    }

    public CommonFooterListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addLoadMoreFooter();
    }

    public CommonFooterListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addLoadMoreFooter();
    }

    public void setState(CommonFooterView.State state) {
        if (mLoadingFooter != null) {
            mLoadingFooter.setState(state);
        }
    }

    /**
     * 添加加载更多底部
     */
    public void addLoadMoreFooter() {
        if (mLoadingFooter == null) {
            mLoadingFooter = new CommonFooterView(getContext());
            addFooterView(mLoadingFooter);
            mLoadingFooter.setState(CommonFooterView.State.RESET);
        }
    }

    /**
     * 设置footer view的点击事件
     */
    public void setOnFooterClickListener(@Nullable CommonFooterView.OnFooterClickListener onFooterClickListener) {
        mLoadingFooter.setOnFooterClickListener(onFooterClickListener);
    }

}
