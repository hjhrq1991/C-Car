package com.hjhrq1991.tool.Util;

import android.app.Activity;
import android.content.Intent;

public class ActivityUtils {
 
 
  /** 
   * Finish the given activity and start a home activity class. 
   * <p> 
   * This mirror the behavior of the home action bar button that clears the 
   * current activity and starts or brings another activity to the top. 
   * 
   * @param activity 
   * @param homeActivityClass 
   */ 
  public static void goHome(Activity activity, Class<?> homeActivityClass) {
    activity.finish();
    Intent intent = new Intent(activity, homeActivityClass);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
    activity.startActivity(intent);
  } 
} 