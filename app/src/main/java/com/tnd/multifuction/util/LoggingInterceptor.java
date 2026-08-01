package com.tnd.multifuction.util;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Interceptor;

import java.io.IOException;

// 自定义拦截器
public class LoggingInterceptor implements Interceptor {
    public static String TAG = "LoggingInterceptor";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        // 获取请求
        Request request = chain.request();

        // 打印请求方法和URL
        Log.d(TAG, request.method() + " " + request.url());

        // 可选：打印请求头
        for (String headerName : request.headers().names()) {
            Log.d(TAG, headerName + ": " + request.header(headerName));
        }

        // 可选：打印请求体（如果存在）
        if (request.body() != null) {
            Log.d(TAG, request.body().toString());
        }

        // 继续请求，获取响应
        Response response = chain.proceed(request);

        // 打印响应码和 word
        Log.d(TAG, "Response Code: " + response.code());

        // 可选：打印响应头
        for (String headerName : response.headers().names()) {
            System.out.println(headerName + ": " + response.header(headerName));
        }

        // 返回响应
        return response;
    }
}
