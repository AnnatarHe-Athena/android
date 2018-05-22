package com.annatarhe.athena.Utils;

import android.util.Base64;

import java.io.UnsupportedEncodingException;

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
    public static String decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException e) {
        }
        return new String(valueDecoded);
    }
}
