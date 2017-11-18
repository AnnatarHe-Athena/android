package com.annatarhe.athena.Utils;

/**
 * Created by iamhe on 11/18/17.
 */

public final class Utils {
    public static String getRealSrcLink(String url) {
        // if (process.env.NODE_ENV !== 'production') {
        //   return 'http://via.placeholder.com/350x150'
        // }
        return url.indexOf("http") == 0 ? url : "https://wx3.sinaimg.cn/bmiddle" + "/" + url;
    }
    public static String getRealSrcLink(String url, String type) {
        // if (process.env.NODE_ENV !== 'production') {
        //   return 'http://via.placeholder.com/350x150'
        // }
        return url.indexOf("http") == 0 ? url : "https://wx3.sinaimg.cn/" + type + "/" + url;
    }
}
