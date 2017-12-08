package com.allen.rxretrofitlib.download;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

/**
 * service统一接口数据
 * Created by allen on 2017/12/8.
 */

public interface HttpDownloadService {
    /**
     * 大文件断点续传
     *
     * @param start 起始下载点
     * @param url   下载Url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> breakpointDownload(@Header("RANGE") String start, @Url String url);

    /**
     * @param url 下载url
     * @return
     */
    @Streaming
    @GET
    Observable<ResponseBody> commonDownload(@Url String url);

}
