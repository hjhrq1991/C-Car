package com.hjhrq1991.tool.Util;

/**
 * Created by zhangchengneng on 2015/3/4.
 */
public class SearchInfoUtils {

    private static String extensions[] = {".com",".cn",".net",".org",".gov",".edu",".mil",".biz",
            ".name",".info",".mobi",".pro",".cc",".ws",".travel",".travel",".fm",".museum",
            ".int",".areo",".post",".rec",".asia", ".aa",".at",".au",".be",".ca",".dk",".fr",
            ".it",".jp",".nl","nz",".uk",".za"};

    public static String HTTP_START = "http://";

    public static boolean checkExtensions(String str){
        boolean flag = false;
        if(checkContains(str) && !checkStart(str)){
            flag = true;
        }
        return flag;
    }

    public static boolean checkStart(String str){
        boolean flag = false;
        for(String ext : extensions){
            if(str.startsWith(ext)){
                flag = true;
            }
        }
        return flag;
    }

    public static boolean checkContains(String str){
        boolean flag = false;
        for(String ext : extensions){
            if(str.contains(ext)){
                flag = true;
            }
        }
        return flag;
    }

}
