package com.tnd.multifuction.util;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class OAuthAuthenticator implements Authenticator {

    private String token;

    public OAuthAuthenticator(String token) {
        this.token = token;
    }

    @Override
    public Request authenticate(Route route, Response response) {
        if (response.request().header("Authorization") != null) {
            return null; // 如果认证头已经存在，则不进行二次认证
        }

        // 添加认证头
        Request.Builder requestBuilder = response.request().newBuilder();
        requestBuilder.addHeader("Authorization", "Bearer " + token);
        return requestBuilder.build();
    }
}
