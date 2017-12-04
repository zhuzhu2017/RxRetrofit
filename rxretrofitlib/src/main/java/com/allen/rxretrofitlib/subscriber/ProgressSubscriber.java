package com.allen.rxretrofitlib.subscriber;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.base.BaseApi;
import com.allen.rxretrofitlib.cookie.CookieResult;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.allen.rxretrofitlib.utils.CookieDbUtil;
import com.allen.rxretrofitlib.utils.NetworkUtil;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.lang.ref.SoftReference;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by allen on 2017/12/2.
 */

public class ProgressSubscriber<T> extends Subscriber<T> {
    //是否显示加载框
    private boolean showProgress = true;
    //软引用回调接口
    private SoftReference<HttpOnNextListener> listener;
    //软引用防止内存泄漏
    private SoftReference<RxAppCompatActivity> mActivity;
    private SoftReference<RxFragment> mFragment;
    //请求的封装数据
    private BaseApi baseApi;
    //加载框
    private AlertDialog dialog;

    public ProgressSubscriber(BaseApi baseApi, RxAppCompatActivity activity) {
        this.baseApi = baseApi;
        this.showProgress = baseApi.isShowProgress();
        this.listener = baseApi.getListener();
        this.mActivity = new SoftReference<>(activity);
        if (baseApi.getDialog() != null) {
            this.dialog = baseApi.getDialog();
        }
        if (baseApi.isShowProgress()) {
            //初始化加载框
            initProgressDialog(baseApi.isCanCancelProgress());
        }
    }

    public ProgressSubscriber(BaseApi baseApi, RxFragment fragment) {
        this.baseApi = baseApi;
        this.showProgress = baseApi.isShowProgress();
        this.listener = baseApi.getListener();
        this.mFragment = new SoftReference<>(fragment);
        if (baseApi.getDialog() != null) {
            this.dialog = baseApi.getDialog();
        }
        if (baseApi.isShowProgress()) {
            //初始化加载框
            initProgressDialog(baseApi.isCanCancelProgress());
        }
    }

    /**
     * 初始化加载框
     *
     * @param canCancelProgress 是否能点击取消
     */
    private void initProgressDialog(boolean canCancelProgress) {
        Context context;
        if (mActivity != null) {
            context = mActivity.get();
        } else if (mFragment != null) {
            context = mFragment.get().getContext();
        } else {
            context = null;
        }
        if (dialog != null) {
            initDialogSetting(canCancelProgress);
        } else {
            if (context == null) return;
            dialog = new ProgressDialog(context);
            initDialogSetting(canCancelProgress);
        }
    }

    /**
     * 初始化Dialog设置
     *
     * @param canCancelProgress 是否能点击取消
     */
    private void initDialogSetting(boolean canCancelProgress) {
        dialog.setCancelable(canCancelProgress);
        if (canCancelProgress) {
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    //触发联网取消操作
                    if (listener != null && listener.get() != null) {
                        listener.get().onCancel();
                    }
                    //取消Subscriber订阅
                    onCancelProgress();
                }
            });
        }
    }

    /**
     * 取消加载框的时候取消对Observable的订阅，同时也取消了Http请求
     */
    private void onCancelProgress() {
        if (!this.isUnsubscribed()) {
            this.unsubscribe();
        }
    }

    /**
     * 显示加载框
     */
    private void showProgressDialog() {
        if (!showProgress) return;
        Context context;
        if (mActivity != null) {
            context = mActivity.get();
        } else if (mFragment != null) {
            context = mFragment.get().getContext();
        } else {
            context = null;
        }
        if (dialog == null || context == null) return;
        if (!dialog.isShowing()) dialog.show();
    }

    /**
     * 关闭加载框
     */
    private void dismissProgressDialog() {
        if (!showProgress) return;
        if (dialog != null && dialog.isShowing()) dialog.dismiss();
    }

    /**
     * 订阅开始时调用
     * 显示加载框
     */
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
        //处理缓存
        if (baseApi.isCacheNeeded() && NetworkUtil.isNetworkAvailable(RxRetrofitApp.getApplication())) {
            //获取缓存对象
            CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieByUrl(baseApi.getUrl());
            if (cookieResult != null) {
                //有缓存，检查缓存时间，看缓存是否可用
                long time = (System.currentTimeMillis() - cookieResult.getTime()) / 1000;
                if (time < baseApi.getCookieNetWorkTime()) {    //缓存没有过时
                    if (listener != null && listener.get() != null) {
                        listener.get().onCacheNext(cookieResult.getResult());
                    }
                    onCompleted();
                    unsubscribe();
                }
            }
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(final Throwable e) {
        dismissProgressDialog();
        //缓存处理
        if (baseApi.isCacheNeeded()) {
            Observable.just(baseApi.getUrl())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            dealError(e);
                        }

                        @Override
                        public void onNext(String s) {
                            //获取缓存数据
                            CookieResult cookieResult = CookieDbUtil.getInstance().queryCookieByUrl(s);
                            if (cookieResult == null) {
                                dealError(e);
                                return;
                            }
                            long time = ((System.currentTimeMillis()) - cookieResult.getTime()) / 1000;
                            if (time < baseApi.getCookieNetWorkTime()) {
                                if (listener != null && listener.get() != null) {
                                    listener.get().onCacheNext(cookieResult.getResult());
                                }
                            } else {
                                //删除过期缓存
                                CookieDbUtil.getInstance().deleteCookie(cookieResult);
                                dealError(e);
                            }
                        }
                    });
        } else {
            dealError(e);
        }
    }

    @Override
    public void onNext(T t) {
        if (listener != null && listener.get() != null) {
            listener.get().onNext(t);
        }
    }

    /**
     * 处理错误信息
     *
     * @param e 异常
     */
    private void dealError(Throwable e) {
        //触发错误回调
        if (listener != null && listener.get() != null) {
            listener.get().onError(e);
        }
    }

}
