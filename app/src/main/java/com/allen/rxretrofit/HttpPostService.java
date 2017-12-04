package com.allen.rxretrofit;

import com.allen.rxretrofit.entity.SubjectResult;
import com.allen.rxretrofit.entity.VersionResult;
import com.allen.rxretrofitlib.base.BaseResultEntity;

import java.util.List;
import java.util.Map;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import rx.Observable;


/**
 * 测试接口
 * Created by allen on 2017/8/24.
 */

public interface HttpPostService {
    @FormUrlEncoded
    @POST("AppFiftyToneGraph/videoLink")
    Observable<BaseResultEntity<List<SubjectResult>>> getAllVideoBy(@Field("once") boolean once);

    @GET("api/public/check-version")
    Observable<BaseResultEntity<VersionResult>> getVersionResult(@QueryMap Map<String, String> params);
}
