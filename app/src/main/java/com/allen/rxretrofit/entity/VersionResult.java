package com.allen.rxretrofit.entity;

import java.io.Serializable;

/**
 * Created by allen on 2017/12/4.
 */

public class VersionResult implements Serializable {

    /**
     * downloadurl : 下载地址（0的时候为空）
     * desc : 更新描述
     * miniver : 1//最小内部版本号, 小于指定最小版本号必须强制更新
     * iver : 1.0.0.1
     * vercode : 100
     */

    private String downloadurl;
    private String desc;
    private String miniver;
    private String iver;
    private String vercode;

    public String getDownloadurl() {
        return downloadurl;
    }

    public void setDownloadurl(String downloadurl) {
        this.downloadurl = downloadurl;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getMiniver() {
        return miniver;
    }

    public void setMiniver(String miniver) {
        this.miniver = miniver;
    }

    public String getIver() {
        return iver;
    }

    public void setIver(String iver) {
        this.iver = iver;
    }

    public String getVercode() {
        return vercode;
    }

    public void setVercode(String vercode) {
        this.vercode = vercode;
    }
}
