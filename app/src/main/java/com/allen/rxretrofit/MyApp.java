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
        RxRetrofitApp.init(this, Constants.HTTP_RESUCCESS);
        RxRetrofitApp.setCacheDBName(Constants.CACHE_DB_NAME);
        RxRetrofitApp.setBaseUrl(Constants.BASE_URL);
    }
}
