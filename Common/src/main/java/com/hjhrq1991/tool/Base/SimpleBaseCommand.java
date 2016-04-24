//package com.hjhrq1991.tool.Base;
//
//import android.content.Context;
//
//import com.loopj.android.http.RequestParams;
//import com.loopj.android.http.TextHttpResponseHandler;
//import com.hjhrq1991.tool.Util.HttpUtils;
//
//import org.apache.http.Header;
//
///**
// * Created by yanghailong on 15/2/9.
// */
//public abstract class SimpleBaseCommand extends Command {
//
//    public SimpleBaseCommand(RequestParams requestParams) {
//        super(requestParams);
//    }
//
//    @Override
//    public void excute(Context mContext) {
//        String url = HttpUtils.appendRequesturl(mContext, getAppendUrlResource());
//        if (getHttyType() == HttpType.Get) {
//            HttpUtils.get(mContext, url, requestParams, new TextHttpResponseHandler() {
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//                    System.out.println("getPKList onFailure statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "], throwable = [" + throwable + "]");
//                    onCommandFailure(statusCode, headers, responseString, throwable);
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                    System.out.println("getPKList onSuccess statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "]");
//
//                    onCommandSuccess(statusCode, headers, responseString);
//
//                }
//
//                @Override
//                public void onProgress(int bytesWritten, int totalSize) {
//                    super.onProgress(bytesWritten, totalSize);
//                    onCommandProgress(bytesWritten, totalSize);
//                }
//            });
//        } else {
//            HttpUtils.post(mContext, url, requestParams, new TextHttpResponseHandler() {
//                @Override
//                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
//
//                    System.out.println("getPKList onFailure statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "], throwable = [" + throwable + "]");
//                    onCommandFailure(statusCode, headers, responseString, throwable);
//                }
//
//                @Override
//                public void onSuccess(int statusCode, Header[] headers, String responseString) {
//
//                    System.out.println("getPKList onSuccess statusCode = [" + statusCode + "], headers = [" + headers + "], responseString = [" + responseString + "]");
//
//                    onCommandSuccess(statusCode, headers, responseString);
//
//                }
//
//                @Override
//                public void onProgress(int bytesWritten, int totalSize) {
//                    super.onProgress(bytesWritten, totalSize);
//                    onCommandProgress(bytesWritten, totalSize);
//                }
//
//            });
//        }
//
//
//    }
//
//    /**
//     * 如果 需要实现进度条的，需要实现该方法
//     *
//     * @param bytesWritten
//     * @param totalSize
//     */
//    public void onCommandProgress(int bytesWritten, int totalSize) {
//
//    }
//
//    public abstract HttpType getHttyType();
//
//    public abstract int getAppendUrlResource();
//
//    public abstract void onCommandFailure(int statusCode, Header[] headers, String responseString, Throwable throwable);
//
//    public abstract void onCommandSuccess(int statusCode, Header[] headers, String responseString);
//
//}
