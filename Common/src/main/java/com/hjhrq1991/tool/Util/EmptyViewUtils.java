package com.hjhrq1991.tool.Util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;


public class EmptyViewUtils { 
 
 
    public static View createLoadingView(Context context,int loadingLayoutResource ) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(loadingLayoutResource, null);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setVisibility(View.GONE);
        return linearLayout;
    } 
 
 
    public static View createFailView(Context context,int loadingFialRelayoutResource) {
        LinearLayout linearLayout = (LinearLayout) LayoutInflater.from(context).inflate(loadingFialRelayoutResource, null);
        linearLayout.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setVisibility(View.GONE);
        return linearLayout;
    } 
 
 
} 