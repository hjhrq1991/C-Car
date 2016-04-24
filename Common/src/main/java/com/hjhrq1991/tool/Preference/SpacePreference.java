package com.hjhrq1991.tool.Preference;

import android.content.Context;
import android.preference.PreferenceCategory;
import android.util.AttributeSet;

import com.hjhrq1991.tool.R;


/**
 * Created by yanghailong on 15/2/2.
 */
public class SpacePreference extends PreferenceCategory {

    public SpacePreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setLayoutResource(R.layout.preference_space_of_layout);
        setSelectable(false);
    }

    public SpacePreference(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }



}
