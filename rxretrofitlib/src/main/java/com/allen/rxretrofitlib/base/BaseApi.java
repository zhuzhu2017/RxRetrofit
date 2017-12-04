package com.allen.rxretrofitlib.base;

import android.app.AlertDialog;
import android.app.Dialog;

import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.lang.ref.SoftReference;

import okhttp3.Interceptor;
import retrofit2.Retrofit;
import rx.Observable;

/**
 * api基类
 * Created by allen on 2017/11/30.
 */

public abstract class BaseApi {
    /*rx与activity绑定，管理生命周期，软引用防止内存泄漏*/
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*rx与Fragment绑定，管理生命周期，软引用防止内存泄露*/
    private SoftReference<RxFragment> rxFragment;
    /*请求结果回调*/
    private SoftReference<HttpOnNextListener> listener;
    /*是否能点击取消加载框——默认点击不可取消*/
    private boolean canCancelProgress;
    /*是否显示加载框——默认显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理——默认不需要缓存处理*/
    private boolean cacheNeeded;
    /*基础URL——不能为空*/
    private String baseUrl;
    /*方法名称，对应baseUrl后面的方法名称，该变量仅用于设置缓存路径url*/
    private String methodName;
    /*联网超时时间——默认10s*/
    private int connTimeout = 10;   //默认10秒
    /*有网络情况下本地缓存时间默认60秒，60秒后重新请求接口并保存*/
    private int cookieNetWorkTime = 30;
    /*无网络情况下本地缓存默认30天，30天后失效，并清空本地保存的数据*/
    private int cookieNoNetWorkTime = 30 * 24 * 60 * 60;
    /*失败后重试的次数——默认0次*/
    private int retryCount = 0;
    /*失败后retry的延迟时间--过去对应时间后再走一次请求*/
    private int retryDelay = 0;
    /*失败后retry叠加延迟--失败一次延迟请求时间叠加一次*/
    private int retryIncreaseDelay = 0;
    /*缓存URL--可手动设置*/
    private String cacheUrl;
    /*获取网络请求来源——如果不设置默认Activity请求,Fragment请求必须设置否则会出错*/
    private String requestSource;
    /*设置网络请求拦截器——不设置的话默认不添加自定义拦截器*/
    private Interceptor interceptor;
    /*编码格式——默认UTF-8*/
    private String charset;
    /*自定义加载框——不设置的话默认系统加载框*/
    private Dialog dialog;
    /*返回json格式数据——不设置的话，默认BaseResultEntity返回*/
    private boolean isReturnJson;
    /*网络请求错误码——供客户端自定义错误信息处理*/
    private int errorCode;

    /**
     * 构造函数--RxAppCompatActivity
     *
     * @param listener 回调监听
     * @param activity 请求接口载体是Activity
     */
    public BaseApi(HttpOnNextListener listener, RxAppCompatActivity activity) {
        setRxAppCompatActivity(activity);
        setListener(listener);
        //默认显示加载框
        setShowProgress(true);
        //默认不需要缓存
        setCacheNeeded(false);
        //默认无法点击关闭加载框
        setCanCancelProgress(false);
        //默认UTF-8编码
        setCharset(RxConstants.CHARSET);
        //设置网络请求来源
        setRequestSource(RxConstants.TYPE_ACTIVITY);
        //设置基础Url
        setBaseUrl(RxRetrofitApp.getBaseUrl());
    }

    /**
     * 构造函数--RxFragment
     *
     * @param listener 回调监听
     * @param fragment 请求接口载体是Fragment
     */
    public BaseApi(HttpOnNextListener listener, RxFragment fragment) {
        setRxFragment(fragment);
        setListener(listener);
        //默认显示加载框
        setShowProgress(true);
        //默认需要缓存
        setCacheNeeded(false);
        //默认无法点击关闭加载框
        setCanCancelProgress(false);
        //默认UTF-8编码
        setCharset(RxConstants.CHARSET);
        //设置网络请求来源
        setRequestSource(RxConstants.TYPE_ACTIVITY);
        //设置基础Url
        setBaseUrl(RxRetrofitApp.getBaseUrl());
    }

    public RxAppCompatActivity getRxAppCompatActivity() {
        if (rxAppCompatActivity == null) return null;
        return rxAppCompatActivity.get();
    }

    public void setRxAppCompatActivity(RxAppCompatActivity rxAppCompatActivity) {
        this.rxAppCompatActivity = new SoftReference<>(rxAppCompatActivity);
    }

    public RxFragment getRxFragment() {
        if (rxFragment == null) return null;
        return rxFragment.get();
    }

    public void setRxFragment(RxFragment rxFragment) {
        this.rxFragment = new SoftReference<>(rxFragment);
    }

    public SoftReference<HttpOnNextListener> getListener() {
        return listener;
    }

    public void setListener(HttpOnNextListener listener) {
        this.listener = new SoftReference<>(listener);
    }

    public boolean isCanCancelProgress() {
        return canCancelProgress;
    }

    public void setCanCancelProgress(boolean canCancelProgress) {
        this.canCancelProgress = canCancelProgress;
    }

    public boolean isShowProgress() {
        return showProgress;
    }

    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    public boolean isCacheNeeded() {
        return cacheNeeded;
    }

    public void setCacheNeeded(boolean cacheNeeded) {
        this.cacheNeeded = cacheNeeded;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public int getConnTimeout() {
        return connTimeout;
    }

    public void setConnTimeout(int connTimeout) {
        this.connTimeout = connTimeout;
    }

    public int getCookieNetWorkTime() {
        return cookieNetWorkTime;
    }

    public void setCookieNetWorkTime(int cookieNetWorkTime) {
        this.cookieNetWorkTime = cookieNetWorkTime;
    }

    public int getCookieNoNetWorkTime() {
        return cookieNoNetWorkTime;
    }

    public void setCookieNoNetWorkTime(int cookieNoNetWorkTime) {
        this.cookieNoNetWorkTime = cookieNoNetWorkTime;
    }

    public int getRetryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public int getRetryDelay() {
        return retryDelay;
    }

    public void setRetryDelay(int retryDelay) {
        this.retryDelay = retryDelay;
    }

    public int getRetryIncreaseDelay() {
        return retryIncreaseDelay;
    }

    public void setRetryIncreaseDelay(int retryIncreaseDelay) {
        this.retryIncreaseDelay = retryIncreaseDelay;
    }

    public String getRequestSource() {
        return requestSource;
    }

    public void setRequestSource(String requestSource) {
        this.requestSource = requestSource;
    }

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
    }

    public Interceptor getInterceptor() {
        return interceptor;
    }

    public void setInterceptor(Interceptor interceptor) {
        this.interceptor = interceptor;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public void setDialog(Dialog dialog) {
        this.dialog = dialog;
    }

    public boolean isReturnJson() {
        return isReturnJson;
    }

    public void setReturnJson(boolean returnJson) {
        isReturnJson = returnJson;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    /**
     * 在没有手动设置缓存URL的情况下，简单拼接
     *
     * @return 拼接的url
     */
    public String getUrl() {
        if (null == getCacheUrl() || "".equals(getCacheUrl())) {
            return getBaseUrl() + getMethodName();
        }
        return getCacheUrl();
    }

    /**
     * 根据Retrofit对象设置Observable
     *
     * @param retrofit 创建的Retrofit对象
     * @return Observable对象
     */
    public abstract Observable getObservable(Retrofit retrofit);
}
