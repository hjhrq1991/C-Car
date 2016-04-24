package com.hjhrq1991.car.Activity.MainActivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hjhrq1991.car.Base.SimplerRecyclerViewAdapter;
import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.db.ConsumeDB;
import com.hjhrq1991.tool.Util.TimeUtils;

/**
 * @author hjhrq1991 created at 1/14/16 21 28.
 * @Package com.hjhrq1991.car.Activity.MainActivity
 * @Description:
 */
public class ListAdapterV2 extends SimplerRecyclerViewAdapter<ConsumeDB> {

    boolean isGas = false;

    public ListAdapterV2(Context context, OnItemClickLitener mOnItemClickLitener) {
        super(context, mOnItemClickLitener);
    }

    public void setGas(boolean gas) {
        isGas = gas;
    }

    @Override
    public int getLayoutResource(int type) {
        return R.layout.item_custom_layout;
    }

    @Override
    public RecyclerView.ViewHolder getLayoutHolder(View convertView, int type) {
        RecyclerView.ViewHolder baseHoder = new BaseViewHolder(convertView);
        return baseHoder;
    }

    @Override
    public RecyclerView.ViewHolder getView(RecyclerView.ViewHolder holder, int position) {
        ConsumeDB consumeDB = getItem(position);

        BaseViewHolder baseViewHolder = (BaseViewHolder) holder;

        baseViewHolder.mName.setText(context.getString(R.string.item_type));
        baseViewHolder.mDate.setText(context.getString(R.string.item_date));
        baseViewHolder.mTotal.setText(context.getString(R.string.item_total));
        baseViewHolder.mPrice.setText(context.getString(R.string.item_price));
        baseViewHolder.mMileage.setText(context.getString(R.string.item_mileage));

        if (consumeDB.type != CustomConstant.type_title) {
            baseViewHolder.mName.setText(consumeDB.name);
            baseViewHolder.mDate.setText(TimeUtils.getDateToString(consumeDB.date));
            baseViewHolder.mTotal.setText(consumeDB.total + "");

            if (isGas) {
                baseViewHolder.mPrice.setVisibility(View.VISIBLE);
                baseViewHolder.mMileage.setVisibility(View.VISIBLE);
                baseViewHolder.mPrice.setText(consumeDB.price + "");
                baseViewHolder.mMileage.setText(consumeDB.mileage + "");
            } else {
                baseViewHolder.mPrice.setVisibility(View.GONE);
                baseViewHolder.mMileage.setVisibility(View.GONE);
            }
        } else {
            if (isGas) {
                baseViewHolder.mPrice.setVisibility(View.VISIBLE);
                baseViewHolder.mMileage.setVisibility(View.VISIBLE);
            } else {
                baseViewHolder.mPrice.setVisibility(View.GONE);
                baseViewHolder.mMileage.setVisibility(View.GONE);
            }
        }

        baseViewHolder.mRoot.setOnClickListener(new OnItemClick(holder, position));
        return baseViewHolder;
    }

    class BaseViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;
        public TextView mDate;
        public TextView mTotal;
        public TextView mPrice;
        public TextView mMileage;
        public View mRoot;

        public BaseViewHolder(View view) {
            super(view);
            this.mName = (TextView) view.findViewById(R.id.name);
            this.mDate = (TextView) view.findViewById(R.id.date);
            this.mTotal = (TextView) view.findViewById(R.id.total);
            this.mPrice = (TextView) view.findViewById(R.id.price);
            this.mMileage = (TextView) view.findViewById(R.id.mileage);
            this.mRoot = view;
        }
    }
}
