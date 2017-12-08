package com.allen.rxretrofitlib.subscriber;

import com.allen.rxretrofitlib.download.DownloadInfo;
import com.allen.rxretrofitlib.download.DownloadState;
import com.allen.rxretrofitlib.download.HttpDownloadOnNextListener;
import com.allen.rxretrofitlib.download.listener.DownloadProgressListener;
import com.allen.rxretrofitlib.manager.HttpDownloadManager;
import com.allen.rxretrofitlib.utils.DBDownloadUtil;

import java.lang.ref.SoftReference;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * 下载处理类Subscriber
 * Created by allen on 2017/12/8.
 */

public class ProgressDownloadSubscriber<T> extends Subscriber<T> implements DownloadProgressListener {

    /*弱引用结果回调*/
    private SoftReference<HttpDownloadOnNextListener> listener;
    /*下载的数据*/
    private DownloadInfo info;

    public ProgressDownloadSubscriber(DownloadInfo info) {
        this.listener = new SoftReference<>(info.getListener());
        this.info = info;
    }

    public void setDownloadInfo(DownloadInfo info) {
        this.listener = new SoftReference<>(info.getListener());
        this.info = info;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (listener != null && listener.get() != null) {
            listener.get().onStart();
        }
        info.setState(DownloadState.START);
    }

    @Override
    public void onCompleted() {
        if (listener != null && listener.get() != null) {
            listener.get().onComplete();
        }
        HttpDownloadManager.getInstance().removeDownloadData(info);
        info.setState(DownloadState.FINISH);
        DBDownloadUtil.getInstance().updateDownloadInfo(info);
    }

    @Override
    public void onError(Throwable e) {
        if (listener != null && listener.get() != null) {
            listener.get().onError(e);
        }
        HttpDownloadManager.getInstance().removeDownloadData(info);
        info.setState(DownloadState.ERROR);
        DBDownloadUtil.getInstance().updateDownloadInfo(info);
    }

    @Override
    public void onNext(T t) {
        if (listener != null && listener.get() != null) {
            listener.get().onNext(t);
        }
    }

    @Override
    public void update(long read, long count, boolean done) {
        if (info.getTotalLength() > count) {
            read = info.getTotalLength() - count + read;
        } else {
            info.setTotalLength(count);
        }
        info.setReadLength(read);
        /*UI更新回调，占用UI线程*/
        if (listener != null && listener.get() != null) {
            Observable.just(read)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (info.getState() == DownloadState.PAUSE || info.getState() == DownloadState.STOP)
                                return;
                            info.setState(DownloadState.DOWN);
                            listener.get().onProgress(aLong, info.getTotalLength());
                        }
                    });
        }
    }
}
