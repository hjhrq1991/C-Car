package com.hjhrq1991.tool.Base;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yanghailong on 15/1/31.
 */
public abstract class BaseFragment extends Fragment {


    public abstract int getLayoutResource();

    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResource(),null);
        return view;
    }


    /**
     * Get string extra from activity's intent
     * @param name
     * @return extra
     */
    protected String getStringExtra(final String name) {
        Activity activity = getActivity();
        if (activity != null)
            return activity.getIntent().getStringExtra(name);
        else
            return null;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
//        ButterKnife.unbind(this);
    }
}
