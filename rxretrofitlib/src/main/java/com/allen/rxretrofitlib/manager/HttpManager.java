package com.allen.rxretrofitlib.manager;

import android.text.TextUtils;

import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.base.BaseApi;
import com.allen.rxretrofitlib.base.BaseEntityReturnApi;
import com.allen.rxretrofitlib.base.BaseJsonReturnAPi;
import com.allen.rxretrofitlib.conberter.JsonConverterFactory;
import com.allen.rxretrofitlib.cookie.CookieInterceptor;
import com.allen.rxretrofitlib.exception.RetryWhenNetworkException;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.allen.rxretrofitlib.subscriber.ProgressSubscriber;
import com.trello.rxlifecycle.LifecycleTransformer;
import com.trello.rxlifecycle.android.ActivityEvent;
import com.trello.rxlifecycle.android.FragmentEvent;

import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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
        builder.addInterceptor(new CookieInterceptor(baseApi.isCacheNeeded(), baseApi.getUrl(), baseApi.getCharset()));
        //获取网络请求页面来源
        String requestSource = baseApi.getRequestSource();
        Retrofit retrofit;
        if (baseApi instanceof BaseJsonReturnAPi) {
            //创建Retrofit对象
            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(JsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseApi.getBaseUrl())
                    .build();
        } else {
            //创建Retrofit对象
            retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(baseApi.getBaseUrl())
                    .build();
        }
        //RxJava处理
        ProgressSubscriber subscriber;
        Observable observable;
        if (TextUtils.equals(requestSource, RxConstants.TYPE_ACTIVITY)) {
            subscriber = new ProgressSubscriber(baseApi, baseApi.getRxAppCompatActivity());
            if (baseApi instanceof BaseJsonReturnAPi) {
                observable = getObservable(true, baseApi, retrofit,
                        baseApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE));
            } else {
                observable = getObservable(false, baseApi, retrofit,
                        baseApi.getRxAppCompatActivity().bindUntilEvent(ActivityEvent.PAUSE));
            }
        } else {
            subscriber = new ProgressSubscriber(baseApi, baseApi.getRxFragment());
            if (baseApi instanceof BaseJsonReturnAPi) {
                observable = getObservable(true, baseApi, retrofit,
                        baseApi.getRxFragment().bindUntilEvent(FragmentEvent.PAUSE));
            } else {
                observable = getObservable(false, baseApi, retrofit,
                        baseApi.getRxFragment().bindUntilEvent(FragmentEvent.PAUSE));
            }
        }
        //链接式对象返回
        HttpOnNextListener listener = baseApi.getListener();
        if (listener != null) {
            listener.onNext(observable);
        }
        observable.subscribe(subscriber);
    }

    /**
     * 根据请求源获取Observable对象
     *
     * @param isReturnJson 是否需要返回Json格式数据
     * @param baseApi      api基类
     * @param retrofit     Retrofit对象
     * @param transformer  变换器管理生命周期
     * @return Observable对象
     */
    private Observable getObservable(boolean isReturnJson, BaseApi baseApi,
                                     Retrofit retrofit, LifecycleTransformer<Object> transformer) {
        if (isReturnJson) {
            BaseJsonReturnAPi jsonReturnAPi = (BaseJsonReturnAPi) baseApi;
            return jsonReturnAPi.getObservable(retrofit)
                    /*失败后重新请求设置*/
                    .retryWhen(new RetryWhenNetworkException(jsonReturnAPi.getRetryCount(),
                            jsonReturnAPi.getRetryDelay(), jsonReturnAPi.getRetryIncreaseDelay()))
                    /*Observable对象转换，管理生命周期*/
                    .compose(transformer)
                    /*http请求线程*/
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    /*数据回调线程*/
                    .observeOn(AndroidSchedulers.mainThread())
                    /*返回结果判断*/
                    .map(jsonReturnAPi);
        }
        BaseEntityReturnApi entityReturnApi = (BaseEntityReturnApi) baseApi;
        return entityReturnApi.getObservable(retrofit)
                /*失败后重新请求设置*/
                .retryWhen(new RetryWhenNetworkException(entityReturnApi.getRetryCount(),
                        entityReturnApi.getRetryDelay(), entityReturnApi.getRetryIncreaseDelay()))
                /*Observable对象转换，管理生命周期*/
                .compose(transformer)
                /*http请求线程*/
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*数据回调线程*/
                .observeOn(AndroidSchedulers.mainThread())
                /*返回结果判断*/
                .map(entityReturnApi);
    }
}
