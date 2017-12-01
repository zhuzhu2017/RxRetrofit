package com.allen.rxretrofitlib.cookie;

import com.allen.rxretrofitlib.utils.CookieDbUtil;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;

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
    //编码格式
    private Charset charset;

    public CookieInterceptor(boolean cacheNeeded, String cacheUrl, String charSet) {
        dbUtil = CookieDbUtil.getInstance();
        this.cacheNeed = cacheNeeded;
        this.cacheUrl = cacheUrl;
        charset = Charset.forName(charSet);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        //需要缓存处理的话在拦截器添加数据的保存和更新操作
        if (cacheNeed) {
            ResponseBody body = response.body();
            if (body != null) {
                //获取响应资源
                BufferedSource source = body.source();
                //设置最大缓存
                source.request(Integer.MAX_VALUE);
                Buffer buffer = source.buffer();
                //设置编码格式
                MediaType contentType = body.contentType();
                if (contentType == null) return response;
                Charset charSet = contentType.charset(charset);
                //获取网络响应内容
                String bodyString = buffer.clone().readString(charSet == null ? charset : charSet);
                //获取缓存
                CookieResult cookieResult = dbUtil.queryCookieByUrl(cacheUrl);
                //不存在则新建保存，存在则更新
                long time = System.currentTimeMillis();
                if (cookieResult == null) {
                    cookieResult = new CookieResult(null, cacheUrl, bodyString, time);
                    dbUtil.saveCookie(cookieResult);
                } else {
                    cookieResult.setResult(bodyString);
                    cookieResult.setTime(time);
                    dbUtil.updateCookie(cookieResult);
                }
            }
        }
        return response;
    }
}
