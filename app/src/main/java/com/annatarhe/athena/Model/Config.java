package com.annatarhe.athena.Model;

import com.annatarhe.athena.BuildConfig;
import com.apollographql.apollo.ApolloClient;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by Annatarhe on 9/22/2017.
 *
 * @author AnnatarHe
 * @email iamhele1994@gmail.com
 */



public class Config {
    public static String token = "";

    private static String serverUrl = BuildConfig.DEBUG ? "http://localhost:9000/graphql/v1" : "https://api.dbg.annatarhe.com/graphql/v1";

    public static ApolloClient getApolloClient() {
        return ApolloClient.builder()
                .serverUrl(serverUrl)
                .okHttpClient(new OkHttpClient.Builder().addNetworkInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        return chain.proceed(chain.request().newBuilder().header("athena-token", Config.token).build());
                    }
                }).build())
                .build();
    }
}
