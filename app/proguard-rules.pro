# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\adt-bundle-windows-x86_64-20140702\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
 -dontwarn
 -optimizationpasses 5
 -dontusemixedcaseclassnames
 -dontskipnonpubliclibraryclasses
 -dontpreverify
 -verbose
 -optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

 # keep住源文件以及行号 混淆后日志用，不加崩溃时会返回unknown source
 -keepattributes SourceFile,LineNumberTable

 -dontwarn com.nineoldandroids.**
 -dontwarn com.baidu.**
 -dontwarn m.framework.**
 -dontwarn android.support.**

 #阿里各种sdk
 -keepattributes Signature
 -keep class sun.misc.Unsafe { *; }
 -keep class com.taobao.** {*;}
 -keep class com.alibaba.** {*;}
 -keep class com.alipay.** {*;}
 -keep class com.ut.** {*;}
 -keep class com.ta.** {*;}
 -dontwarn com.taobao.**
 -dontwarn com.alibaba.**
 -dontwarn com.alipay.**
 -dontwarn com.ut.**
 -dontwarn com.ta.**

 # Butterknife
 -dontwarn butterknife.internal.**
 -keep class butterknife.** { *; }
 -keep class **$$ViewInjector { *; }

 -keepclasseswithmembernames class * {
     @butterknife.InjectView <fields>;
 }

 -keepclasseswithmembernames class * {
     @butterknife.OnClick <methods>;
     @butterknife.OnEditorAction <methods>;
     @butterknife.OnItemClick <methods>;
     @butterknife.OnItemLongClick <methods>;
     @butterknife.OnLongClick <methods>;
 }

 # EventBus
 -keepclassmembers class ** {
     public void onEvent*(**);
 }

 #百度语音
 -keep class com.baidu.android.** {*;}
 -keep class com.baidu.speechsynthesizer.** {*;}

 #讯飞
 -keep class com.iflytek.** {*;}
 #使用反射时过滤类型转换，讯飞离线合成需要使用
 -keepattributes Signature

 #JNI相关
 -keep class com.qoocc.serialport.SerialPort {*;}

 #数据库相关
 -keep class com.activeandroid.** { *; }
 -dontwarn com.ikoding.app.biz.dataobject.**
 -keep public class com.ikoding.app.biz.dataobject.** { *;}
 -keepattributes *Annotation*

 #MPAndroidChart
 -keep class com.github.mikephil.charting.** { *; }
 -dontwarn io.realm.**

 #友盟自动更新SDK
 -keepclassmembers class * {
    public <init>(org.json.JSONObject);
 }
 -keep public class com.hjhrq1991.car.R$*{
    public static final int *;
 }
 -keep public class * extends com.umeng.**
 -keep class com.umeng.** { *; }
 -dontwarn com.umeng.**

 -dontwarn com.hannesdorfmann.**

 #glide
 -keep public class * implements com.bumptech.glide.module.GlideModule
 -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
   **[] $VALUES;
   public *;
 }

 #极光推送
 -dontwarn cn.jpush.**
 -keep class cn.jpush.** { *; }

 #高德-3D 地图
 -keep class com.amap.api.mapcore.**{*;}
 -keep class com.amap.api.maps.**{*;}
 -keep class com.autonavi.amap.mapcore.*{*;}
 #高德 定位
 -keep class com.amap.api.location.**{*;}
 -keep class com.amap.api.fence.**{*;}
 -keep class com.autonavi.aps.amapapi.model.**{*;}
 #高德 搜索
 -keep class com.amap.api.services.**{*;}
 #高德 2D地图
 -keep class com.amap.api.maps2d.**{*;}
 -keep class com.amap.api.mapcore2d.**{*;}
 #高德 导航
 -keep class com.amap.api.navi.**{*;}
 -keep class com.autonavi.**{*;}

 #uc广告联盟
 -keep class com.iinmobi.adsdk.** {*;}

 #xutils
 -keepattributes Exceptions,InnerClasses,Signature,Deprecated,*Annotation*,Synthetic,EnclosingMethod

 -keep public class org.xutils.** {
    public protected *;
 }
 -keep public interface org.xutils.** {
    public protected *;
 }
 -keepclassmembers class * extends org.xutils.** {
    public protected *;
 }
 -keepclassmembers class * extends org.xutils.http.RequestParams {*;}
 -keepclassmembers class * {
   void *(android.view.View);
   *** *Click(...);
   *** *Event(...);
 }

 #
 -dontskipnonpubliclibraryclassmembers
 -dontskipnonpubliclibraryclasses
 -keep class locSDK_3.3.**{*;}
 -keep class mframework.**{*;}
 -keep class com.nineoldandroids.**{*;}
 -keep class m.framework.**{*;}
 -keep class com.nostra13.**{*;}
 -keep class ant.jar.**{*;}
 -keep class httpmime-4.0.1.jar.**{*;}
 -keep class photoview_library.jar.**{*;}
 -keep class SlideMenuLibray2.jar.**{*;}
 -keep class pl.droidsonroids.gif.GifIOException { *; }
 -keep class pl.droidsonroids.gif.GifError { *; }
 -keep class com.activeandroid.** {*;}
 -keep class com.loopj.android.http.** {*;}
 -keep class fr.castorflex.android.smoothprogressbar.** {*;}
 -keep class uk.co.senab.** {*;}
 -keep class java.lang.ref.** {*;}
 -keep class cn.jpush.** {*;}
 -keep class com.squareup.** {*;}
 -keep class com.nostra13.universalimageloader.** {*;}

 #忽略警告
 -dontwarn com.squareup.**


 -dontwarn android.support.v4.**
 -keep class android.support.v4.** { *; }
 -keep interface android.support.v4.app.** { *; }
 -keep public class * extends android.support.v4.**
 -keep public class * extends android.app.Fragment



 -keep public class * extends android.app.Activity
 -keep public class * extends android.app.Application
 -keep public class * extends android.app.Service
 -keep public class * extends android.content.BroadcastReceiver
 -keep public class * extends android.content.ContentProvider
 -keep public class * extends android.app.backup.BackupAgentHelper
 -keep public class * extends android.preference.Preference
 -keep public class com.android.vending.licensing.ILicensingService

 -keepclasseswithmembernames class * {
     native <methods>;
 }

 -keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet);
 }

 -keepclasseswithmembers class * {
     public <init>(android.content.Context, android.util.AttributeSet, int);
 }

 -keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
 }

 -keepclassmembers enum * {
     public static **[] values();
     public static ** valueOf(java.lang.String);
 }

 -keep class * implements android.os.Parcelable {
   public static final android.os.Parcelable$Creator *;
 }