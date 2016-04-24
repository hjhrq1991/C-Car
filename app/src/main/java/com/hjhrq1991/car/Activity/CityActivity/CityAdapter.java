package com.hjhrq1991.car.Activity.CityActivity;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hjhrq1991.car.R;
import com.hjhrq1991.car.db.CityDB;
import com.hjhrq1991.tool.Base.SimpleOneViewHolderBaseAdapter;

/**
 * @author hjhrq1991 created at 1/13/16 22 01.
 * @Package com.hjhrq1991.car.Activity.CityActivity
 * @Description:
 */
public class CityAdapter extends SimpleOneViewHolderBaseAdapter<CityDB> {

    public CityAdapter(Context mContext) {
        super(mContext);
    }

    @Override
    public int getItemResource() {
        return R.layout.pop_up_item_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewHolder holder) {
        CityDB cityDB = getItem(position);
        TextView textView = holder.getView(R.id.name);
        textView.setText(cityDB.name);
        return convertView;
    }
}
