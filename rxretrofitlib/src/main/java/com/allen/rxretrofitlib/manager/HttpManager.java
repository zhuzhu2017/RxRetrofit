package com.allen.rxretrofitlib.manager;

import android.text.TextUtils;

import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.base.BaseApi;
import com.allen.rxretrofitlib.cookie.CookieInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;

/**
 * 网络请求管理器
 * Created by allen on 2017/11/30.
 */

public class HttpManager {
    private volatile static HttpManager INSTANCE;

    private HttpManager() {
    }

    /**
     * 创建HttpManager单例对象
     *
     * @return 单例对象
     */
    public static HttpManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 网络请求
     *
     * @param baseApi 网络请求设置和API参数
     */
    public void httpRequest(BaseApi baseApi) {
        if (baseApi == null) return;
         /*手动创建一个OkHttpClient并设置超时时间*/
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(baseApi.getConnTimeout(), TimeUnit.SECONDS);
        //添加自定义拦截器
        Interceptor interceptor = baseApi.getInterceptor();
        if (interceptor != null) builder.addInterceptor(interceptor);
        //添加缓存处理拦截器
        builder.addInterceptor(new CookieInterceptor(baseApi.isCacheNeeded(), baseApi.getUrl(),baseApi.getCharset()));
        String requestSource = baseApi.getRequestSource();
        if (TextUtils.equals(requestSource, RxConstants.TYPE_ACTIVITY)) {

        } else if (TextUtils.equals(requestSource, RxConstants.TYPE_FRAGMENT)) {

        } else {

        }
    }

}
