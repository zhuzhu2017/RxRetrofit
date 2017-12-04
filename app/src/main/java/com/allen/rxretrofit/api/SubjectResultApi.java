package com.allen.rxretrofit.api;

import com.allen.rxretrofit.HttpPostService;
import com.allen.rxretrofit.entity.SubjectResult;
import com.allen.rxretrofitlib.RxConstants;
import com.allen.rxretrofitlib.base.BaseEntityReturnApi;
import com.allen.rxretrofitlib.base.BaseResultEntity;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by allen on 2017/8/24.
 */

public class SubjectResultApi extends BaseEntityReturnApi {

    //需要传递的参数
    private boolean all;

    public SubjectResultApi(HttpOnNextListener listener, RxAppCompatActivity rxAppCompatActivity) {
        super(listener, rxAppCompatActivity);
        setShowProgress(true);
        setCanCancelProgress(true);
        setCacheNeeded(true);
        setBaseUrl("https://www.izaodao.com/Api/");
        setMethodName("AppFiftyToneGraph/videoLink");
        setCookieNetWorkTime(10);
        setCookieNoNetWorkTime(24 * 60 * 60);
        setRequestSource(RxConstants.TYPE_ACTIVITY);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService httpPostService = retrofit.create(HttpPostService.class);
        return httpPostService.getAllVideoBy(isAll());
    }

    public boolean isAll() {
        return all;
    }

    public void setAll(boolean all) {
        this.all = all;
    }
}
