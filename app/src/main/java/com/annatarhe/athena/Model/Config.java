package com.annatarhe.athena.Model;

import android.util.Log;

import com.annatarhe.athena.BuildConfig;
import com.apollographql.apollo.ApolloClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Annatarhe on 9/22/2017.
 *
 * @author AnnatarHe
 * @email iamhele1994@gmail.com
 */



public class Config {
//    public static volatile String token = "6|66170ced70baf97ac5866193ad05fed7";
//    6|66170ced70baf97ac5866193ad05fed7
    public static int userID = -1;
    public static volatile String token = "";

//    private static String serverUrl = "https://api.dbg.annatarhe.com/graphql/v1";
    private static String serverUrl = BuildConfig.DEBUG ?
            "http://192.168.111.185:9009/graphql/v1" :
            "https://api.dbg.annatarhe.com/graphql/v1";

    public static ApolloClient getApolloClient() {

        Log.i("token", Config.token);

        OkHttpClient okHttp = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request original = chain.request();
                        Request request = original.newBuilder()
                                .removeHeader("content-type")
                                .header("content-type", "application/json")
                                .header("athena-token", Config.token)
                                .build();
                        Log.i("http", request.toString());
                        return chain.proceed(request);
                    }
                }).build();

        return ApolloClient.builder()
                .serverUrl(serverUrl)
                .okHttpClient(okHttp)
                .build();
    }
}
