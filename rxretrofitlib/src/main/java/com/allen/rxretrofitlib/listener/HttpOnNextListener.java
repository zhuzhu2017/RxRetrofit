package com.allen.rxretrofitlib.listener;

import rx.Observable;

/**
 * 网络请求回调监听
 * Created by allen on 2017/11/30.
 */

public abstract class HttpOnNextListener<T> {
    /**
     * 成功后回调方法
     *
     * @param t 返回的请求对象
     */
    public abstract void onNext(T t);

    /**
     * 缓存回调
     *
     * @param cache 缓存内容
     */
    public void onCacheNext(String cache) {
    }

    /**
     * 成功后的Observable返回，扩展链式调用
     *
     * @param observable 返回的observable，供链式调用
     */
    public void onNext(Observable observable) {
    }

    /**
     * 错误回到
     *
     * @param e         异常
     * @param errorCode 错误码——供客户端自定义错误码处理
     */
    public void onError(Throwable e, int errorCode) {
    }

    /**
     * 取消回调
     */
    public void onCancel() {
    }

}
