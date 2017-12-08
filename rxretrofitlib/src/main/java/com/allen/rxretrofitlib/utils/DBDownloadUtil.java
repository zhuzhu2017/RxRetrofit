package com.allen.rxretrofitlib.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.cookie.DaoMaster;
import com.allen.rxretrofitlib.cookie.DaoSession;
import com.allen.rxretrofitlib.download.DownloadInfo;
import com.allen.rxretrofitlib.download.DownloadInfoDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * 断点续传工具类
 * 数据库工具类-geendao运用
 * Created by allen on 2017/12/8.
 */

public class DBDownloadUtil {
    //数据库名称
    private static String dbName = RxConstants.CACHE_DB_NAME;
    //GreenDao处理工具类
    private DaoMaster.DevOpenHelper openHelper;
    private Context context;
    private static DBDownloadUtil db;

    private DBDownloadUtil() {
        dbName = RxRetrofitApp.getCacheDBName();
        context = RxRetrofitApp.getApplication();
        openHelper = new DaoMaster.DevOpenHelper(context, dbName);
    }

    /*获取单例对象*/
    public static DBDownloadUtil getInstance() {
        if (db == null) {
            synchronized (DBDownloadUtil.class) {
                if (db == null) {
                    db = new DBDownloadUtil();
                }
            }
        }
        return db;
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
     * @param info 需要保存的数据对象
     */
    public void saveDownloadInfo(DownloadInfo info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadInfoDao downloadInfoDao = daoSession.getDownloadInfoDao();
        downloadInfoDao.insert(info);
    }

    /**
     * 删除数据对象
     *
     * @param info 需要删除的数据对象
     */
    public void deleteDownloadInfo(DownloadInfo info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadInfoDao downloadInfoDao = daoSession.getDownloadInfoDao();
        downloadInfoDao.delete(info);
    }

    /**
     * 更新数据对象
     *
     * @param info 新的数据对象
     */
    public void updateDownloadInfo(DownloadInfo info) {
        DaoMaster daoMaster = new DaoMaster(getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadInfoDao downloadInfoDao = daoSession.getDownloadInfoDao();
        downloadInfoDao.update(info);
    }

    /**
     * 根据缓存url查询cookie
     *
     * @param id 查询id
     * @return 查询到的数据对象
     */
    public DownloadInfo queryDownloadInfoById(long id) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadInfoDao downloadInfoDao = daoSession.getDownloadInfoDao();
        QueryBuilder<DownloadInfo> builder = downloadInfoDao.queryBuilder();
        builder.where(DownloadInfoDao.Properties.Id.eq(id));
        List<DownloadInfo> list = builder.list();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    /**
     * 根据存储名称查询数据
     *
     * @param name 存储名称
     * @return 查询到的数据对象
     */
    public DownloadInfo queryDownloadInfoByName(String name) {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadInfoDao downloadInfoDao = daoSession.getDownloadInfoDao();
        QueryBuilder<DownloadInfo> builder = downloadInfoDao.queryBuilder();
        builder.where(DownloadInfoDao.Properties.SaveName.eq(name));
        List<DownloadInfo> list = builder.list();
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
    public List<DownloadInfo> queryAllDownloadInfo() {
        DaoMaster daoMaster = new DaoMaster(getReadableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        DownloadInfoDao downloadInfoDao = daoSession.getDownloadInfoDao();
        return downloadInfoDao.queryBuilder().list();
    }

}
