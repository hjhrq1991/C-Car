package com.hjhrq1991.car.View.PopUpWindow;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hjhrq1991.car.Model.SelectModel;
import com.hjhrq1991.car.R;
import com.hjhrq1991.tool.Base.SimpleOneViewHolderBaseAdapter;

/**
 * @author hjhrq1991 created at 12/14/15 15 43.
 * @Package com.qoocc.xite.community.View.Community
 * @Description:
 */
public class PopUpAdapter extends SimpleOneViewHolderBaseAdapter<SelectModel> {

    public PopUpAdapter(Context context) {
        super(context);
    }


    @Override
    public int getItemResource() {
        return R.layout.pop_up_item_layout;
    }

    @Override
    public View getView(int position, View convertView, ViewHolder holder) {
        SelectModel model = getItem(position);

        TextView mName = holder.getView(R.id.name);
        mName.setText(model.getName());

        return convertView;
    }

}
