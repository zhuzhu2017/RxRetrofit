package com.allen.rxretrofitlib.download;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 下载信息
 * Created by allen on 2017/12/6.
 */
@Entity
public class DownloadInfo {
    @Id
    private long id;
    /*存储位置*/
    private String savePath;
    /*存储名称*/
    private String saveName;
    /*文件总长度*/
    private long totalLength;
    /*下载长度*/
    private long readLength;
    /*下载服务*/
    @Transient
    private HttpDownloadService service;
    /*下载监听*/
    @Transient
    private HttpDownloadOnNextListener listener;
    /*超时设置*/
    private int connectonTime = 5;
    /*state状态数据库保存*/
    private int stateInt;
    /*url*/
    private String url;

    public DownloadInfo(String url, HttpDownloadOnNextListener listener) {
        setUrl(url);
        setListener(listener);
    }

    public DownloadInfo(String url) {
        setUrl(url);
    }

    @Generated(hash = 1625918265)
    public DownloadInfo(long id, String savePath, String saveName, long totalLength,
                        long readLength, int connectonTime, int stateInt, String url) {
        this.id = id;
        this.savePath = savePath;
        this.saveName = saveName;
        this.totalLength = totalLength;
        this.readLength = readLength;
        this.connectonTime = connectonTime;
        this.stateInt = stateInt;
        this.url = url;
    }

    @Generated(hash = 327086747)
    public DownloadInfo() {
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getSavePath() {
        return this.savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getSaveName() {
        return this.saveName;
    }

    public void setSaveName(String saveName) {
        this.saveName = saveName;
    }

    public long getTotalLength() {
        return this.totalLength;
    }

    public void setTotalLength(long totalLength) {
        this.totalLength = totalLength;
    }

    public long getReadLength() {
        return this.readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public int getConnectonTime() {
        return this.connectonTime;
    }

    public void setConnectonTime(int connectonTime) {
        this.connectonTime = connectonTime;
    }

    public DownloadState getState() {
        switch (getStateInt()) {
            case 0:
                return DownloadState.START;
            case 1:
                return DownloadState.DOWN;
            case 2:
                return DownloadState.PAUSE;
            case 3:
                return DownloadState.STOP;
            case 4:
                return DownloadState.ERROR;
            case 5:
            default:
                return DownloadState.FINISH;
        }
    }

    public void setState(DownloadState state) {
        setStateInt(state.getState());
    }

    public int getStateInt() {
        return this.stateInt;
    }

    public void setStateInt(int stateInt) {
        this.stateInt = stateInt;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public HttpDownloadService getService() {
        return service;
    }

    public void setService(HttpDownloadService service) {
        this.service = service;
    }

    public HttpDownloadOnNextListener getListener() {
        return listener;
    }

    public void setListener(HttpDownloadOnNextListener listener) {
        this.listener = listener;
    }
}
