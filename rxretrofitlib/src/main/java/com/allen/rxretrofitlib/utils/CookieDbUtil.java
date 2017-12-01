package com.allen.rxretrofitlib.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.cookie.CookieResult;
import com.allen.rxretrofitlib.cookie.CookieResultDao;
import com.allen.rxretrofitlib.cookie.DaoMaster;
import com.allen.rxretrofitlib.cookie.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

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

    /**
     * 获取可读数据库
     *
     * @return readable database
     */
    private SQLiteDatabase getReadableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        return openHelper.getReadableDatabase();
    }

    /**
     * 获取可写入数据库
     *
     * @return writable database
     */
    private SQLiteDatabase getWritableDatabase() {
        if (openHelper == null) {
            openHelper = new DaoMaster.DevOpenHelper(context, dbName);
        }
        return openHelper.getWritableDatabase();
    }


    /**
     * 保存数据对象
     *
     * @param cookieResult 需要保存的数据对象
     */
    public void saveCookie(CookieResult cookieResult) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao cookieResultDao = daoSession.getCookieResultDao();
        cookieResultDao.insert(cookieResult);
    }

    /**
     * 删除数据对象
     *
     * @param cookieResult 需要删除的数据对象
     */
    public void deleteCookie(CookieResult cookieResult) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao cookieResultDao = daoSession.getCookieResultDao();
        cookieResultDao.delete(cookieResult);
    }

    /**
     * 更新数据对象
     *
     * @param cookieResult 新的数据对象
     */
    public void updateCookie(CookieResult cookieResult) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao cookieResultDao = daoSession.getCookieResultDao();
        cookieResultDao.update(cookieResult);
    }

    /**
     * 根据缓存url查询cookie
     *
     * @param url 缓存url
     * @return 查询到的数据对象
     */
    public CookieResult queryCookieByUrl(String url) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao cookieResultDao = daoSession.getCookieResultDao();
        QueryBuilder<CookieResult> builder = cookieResultDao.queryBuilder();
        List<CookieResult> list = builder.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 查询所有数据对象
     *
     * @return 数据对象集合
     */
    public List<CookieResult> queryAllCookie() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        CookieResultDao cookieResultDao = daoSession.getCookieResultDao();
        return cookieResultDao.queryBuilder().list();
    }

}
