package com.allen.rxretrofitlib.cookie;

import com.allen.rxretrofitlib.utils.CookieDbUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 自定义缓存拦截器
 * Created by allen on 2017/12/1.
 */

public class CookieInterceptor implements Interceptor {
    //缓存处理工具类
    private CookieDbUtil dbUtil;
    //是否需要缓存处理
    private boolean cacheNeed;
    //缓存Url
    private String cacheUrl;

    public CookieInterceptor(boolean cacheNeeded, String cacheUrl) {
        this.cacheNeed = cacheNeeded;
        this.cacheUrl = cacheUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        return null;
    }
}
