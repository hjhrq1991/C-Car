//package com.hjhrq1991.car.View;
//
//import android.app.AlertDialog;
//import android.app.NotificationManager;
//import android.content.Context;
//import android.content.Intent;
//import android.graphics.Bitmap;
//import android.graphics.PixelFormat;
//import android.net.Uri;
//import android.view.Gravity;
//import android.view.View;
//import android.view.ViewGroup;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.qoocc.Model.BannerModel;
//
///**
// * @author hjhrq1991 created at 9/8/15 09 07.
// * @Package com.qoocc.View
// * @Description:
// */
//public class BannerView {
//    private Context mContext;
//    private BannerModel model;
//    private Bitmap bannerImage;
//
//    /**
//     * 宽度
//     */
//    private static int AD_Width = 320;
//    /**
//     * 高度
//     */
//    private static int AD_Heigth = 50;
//
////    NotificationManager mNotifyMgr;
//
//
//    public BannerView(Context context, BannerModel model, Bitmap bitmap) {
//        this.bannerImage = bitmap;
//        this.model = model;
//        this.mContext = context;
////        mNotifyMgr = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
//        initViev();
//    }
//
//    private void initViev() {
//
//        final WindowManager wm = (WindowManager) mContext.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
//        WindowManager.LayoutParams para = new WindowManager.LayoutParams();
//        para.height = WindowManager.LayoutParams.MATCH_PARENT;
//        para.width = WindowManager.LayoutParams.MATCH_PARENT;
//        para.format = PixelFormat.RGBA_8888;
//
//        para.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
//        para.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
//
//        final RelativeLayout mView = new RelativeLayout(mContext);
//        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        mView.setLayoutParams(params1);
//        mView.setBackgroundColor(mContext.getResources().getColor(android.R.color.transparent));
//
//
//        RelativeLayout mBackground = new RelativeLayout(mContext);
//        mBackground.setBackgroundColor(mContext.getResources().getColor(android.R.color.darker_gray));
//        RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dip2px(mContext, AD_Heigth));
//        params2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        mBackground.setLayoutParams(params2);
//        mView.addView(mBackground);
//
//        ImageView mImageView = new ImageView(mContext);
//        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(mContext, AD_Width), dip2px(mContext, AD_Heigth));
//        params.addRule(RelativeLayout.CENTER_IN_PARENT);
//        mImageView.setLayoutParams(params);
//        mImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
//        mImageView.setImageBitmap(bannerImage);
//        mBackground.addView(mImageView);
//
//        ImageView mClosedBtn = new ImageView(mContext);
//        mClosedBtn.setImageResource(mContext.getResources().getIdentifier("icon_closed", "drawable", mContext.getPackageName()));
//        RelativeLayout.LayoutParams closeParams = new RelativeLayout.LayoutParams(dip2px(mContext, 20f), dip2px(mContext, 20f));
//        closeParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//        closeParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        mClosedBtn.setLayoutParams(closeParams);
//        mBackground.addView(mClosedBtn);
//
//        wm.addView(mView, para);
//
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //浏览器打开url
//                Intent mIntent = new Intent();
//                mIntent.setAction(Intent.ACTION_VIEW);
//                mIntent.setData(Uri.parse(model.getUrl()));
//                mIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////                mIntent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
//                mContext.startActivity(mIntent);
//
////                showNotification();
//                wm.removeView(mView);
//            }
//        });
//
//        mClosedBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //取消dialog
//                wm.removeView(mView);
//            }
//        });
//    }
//
//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
//    private int dip2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    /**
//     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
//     */
//    private int px2dip(Context context, float pxValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (pxValue / scale + 0.5f);
//    }
//}
