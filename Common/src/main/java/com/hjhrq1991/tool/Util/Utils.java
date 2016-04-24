package com.hjhrq1991.tool.Util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Utils {

    public static int convertDpToPixel(Context context, int dp) {
        float density = context.getResources().getDisplayMetrics().density;
        return Math.round((float) dp * density);
    }

    public static float convertPixelsToDp(Context context, int px) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return px / (metrics.densityDpi / 160f);
    }



    public static int getCurScreenWidth(Context context) {
        if (context != null)
            return ((WindowManager) context.getSystemService("window"))
                    .getDefaultDisplay().getWidth();
        else
            return 480;
    }

    public static int getCurScreenHeight(Context context) {
        return ((WindowManager) context.getSystemService("window"))
                .getDefaultDisplay().getHeight();
    }

}
