//package com.hjhrq1991.tool.Widget;
//
//import android.content.Context;
//import android.util.AttributeSet;
//import android.view.MotionEvent;
//import android.view.View;
//import android.widget.RelativeLayout;
//
//import com.facebook.rebound.Spring;
//import com.facebook.rebound.SpringConfig;
//import com.facebook.rebound.SpringListener;
//import com.facebook.rebound.SpringSystem;
//
///**
// * Created by yanghailong on 15/2/7.
// */
//public class ReboundableRelativeLayout extends RelativeLayout implements SpringListener {
//
//    private SpringSystem mSpringSystem;
//
//    private Spring mSpring;
//
//
//    private int tension = 70;//张力系数，越大，缩小范围越小
//
//    private int friction = 7; //阻力系数，越大，缩小范围和反弹次数越小
//
//    private final long clickEffect = 500; //如果 点击时长超过500则认为点击不成立
//
//    private long currentClickTime ;
//
//    public interface  onReboundClickListener{
//        public void onClick(View view);
//    }
//
//
//    public onReboundClickListener onReboundClickListener;
//
//    public void setOnReboundClickListener(onReboundClickListener onClick){
//        this.onReboundClickListener = onClick;
//    }
//
//
//    public ReboundableRelativeLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
//
//
//        mSpringSystem = SpringSystem.create();
//
//        mSpring = mSpringSystem.createSpring();
//
//        mSpring.addListener(this);
//
//        SpringConfig springConfig = new SpringConfig(tension, friction);
//        mSpring.setSpringConfig(springConfig);
//
//    }
//
//
//
//
//    public void endSpring(){
//        mSpring.setEndValue(0f);
//    }
//
//    public void springVale(float value){
//        mSpring.setEndValue(value);
//
//    }
//
//
//    @Override
//    public void onSpringUpdate(Spring spring) {
//        float value = (float) spring.getCurrentValue();
//        float scale = 1f - (value * 0.8f);
//
//        setScaleX(scale);
//        setScaleY(scale);
//    }
//
//    @Override
//    public void onSpringAtRest(Spring spring) {
//        SpringConfig config = new SpringConfig(tension, friction);
//        mSpring.setSpringConfig(config);
//    }
//
//    @Override
//    public void onSpringActivate(Spring spring) {
//
//    }
//
//    @Override
//    public void onSpringEndStateChange(Spring spring) {
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        switch (event.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                mSpring.setEndValue(0.2f);
//                currentClickTime= System.currentTimeMillis();
//                return true;
//
//            case MotionEvent.ACTION_CANCEL:
//            case MotionEvent.ACTION_UP:
//                mSpring.setEndValue(0f);
//
//                if(onReboundClickListener!=null && System.currentTimeMillis() - currentClickTime < clickEffect){
//                    onReboundClickListener.onClick(this);
//                }
//                return true;
//
//        }
//        return super.onTouchEvent(event);
//    }
//}
