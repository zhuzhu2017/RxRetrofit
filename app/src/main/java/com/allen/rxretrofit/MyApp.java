package com.allen.rxretrofit;

import android.app.Application;

import com.allen.rxretrofitlib.RxRetrofitApp;

/**
 * 全局Application
 * Created by allen on 2017/11/30.
 */

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //初始化RxRetrofitLib
        RxRetrofitApp.init(this, Constants.HTTP_RESUCCESS);
        //设置数据库名称
        RxRetrofitApp.setCacheDBName(Constants.CACHE_DB_NAME);
        //设置网络请求基础Url
        RxRetrofitApp.setBaseUrl(Constants.BASE_URL);
    }
}
