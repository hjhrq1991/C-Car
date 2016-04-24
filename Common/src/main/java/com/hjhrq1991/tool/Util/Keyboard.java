package com.hjhrq1991.tool.Util;

import android.view.View;
import android.view.inputmethod.InputMethodManager;

import static android.content.Context.INPUT_METHOD_SERVICE;
 
 
/** 
 * Keyboard utilities 
 */ 
public class Keyboard { 
 
 
  /** 
   * Hide soft input method manager 
   * 
   * @param view 
   * @return view 
   */ 
  public static View hideSoftInput(final View view) {
    InputMethodManager manager = (InputMethodManager) view.getContext()
        .getSystemService(INPUT_METHOD_SERVICE);
    if (manager != null)
      manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    return view;
  } 
} 