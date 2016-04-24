//package com.hjhrq1991.tool.Util;
//
//import android.content.Context;
//import android.net.ConnectivityManager;
//import android.net.NetworkInfo;
//import android.telephony.TelephonyManager;
//
//import com.loopj.android.http.AsyncHttpClient;
//import com.loopj.android.http.AsyncHttpResponseHandler;
//import com.loopj.android.http.RequestParams;
//import com.qoocc.common.R;
//
//import org.apache.http.HttpEntity;
//
//public class HttpUtils {
//    private static AsyncHttpClient client = new AsyncHttpClient();
//
//    private HttpUtils() {
//
//
//    }
//
//    /**
//     * 判断网络是否连接
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isNetworkConnected(Context context) {
//        if (context != null) {
//            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
//                    .getSystemService(Context.CONNECTIVITY_SERVICE);
//            NetworkInfo mNetworkInfo = mConnectivityManager
//                    .getActiveNetworkInfo();
//            if (mNetworkInfo != null) {
//                if (mNetworkInfo.isAvailable()) {
//                    return true;
//                } else {
//                    return false;
//                }
//            } else {
//                return false;
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断网络是否可用
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isNetworkAvailable(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        if (cm == null) {
//        } else {//如果仅仅是用来判断网络连接
//            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
//            NetworkInfo[] info = cm.getAllNetworkInfo();
//            if (info != null) {
//                for (int i = 0; i < info.length; i++) {
//                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
//                        return true;
//                    }
//                }
//            }
//        }
//        return false;
//    }
//
//    /**
//     * 判断是WiFi或是sim卡网络
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isWifi(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
//        if (networkINfo != null
//                && networkINfo.getType() == ConnectivityManager.TYPE_WIFI) {
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * 判断是否3G网络
//     *
//     * @param context
//     * @return
//     */
//    public static boolean is3rdNetWork(Context context) {
//        ConnectivityManager cm = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkINfo = cm.getActiveNetworkInfo();
//        if (networkINfo != null
//                && networkINfo.getType() == ConnectivityManager.TYPE_MOBILE) {
//            return true;
//        }
//        return false;
//    }
//
//
//    /**
//     * 判断wifi是否打开
//     *
//     * @param context
//     * @return
//     */
//    public static boolean isWifiEnabled(Context context) {
//        ConnectivityManager mgrConn = (ConnectivityManager) context
//                .getSystemService(Context.CONNECTIVITY_SERVICE);
//        TelephonyManager mgrTel = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        return ((mgrConn.getActiveNetworkInfo() != null && mgrConn
//                .getActiveNetworkInfo().getState() == NetworkInfo.State.CONNECTED) || mgrTel
//                .getNetworkType() == TelephonyManager.NETWORK_TYPE_UMTS);
//    }
//
//    /**
//     * 组装请求URL
//     *
//     * @param stringID
//     * @return
//     */
//    public static String appendRequesturl(Context mContext, int... stringID) {
//        int[] ids = stringID;
//        StringBuffer sb = new StringBuffer();
//        sb.append(mContext.getString(R.string.host));
//
//        for (int resource : ids) {
//            sb.append(mContext.getString(resource));
//        }
//
//        return sb.toString();
//    }
//
//    public static void get(Context context, String url, RequestParams params,
//                           AsyncHttpResponseHandler handler) {
//        getAsyncHttpClient().get(context, url, params, handler);
//    }
//
//    public static void post(String url, RequestParams params,
//                            AsyncHttpResponseHandler handler) {
//        getAsyncHttpClient().post(url, params, handler);
//    }
//
//    public static void post(Context context, String url, RequestParams params,
//                            AsyncHttpResponseHandler handler) {
//        getAsyncHttpClient().post(context, url, params, handler);
//    }
//
//    public static void post(Context context, String url, HttpEntity entity, String contentType,
//                            AsyncHttpResponseHandler handler) {
//        getAsyncHttpClient().post(context, url, entity, contentType, handler);
//    }
//
//    public static void cancel(Context context, boolean mayInterruptIfRunning) {
//        getAsyncHttpClient().cancelRequests(context, mayInterruptIfRunning);
//    }
//
//
//    /**
//     * 下载文件
//     *
//     * @param context
//     * @param url
//     * @param handler
//     */
//    public static void download(Context context, String url, AsyncHttpResponseHandler handler) {
//        client.get(context, url, handler);
//    }
//
//    public static AsyncHttpClient getAsyncHttpClient() {
//
//        return client;
//    }
//}
