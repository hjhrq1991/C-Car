package com.hjhrq1991.car.View.PopUpWindow;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.hjhrq1991.car.Model.SelectModel;
import com.hjhrq1991.car.R;

import java.util.List;

/**
 * @author hjhrq1991 created at 12/14/15 15 31.
 * @Package com.qoocc.xite.community.View
 * @Description:个人信息表通用下拉
 */
public class PopUpWin extends PopupWindow implements AdapterView.OnItemClickListener {

    private Context mContext;
    private ListView mListView;
    private List<SelectModel> mList;
    private PopUpAdapter mAdaper;
    private int type;
    private OnPopWindowListener popWindowListener;
    private int mWidth;

    public PopUpWin(Context context, List<SelectModel> list, int type, int width, OnPopWindowListener popWindowListener) {
        super(context);
        this.mContext = context;
        this.mList = list;
        this.type = type;
        this.popWindowListener = popWindowListener;
        this.mWidth = width;
        initViev();
    }

    private void initViev() {
        View mView = LayoutInflater.from(mContext).inflate(R.layout.pop_up_window_layout, null);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = mWidth;
        lp.bottomMargin = 70;

        this.setContentView(mView);
        this.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        this.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        mListView = (ListView) mView.findViewById(R.id.list);
        mListView.setLayoutParams(lp);
        mListView.setOnItemClickListener(this);

        mAdaper = new PopUpAdapter(mContext);
        mListView.setAdapter(mAdaper);

        mAdaper.replaceAll(mList);
    }

    @Override
    public void dismiss() {
        super.dismiss();

    }

    public interface OnPopWindowListener {

        void onItemClick(int type, SelectModel db);
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (popWindowListener != null) {
            popWindowListener.onItemClick(type, mAdaper.getItem(position));
        }
        dismiss();
    }
}
