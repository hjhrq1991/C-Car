package com.hjhrq1991.commonview.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.hjhrq1991.commonview.R;

/**
 * 公用的footer view
 **/
public class CommonFooterView extends LinearLayout {

    private View footerContent;
    private ProgressBar progressBar;
    private TextView textView;
    private ImageView img_not_net_work_guid;
    /**
     * 当前footer view的状态
     */
    private State state;

    private OnFooterClickListener onFooterClickListener;

    public enum State {
        /**
         * 待加载的状态
         */
        RESET,
        /**
         * 正在加载状态
         */
        LOADING,
        /**
         * 完全隐藏的状态
         */
        HIDE,
        /**
         * 无网络状态
         **/
        NOTNETWORK
    }

    public CommonFooterView(Context context) {
        super(context);
        init(context);
    }

    public CommonFooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        footerContent = LayoutInflater.from(context).inflate(R.layout.load_data_footer, null);
        progressBar = (ProgressBar) footerContent.findViewById(R.id.progress_more);
        textView = (TextView) footerContent.findViewById(R.id.progress_text);
        img_not_net_work_guid = (ImageView) footerContent.findViewById(R.id.img_not_net_work_guid);
        setGravity(Gravity.CENTER);
        addView(footerContent, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
    }

    /**
     * 将footer view重置为“更多”状态
     */
    public void resetFooterView() {
        footerContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setText(R.string.footer_hint_normal);
        footerContent.setClickable(true);
        state = State.RESET;
//        setOnClickListener(null);
        img_not_net_work_guid.setVisibility(View.GONE);
    }


    /**
     * 无网络状态
     */
    public void resetNotNetWorkStateFoorterView() {
        footerContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        textView.setText("还没联网哦，去设置网络吧");

        footerContent.setClickable(true);
        state = State.RESET;

        footerContent.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                if (onFooterClickListener != null) {
                    onFooterClickListener.onNotNetWorkClick(view);
                }
            }
        });

        img_not_net_work_guid.setVisibility(View.VISIBLE);
    }

    /**
     * 将footer view置为“正在加载”状态
     */
    public void loadingFooterView() {
        footerContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        textView.setText(R.string.data_loading);
        footerContent.setClickable(true);
        state = State.LOADING;
        footerContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                if (onFooterClickListener != null) {
                    onFooterClickListener.onLoadMoreClick(view);
                }
            }
        });
        img_not_net_work_guid.setVisibility(View.GONE);
    }

    /**
     * 刷新状态
     */
    public void loadingFooterView(String loadText) {
        footerContent.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.VISIBLE);
        textView.setText(loadText);
        footerContent.setClickable(true);
        state = State.LOADING;
        footerContent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO Auto-generated method stub
                if (onFooterClickListener != null) {
                    onFooterClickListener.onLoadMoreClick(view);
                }
            }
        });
        img_not_net_work_guid.setVisibility(View.GONE);
    }

    /**
     * 完全隐藏footer view
     */
    public void hideFooterView() {
        footerContent.setVisibility(View.GONE);
        footerContent.setClickable(false);
        state = State.HIDE;
        setOnClickListener(null);
        img_not_net_work_guid.setVisibility(View.GONE);
    }

    public void setState(State state) {
        switch (state) {
            case LOADING:
                loadingFooterView();
                break;
            case RESET:
                resetFooterView();
                break;
            case HIDE:
                hideFooterView();
                break;
            case NOTNETWORK:
                resetNotNetWorkStateFoorterView();
                break;
            default:
                break;
        }
    }

    public interface OnFooterClickListener {
        /**
         * 无网络按钮点击
         */
        void onNotNetWorkClick(View view);

        /**
         * 加载更多点击
         */
        void onLoadMoreClick(View view);
    }

    /**
     * 设置footer view的点击事件
     */
    public void setOnFooterClickListener(OnFooterClickListener onFooterClickListener) {
        this.onFooterClickListener = onFooterClickListener;
    }

    //    /**
//     * 设置footer view的点击事件
//     *
//     * @param l
//     */
//    public void setFooterViewOnClickListener(OnClickListener l) {
//        footerContent.setOnClickListener(l);
//    }

    /**
     * 设置footer view的背景颜色
     *
     * @param resId
     */
    public void setFooterViewBackground(int resId) {
        footerContent.setBackgroundResource(resId);
    }

    /**
     * 获取当前的footer view的状态
     *
     * @return
     */
    public State getCurrentState() {
        return state;
    }

    /**
     * 当前的footer view 是否可以进入“正在加载”的状态
     *
     * @return
     */
    public boolean canLoading() {
        return getCurrentState() == State.RESET;
    }

    /**
     * 是否显示状态
     *
     * @return
     */
    public boolean isShowing() {
        return getCurrentState() != State.HIDE;
    }

    /**
     * 当前是否是正在加载状态
     */
    public boolean isLoading() {
        return getCurrentState() == State.LOADING;
    }

}
