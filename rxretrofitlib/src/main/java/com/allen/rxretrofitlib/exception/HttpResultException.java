package com.allen.rxretrofitlib.exception;

/**
 * 接口返回异常结果的统一处理
 * Created by allen on 2017/11/30.
 */

public class HttpResultException extends RuntimeException {
    public HttpResultException(String errorMsg) {
        super(errorMsg);
    }
}
