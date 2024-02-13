package com.navod.networing.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
    private final static String TAG = RequestInterceptor.class.getName();
    private String token;
    public RequestInterceptor(String token){
        this.token = token;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Log.i(TAG, "Call: RequestInterceptor....");
        Request request = chain.request().newBuilder().header("Authorization", "Bearer " + token).build();
        return chain.proceed(request);
    }
}
