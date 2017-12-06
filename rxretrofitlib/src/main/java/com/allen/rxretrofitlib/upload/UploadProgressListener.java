package com.allen.rxretrofitlib.upload;

/**
 * 上传进度回调监听
 * Created by allen on 2017/12/6.
 */

public interface UploadProgressListener {

    /**
     * 上传进度
     *
     * @param currentBytesCount 当前上传进度
     * @param totalBytesCount   上传文件总大小
     */
    void onProgress(long currentBytesCount, long totalBytesCount);

}
