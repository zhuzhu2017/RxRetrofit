package com.allen.rxretrofitlib.manager;

import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.download.DownloadInfo;
import com.allen.rxretrofitlib.download.DownloadState;
import com.allen.rxretrofitlib.download.HttpDownloadService;
import com.allen.rxretrofitlib.download.listener.DownloadInterceptor;
import com.allen.rxretrofitlib.subscriber.ProgressDownloadSubscriber;
import com.allen.rxretrofitlib.utils.DBDownloadUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 下载管理器
 * Created by allen on 2017/12/6.
 */

public class HttpDownloadManager {
    /*记录下载数据*/
    private Set<DownloadInfo> downloadInfos;
    /*记录回调*/
    private HashMap<String, ProgressDownloadSubscriber> subMap;
    /*数据库工具*/
    private DBDownloadUtil dbUtil;
    /*单例对象*/
    private volatile static HttpDownloadManager INSTANCE;

    /*私有构造器*/
    private HttpDownloadManager() {
        downloadInfos = new HashSet<>();
        subMap = new HashMap<>();
        dbUtil = DBDownloadUtil.getInstance();
    }

    /*获取单例对象*/
    public static HttpDownloadManager getInstance() {
        if (INSTANCE == null) {
            synchronized (HttpDownloadManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new HttpDownloadManager();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * 开始下载
     *
     * @param info 下载信息
     */
    public void startDownload(final DownloadInfo info) {
        if (info == null) return;
        if (subMap.get(info.getUrl()) != null) {   //正在下载
            subMap.get(info.getUrl()).setDownloadInfo(info);
            return;
        }
        /*添加回调处理类*/
        ProgressDownloadSubscriber subscriber = new ProgressDownloadSubscriber(info);
        /*添加回调记录*/
        subMap.put(info.getUrl(), subscriber);
        /*HttpService对象创建*/
        HttpDownloadService service;
        if (downloadInfos.contains(info)) {
            service = info.getService();
        } else {
            //创建下载拦截器
            DownloadInterceptor interceptor = new DownloadInterceptor(subscriber);
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(info.getConnectonTime(), TimeUnit.SECONDS);
            builder.addInterceptor(interceptor);

            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(RxRetrofitApp.getBaseUrl())
                    .build();
            service = retrofit.create(HttpDownloadService.class);
            info.setService(service);
            downloadInfos.add(info);
        }
        //开始下载
        service.breakpointDownload(info.getReadLength() + "", info.getUrl())
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                /*读取写入文件*/
                .map(new Func1<ResponseBody, DownloadInfo>() {
                    @Override
                    public DownloadInfo call(ResponseBody responseBody) {
                        writeToCache(responseBody, new File(info.getSavePath()), info);
                        return info;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    /**
     * 写入文件
     *
     * @param responseBody 请求返回体
     * @param file         保存的文件
     * @param info         下载信息
     */
    private void writeToCache(ResponseBody responseBody, File file, DownloadInfo info) {
        try {
            InputStream is = null;
            RandomAccessFile randomAccessFile = null;
            FileChannel fileChannel = null;
            try {
                if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
                long allLength = 0 == info.getTotalLength() ? responseBody.contentLength() : info.getTotalLength() + responseBody.contentLength();
                is = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                fileChannel = randomAccessFile.getChannel();
                MappedByteBuffer byteBuffer = fileChannel.map(FileChannel.MapMode.READ_WRITE, info.getReadLength(), allLength - info.getReadLength());
                byte[] buffer = new byte[1024 * 4];
                int len;
                while ((len = is.read(buffer)) != -1) {
                    byteBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (is != null) is.close();
                if (randomAccessFile != null) randomAccessFile.close();
                if (fileChannel != null) fileChannel.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 停止下载
     *
     * @param info 下载信息
     */
    public void stopDownload(DownloadInfo info) {
        if (info == null) return;
        info.setState(DownloadState.STOP);
        info.getListener().onStop();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownloadSubscriber subscriber = subMap.get(info.getUrl());
            subscriber.unsubscribe();
            subMap.remove(info.getUrl());
        }
        dbUtil.saveDownloadInfo(info);
    }

    /**
     * 暂停下载
     *
     * @param info 下载信息
     */
    public void pauseDownload(DownloadInfo info) {
        if (info == null) return;
        info.setState(DownloadState.PAUSE);
        info.getListener().onPause();
        if (subMap.containsKey(info.getUrl())) {
            ProgressDownloadSubscriber subscriber = subMap.get(info.getUrl());
            subscriber.unsubscribe();
            subMap.remove(info.getUrl());
        }
        dbUtil.updateDownloadInfo(info);
    }

    /**
     * 停止所有下载
     */
    public void stopAllDownload() {
        for (DownloadInfo info :
                downloadInfos) {
            stopDownload(info);
        }
        subMap.clear();
        downloadInfos.clear();
    }

    /**
     * 暂停发所有下载
     */
    public void pauseAllDownload() {
        for (DownloadInfo info :
                downloadInfos) {
            pauseDownload(info);
        }
        subMap.clear();
        downloadInfos.clear();
    }

    public Set<DownloadInfo> getDownloadInfos() {
        return downloadInfos;
    }

    /**
     * 删除下载数据
     *
     * @param info
     */
    public void removeDownloadData(DownloadInfo info) {
        subMap.remove(info.getUrl());
        downloadInfos.remove(info);
    }

}
