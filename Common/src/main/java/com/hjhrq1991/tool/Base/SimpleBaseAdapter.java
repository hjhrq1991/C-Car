package com.hjhrq1991.tool.Base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 有多个ViewHolder可以使用
 * Created by yanghailong on 15/2/2.
 */
public abstract class SimpleBaseAdapter<T> extends BaseAdapter {

    protected Context mContext;

    protected List<T> mList;

    public SimpleBaseAdapter(Context mContext) {
        this.mContext = mContext;
        mList = new ArrayList<>();
    }


    public abstract int getItemViewType(int position);

    public abstract int getViewTypeCount();

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public T getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(List<T> elem) {
        mList.addAll(elem);
        notifyDataSetChanged();
    }

    public void remove(T elem) {
        mList.remove(elem);
        notifyDataSetChanged();
    }

    public void remove(int index) {
        mList.remove(index);
        notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        mList.clear();
        mList.addAll(elem);
        notifyDataSetChanged();
    }

    @Override
    public final View getView(int position, View convertView, ViewGroup parent) {
        BaseViewHolder baseHoder;
        int type = getItemViewType(position);
        if (convertView == null) {

            convertView = LayoutInflater.from(mContext).inflate(getLayoutResource(type), null);
            baseHoder = getLayoutHolder(convertView, type);
        } else {
            Object tag = convertView.getTag();

            baseHoder = (BaseViewHolder) tag;
        }
        baseHoder.render(position);

        getView(position);
        return convertView;
    }


    /**
     * 返回对应的layout
     *
     * @param type
     * @return
     */
    public abstract int getLayoutResource(int type);

    /**
     * 返回对应的ViewHolder
     *
     * @param convertView
     * @param type
     * @return
     */
    public abstract BaseViewHolder getLayoutHolder(View convertView, int type);


    /**
     * 如果想做一些通用模板的事，可以实现此方法
     *
     * @param position
     */
    public void getView(int position) {

    }

}
