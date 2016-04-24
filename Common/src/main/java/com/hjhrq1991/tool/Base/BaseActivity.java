package com.hjhrq1991.tool.Base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hannesdorfmann.swipeback.Position;
import com.hannesdorfmann.swipeback.SwipeBack;
import com.hannesdorfmann.swipeback.transformer.SlideSwipeBackTransformer;
import com.hjhrq1991.tool.AppManager;
import com.hjhrq1991.tool.R;

import butterknife.ButterKnife;

/**
 * Activity基类，不允许使用getSerializableExtra
 * Created by yanghailong on 15/1/31.
 */
public abstract class BaseActivity extends ActionBarActivity {

    private AppManager appManager = AppManager.getAppManager();

    protected Toolbar toolbar;

    protected TextView toolbar_title;

    protected ImageView toolbar_back;

    /**
     * 获取主界面的Layout
     *
     * @return
     */
    public abstract int getLayoutResource();

    //是否执行动画和允许滑动返回
    protected boolean isAnimAndSwipeBack = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isAnimAndSwipeBack) {
            overridePendingTransition(R.anim.swipeback_slide_right_in,
                    R.anim.swipeback_stack_to_back);
            SwipeBack.attach(this, Position.LEFT)
                    .setSwipeBackTransformer(new SlideSwipeBackTransformer())
                    .setDividerEnabled(true)
                    .setDrawOverlay(true)
                    .setContentView(getLayoutResource()).setSwipeBackView(R.layout.swipeback_guid_layout);
        } else {
            setContentView(getLayoutResource());
        }

        appManager.addActivity(this);
    }


    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);

        View v = findViewById(R.id.toolbar);
        if (v != null) {
            toolbar = (Toolbar) v;
            setSupportActionBar(toolbar);
            toolbar_title = (TextView) v.findViewById(R.id.toolbar_title);
            if (toolbar_title != null) {
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }

            //解决华为 toolbar onOptionsItemSelected 无法退出的问题
            if (isAnimAndSwipeBack) {
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                setTranslucentStatus(true);
//            }
//
//            SystemBarTintManager tintManager = new SystemBarTintManager(this);
//            tintManager.setStatusBarTintEnabled(true);
//            tintManager.setStatusBarTintResource(R.color.colorPrimaryDark);
        }
    }

//    public abstract int getStatusBarTintResource();

//    @TargetApi(19)
//    private void setTranslucentStatus(boolean on) {
//        Window win = getWindow();
//        WindowManager.LayoutParams winParams = win.getAttributes();
//        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
//        if (on) {
//            winParams.flags |= bits;
//        } else {
//            winParams.flags &= ~bits;
//        }
//        win.setAttributes(winParams);
//    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        if (!isChild()) {
            onTitleChanged(getTitle(), getTitleColor());
        }
    }


    @Override
    protected void onTitleChanged(CharSequence title, int color) {
//        super.onTitleChanged(title, color);
        if (toolbar_title != null) {
            toolbar_title.setText(title);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (isAnimAndSwipeBack) {
            overridePendingTransition(R.anim.swipeback_stack_to_front,
                    R.anim.swipeback_stack_right_out);
        }
    }


    /**
     * Get string extra from activity's intent
     *
     * @param name
     * @return extra
     */
    protected String getStringExtra(final String name) {
        return getIntent().getStringExtra(name);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        ButterKnife.unbind(this);
        appManager.finishActivity(this);
    }
}