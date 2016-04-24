package com.hjhrq1991.car.Activity.MainActivity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hjhrq1991.car.Constant.CustomConstant;
import com.hjhrq1991.car.R;
import com.hjhrq1991.car.db.ConsumeDB;
import com.hjhrq1991.tool.Base.SimpleOneViewHolderBaseAdapter;
import com.hjhrq1991.tool.Util.TimeUtils;

/**
 * @author hjhrq1991 created at 1/10/16 10 03.
 * @Package com.hjhrq1991.car.Activity.MainActivity
 * @Description:
 */
public class ListAdapter extends SimpleOneViewHolderBaseAdapter<ConsumeDB> {

    boolean isGas = false;

    public ListAdapter(Context mContext) {
        super(mContext);
    }

    public void setGas(boolean gas) {
        this.isGas = gas;
    }

    @Override
    public int getItemResource() {
        return R.layout.item_custom_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewHolder holder) {

        ConsumeDB consumeDB = getItem(position);
        TextView mName = holder.getView(R.id.name);
        TextView mDate = holder.getView(R.id.date);
        TextView mTotal = holder.getView(R.id.total);
        TextView mPrice = holder.getView(R.id.price);
        TextView mMileage = holder.getView(R.id.mileage);

        mName.setText(mContext.getString(R.string.item_type));
        mDate.setText(mContext.getString(R.string.item_date));
        mTotal.setText(mContext.getString(R.string.item_total));
        mPrice.setText(mContext.getString(R.string.item_price));
        mMileage.setText(mContext.getString(R.string.item_mileage));

        if (consumeDB.type != CustomConstant.type_title) {
            mName.setText(consumeDB.name);
            mDate.setText(TimeUtils.getDateToString(consumeDB.date));
            mTotal.setText(consumeDB.total + "");

            if (isGas) {
                mPrice.setVisibility(View.VISIBLE);
                mMileage.setVisibility(View.VISIBLE);
                mPrice.setText(consumeDB.price + "");
                mMileage.setText(consumeDB.mileage + "");
            } else {
                mPrice.setVisibility(View.GONE);
                mMileage.setVisibility(View.GONE);
            }
        } else {
            if (isGas) {
                mPrice.setVisibility(View.VISIBLE);
                mMileage.setVisibility(View.VISIBLE);
            } else {
                mPrice.setVisibility(View.GONE);
                mMileage.setVisibility(View.GONE);
            }
        }

        return convertView;
    }


}
