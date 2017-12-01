package com.allen.rxretrofitlib;

import android.app.Application;

/**
 * 供引用方初始化操作类
 * Created by allen on 2017/11/30.
 */

public class RxRetrofitApp {

    /*Application对象*/
    private static Application application;
    /*设置是否是调试模式*/
    private static boolean debug;
    /*联网请求成功状态码*/
    public static int REQUEST_SUCCESS = 1100;
    /*缓存数据库名称*/
    private static String cacheDBName;

    public static void init(Application app) {
        setApplication(app);
    }

    public static void init(Application app, boolean debug) {
        setApplication(app);
        setDebug(debug);
    }

    public static Application getApplication() {
        return application;
    }

    private static void setApplication(Application application) {
        RxRetrofitApp.application = application;
    }

    public static boolean isDebug() {
        return debug;
    }

    private static void setDebug(boolean debug) {
        RxRetrofitApp.debug = debug;
    }

    public static int getRequestSuccessCode() {
        return REQUEST_SUCCESS;
    }

    public static void setRequestSuccessCode(int requestSuccess) {
        REQUEST_SUCCESS = requestSuccess;
    }

    public static String getCacheDBName() {
        return cacheDBName;
    }

    public static void setCacheDBName(String cacheDBName) {
        RxRetrofitApp.cacheDBName = cacheDBName;
    }
}
