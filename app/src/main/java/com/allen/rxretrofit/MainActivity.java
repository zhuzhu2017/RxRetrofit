package com.allen.rxretrofit;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.allen.rxretrofit.api.VersionResultApi;
import com.allen.rxretrofit.base.BaseActivity;
import com.allen.rxretrofit.entity.VersionResult;
import com.allen.rxretrofitlib.listener.HttpOnNextListener;
import com.allen.rxretrofitlib.manager.HttpManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_getData)
    Button btnGetData;
    @BindView(R.id.tv_showData)
    TextView tvShowData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.btn_getData)
    public void onViewClicked() {
        simpleGetData();
    }

    /**
     * 获取数据
     */
    private void simpleGetData() {
        VersionResultApi api = new VersionResultApi(listener, this);
        api.setVersioncode("23");
        api.setType("1");
        HttpManager.getInstance().httpRequest(api);
    }

    HttpOnNextListener<VersionResult> listener = new HttpOnNextListener<VersionResult>() {
        @Override
        public void onNext(VersionResult versionResult) {
            Log.d("结果正常：", versionResult + "");
        }

        @Override
        public void onError(Throwable e) {
            super.onError(e);
            Log.d("结果异常：", e.getMessage() + "");
        }

        @Override
        public void onError(int errorCode) {
            super.onError(errorCode);
            Log.d("结果异常：", errorCode + "");
        }

        @Override
        public void onCancel() {
            super.onCancel();
            Log.d("结果取消", "取消请求");
        }

        @Override
        public void onCacheNext(String cache) {
            super.onCacheNext(cache);
            Log.d("结果缓存", cache);
        }
    };

}
