package com.allen.rxretrofitlib.base;

import com.allen.rxretrofitlib.exception.HttpResultException;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.lang.ref.SoftReference;

import retrofit2.Retrofit;
import rx.Observable;
import rx.functions.Func1;

/**
 * api基类，所有调用的api均需要集成该父类
 * Created by allen on 2017/11/30.
 */

public abstract class BaseApi<T> implements Func1<BaseResultEntity<T>, T> {
    /*rx与activity绑定，管理生命周期，软引用防止内存泄漏*/
    private SoftReference<RxAppCompatActivity> rxAppCompatActivity;
    /*rx与Fragment绑定，管理生命周期，软引用防止内存泄露*/
    private SoftReference<RxFragment> rxFragment;
    /*请求结果回调*/
    private SoftReference<HttpOnNextListener> listener;
    /*是否能点击取消加载框*/
    private boolean canCancelProgress;
    /*是否显示加载框*/
    private boolean showProgress;
    /*是否需要缓存处理*/
    private boolean cacheNeeded;
    /*基础URL*/
    private String baseUrl;
    /*方法名称，对应baseUrl后面的方法名称*/
    private String methodName;
    /*联网超时时间*/
    private int connTimeout = 10;   //默认10秒
    /*有网络情况下本地缓存时间默认60秒，60秒后重新请求接口并保存*/
    private int cookieNetWorkTime = 60;
    /*无网络情况下本地缓存默认30天，30天后失效，并清空本地保存的数据*/
    private int cookieNoNetWorkTime = 30 * 24 * 60 * 60;
    /*失败后重试的次数*/
    private int retryCount = 1;
    /*失败后retry的延迟时间--过去对应时间后再走一次请求*/
    private int retryDelay = 100;
    /*失败后retry叠加延迟--失败一次延迟请求时间叠加一次*/
    private int retryIncreaseDelay = 10;
    /*缓存URL--可手动设置*/
    private String cacheUrl;

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
        //默认需要缓存
        setCacheNeeded(true);
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
        setCacheNeeded(true);
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

    public HttpOnNextListener getListener() {
        if (listener == null) return null;
        return listener.get();
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

    public String getCacheUrl() {
        return cacheUrl;
    }

    public void setCacheUrl(String cacheUrl) {
        this.cacheUrl = cacheUrl;
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

    /**
     * 返回结果的统一处理（处理异常信息）
     *
     * @param tBaseResultEntity 统一格式返回数据
     * @return 返回的数据对象
     */
    @Override
    public T call(BaseResultEntity<T> tBaseResultEntity) {
        if (tBaseResultEntity.getCode() != 1100) {
            throw new HttpResultException(tBaseResultEntity.getMsg());
        }
        return tBaseResultEntity.getData();
    }
}
