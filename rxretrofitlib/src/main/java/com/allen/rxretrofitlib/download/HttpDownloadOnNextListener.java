package com.allen.rxretrofitlib.download;

/**
 * Created by allen on 2017/12/8.
 */

public abstract class HttpDownloadOnNextListener<T> {
    /**
     * 成功后的回调
     *
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 下载完成
     */
    public abstract void onComplete();

    /**
     * 下载进度回调
     *
     * @param readLength  下载长度
     * @param totalLength 总长度
     */
    public abstract void onProgress(long readLength, long totalLength);

    /**
     * 异常回调
     *
     * @param e
     */
    public void onError(Throwable e) {
    }

    /**
     * 暂停下载
     */
    public void onPause() {
    }

    /**
     * 停止下载，销毁
     */
    public void onStop() {
    }

}
