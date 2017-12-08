package com.allen.rxretrofitlib.download;

/**
 * 下载状态
 * Created by allen on 2017/12/8.
 */

public enum DownloadState {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    DownloadState(int state) {
        this.state = state;
    }
}
