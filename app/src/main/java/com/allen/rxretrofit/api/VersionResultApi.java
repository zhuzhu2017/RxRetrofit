package com.allen.rxretrofit.api;

import com.allen.rxretrofit.HttpPostService;
import com.allen.rxretrofit.R;
import com.allen.rxretrofit.common.MyDialog;
import com.allen.rxretrofitlib.base.BaseEntityReturnApi;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.trello.rxlifecycle.components.support.RxAppCompatActivity;
import com.trello.rxlifecycle.components.support.RxFragment;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by allen on 2017/12/4.
 */

public class VersionResultApi extends BaseEntityReturnApi {

    private String versioncode;
    private String type;

    public String getVersioncode() {
        return versioncode;
    }

    public void setVersioncode(String versioncode) {
        this.versioncode = versioncode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public VersionResultApi(HttpOnNextListener listener, RxAppCompatActivity activity) {
        super(listener, activity);
    }

    public VersionResultApi(HttpOnNextListener listener, RxFragment fragment) {
        super(listener, fragment);
    }

    @Override
    public Observable getObservable(Retrofit retrofit) {
        HttpPostService postService = retrofit.create(HttpPostService.class);
        Map<String, String> params = new HashMap<>();
        params.put("versioncode", getVersioncode());
        params.put("type", getType());
        return postService.getVersionResult(params);
    }
}
