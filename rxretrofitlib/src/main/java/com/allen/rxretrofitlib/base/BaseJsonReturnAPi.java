package com.allen.rxretrofitlib.base;

import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import org.json.JSONObject;

import rx.functions.Func1;

/**
 * 返回内容为Json对象
 * Created by allen on 2017/12/1.
 */

public abstract class BaseJsonReturnAPi<T> extends BaseApi implements Func1<JSONObject, T> {
    public BaseJsonReturnAPi(HttpOnNextListener listener, RxAppCompatActivity activity) {
        super(listener, activity);
    }

    public BaseJsonReturnAPi(HttpOnNextListener listener, RxFragment fragment) {
        super(listener, fragment);
    }

    @Override
    public T call(JSONObject jsonObject) {
        return (T) jsonObject;
    }
}
