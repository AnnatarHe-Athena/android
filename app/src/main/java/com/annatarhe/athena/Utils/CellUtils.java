package com.annatarhe.athena.Utils;

public class CellUtils {

     // "large" ? "bmiddle"
    public static String getRealImageSrc(String url, String type) {
        String decodedUrl = Utils.decodeBase64(url);

        if (decodedUrl.startsWith("http")) {
            return decodedUrl;
        }

        if (!decodedUrl.startsWith("qn://")) {
            return String.format("https://wx3.sinaimg.cn/%s/%s", type, decodedUrl);

        }

        String qnBasedUrl = decodedUrl.replace("qn://", "https://cdn.annatarhe.com/");
        return qnBasedUrl + String.format("-%s.webp", type == "large" ? "copyrightDB" : "thumbnails");
    }
    public static String getRealImageSrc(String url) {
        String decodedUrl = Utils.decodeBase64(url);

        if (decodedUrl.startsWith("http")) {
            return decodedUrl;
        }

        if (!decodedUrl.startsWith("qn://")) {
            return String.format("https://wx3.sinaimg.cn/%s/%s", "bmiddle", decodedUrl);

        }

        String qnBasedUrl = decodedUrl.replace("qn://", "https://cdn.annatarhe.com/");
        return qnBasedUrl + "-thumbnails.webp";
    }


}
