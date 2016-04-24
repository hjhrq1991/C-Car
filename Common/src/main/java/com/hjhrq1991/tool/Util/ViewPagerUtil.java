package com.hjhrq1991.tool.Util;

import android.app.Fragment;
import android.support.v4.view.ViewPager;

public class ViewPagerUtil {

    /**
     * 找到当前的Fragment
     *
     * @param viewPager
     * @return
     */
    public static Fragment currentFragmentForViewPager(ViewPager viewPager) {
        return (Fragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
    }

    /**
     * 找到当前的Fragment
     *
     * @param viewPager
     * @return
     */
    public static Fragment currentFragmentForViewPager(ViewPager viewPager,int index) {
        return (Fragment) viewPager.getAdapter().instantiateItem(viewPager, index);
    }

    /**
     * 找到当前的Fragment
     *
     * @param viewPager
     * @return
     */
    public static android.support.v4.app.Fragment currentFragmentForV4ViewPager(ViewPager viewPager) {
        return (android.support.v4.app.Fragment) viewPager.getAdapter().instantiateItem(viewPager, viewPager.getCurrentItem());
    }
    /**
     * 找到当前的Fragment
     *
     * @param viewPager
     * @return
     */
    public static android.support.v4.app.Fragment currentFragmentForV4ViewPager(ViewPager viewPager,int index) {
        return (android.support.v4.app.Fragment) viewPager.getAdapter().instantiateItem(viewPager, index);
    }

}
