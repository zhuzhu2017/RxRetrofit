package com.allen.rxretrofitlib.base;

import com.allen.rxretrofitlib.RxRetrofitApp;
import com.allen.rxretrofitlib.exception.HttpResultException;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import rx.functions.Func1;

/**
 * 返回结果为固定格式的Entity数据对象
 * Created by allen on 2017/12/1.
 */

public abstract class BaseEntityReturnApi<T> extends BaseApi implements Func1<BaseResultEntity<T>, T> {
    public BaseEntityReturnApi(HttpOnNextListener listener, RxAppCompatActivity activity) {
        super(listener, activity);
    }

    public BaseEntityReturnApi(HttpOnNextListener listener, RxFragment fragment) {
        super(listener, fragment);
    }

    @Override
    public T call(BaseResultEntity<T> tBaseResultEntity) {
        if (tBaseResultEntity.getCode() != RxRetrofitApp.REQUEST_SUCCESS) {
            throw new HttpResultException(tBaseResultEntity.getMsg());
        }
        return tBaseResultEntity.getData();
    }
}
