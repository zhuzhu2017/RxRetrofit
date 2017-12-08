package com.allen.rxretrofitlib.download.listener;

/**
 * 成功回调
 * Created by allen on 2017/12/8.
 */

public interface DownloadProgressListener {
    /**
     * 更新下载状态
     *
     * @param read
     * @param count
     * @param done
     */
    void update(long read, long count, boolean done);

}
