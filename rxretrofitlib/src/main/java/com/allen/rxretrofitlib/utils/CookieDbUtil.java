package com.allen.rxretrofitlib.utils;

import android.content.Context;

import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.cookie.DaoMaster;

/**
 * 缓存处理工具类
 * 采用GreenDao
 * Created by allen on 2017/12/1.
 */

public class CookieDbUtil {
    //缓存数据库名称
    private static String dbName = RxConstants.CACHE_DB_NAME;
    //GreenDao处理工具对象
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private static CookieDbUtil dbUtil;

    private CookieDbUtil() {
        dbName = RxRetrofitApp.getCacheDBName();
        context = RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName);
    }

    /**
     * 获取缓存工具类单例对象
     *
     * @return 工具类单例对象
     */
    public static CookieDbUtil getInstance() {
        if (dbUtil == null) {
            synchronized (CookieDbUtil.class) {
                if (dbUtil == null) {
                    dbUtil = new CookieDbUtil();
                }
            }
        }
        return dbUtil;
    }

}
