package com.hjhrq1991.car.Base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hjhrq1991 on 15/4/17.
 */
public abstract class SimplerRecyclerViewAdapter<T> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> mList;
    protected Context context;

    public SimplerRecyclerViewAdapter(Context context, OnItemClickLitener mOnItemClickLitener) {
        super();
        this.context = context;
        this.mOnItemClickLitener = mOnItemClickLitener;
        mList = new ArrayList<>();
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

    public T getItem(int position) {
        return mList.get(position);
    }


    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mList.size();
    }

    /**
     * 创建VIewHolder
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewtype) {
        // TODO Auto-generated method stub

        View v = LayoutInflater.from(parent.getContext()).inflate(
                getLayoutResource(viewtype), null);
        RecyclerView.ViewHolder holer = getLayoutHolder(v, viewtype);

        return holer;
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
    public abstract RecyclerView.ViewHolder getLayoutHolder(View convertView, int type);

    /**
     * 绑定viewholder
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // TODO Auto-generated method stub
        getView(holder, position);
    }

    /**
     * 如果想做一些通用模板的事，可以实现此方法
     *
     * @param holder
     * @param position
     * @return
     */
    public abstract RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, int position);


    /**
     * item点击事件的实现
     */
    public class OnItemClick implements View.OnClickListener {
        private RecyclerView.ViewHolder viewHolder;
        private int position;

        public OnItemClick(RecyclerView.ViewHolder viewHolder, int position) {
            this.viewHolder = viewHolder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickLitener != null)
                mOnItemClickLitener.onItemClick(viewHolder.itemView, position);
        }
    }

    protected OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener onItemClickLitener) {
        this.mOnItemClickLitener = onItemClickLitener;
    }

    /**
     * 写一个onitemclick的接口，并在activity实现这个接口，通过此接口实现数据的回调
     */
    public interface OnItemClickLitener {
        void onItemClick(View view, int position);
    }

}