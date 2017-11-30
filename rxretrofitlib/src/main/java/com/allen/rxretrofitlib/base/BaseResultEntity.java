package com.allen.rxretrofitlib.base;

import java.io.Serializable;

/**
 * 通用基础格式父类，可以根据接口返回的固定数据格式进行调整
 * Created by allen on 2017/11/30.
 */

public class BaseResultEntity<T> implements Serializable {
    private int code;
    private String msg;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
