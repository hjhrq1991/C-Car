package com.hjhrq1991.tool.Util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;

public class DensityUtils
{
	public static int dipTopx(Context context, float dpValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}
	
	public static float dipTopxF(Context context, float dpValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (dpValue * scale + 0.5f);
	}


	public static int pxTodip(Context context, float pxValue){
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	  /**
     * 
     * 功能：获取屏幕密度
     * @param ctx
     * @return
     */
    public static int getScreenDPI(Context ctx){
    	DisplayMetrics metrics = new DisplayMetrics();
    	((Activity) ctx).getWindowManager().getDefaultDisplay().getMetrics(metrics);
    	//xh 320 h 240 m 160 l 120
        return  metrics.densityDpi;
    }
	
    /**
     * 
     * 功能：获取屏幕宽度
     * @param ctx
     * @return
     */
    public static int getScreenWidth(Context ctx){
        return  ((Activity) ctx).getWindowManager().getDefaultDisplay().getWidth();
    }
    /**
     * 
     * 功能：获取屏幕高度
     * @param ctx
     * @return
     */
    public static int getScreenHeight(Context ctx){
        return ((Activity) ctx).getWindowManager().getDefaultDisplay().getHeight();
    }
}
