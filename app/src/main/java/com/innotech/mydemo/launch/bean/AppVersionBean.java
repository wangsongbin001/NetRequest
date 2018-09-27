package com.innotech.mydemo.launch.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppVersionBean implements Serializable{
    /**
     * apk的下载链接
     */
    @Expose
    @SerializedName("downloadUrl")
    public String downloadUrl;
    /**
     * 版本号
     */
    @Expose
    @SerializedName("versionCode")
    public int versionCode;
    /**
     * 版本名称
     */
    @Expose
    @SerializedName("versionName")
    public String versionName;
    /**
     * 新版本特性
     */
    @Expose
    @SerializedName("appIntroduce")
    public String appIntroduce;

    @Override
    public String toString() {
        return "AppVersionBean{" +
                "downloadUrl='" + downloadUrl + '\'' +
                ", versionCode=" + versionCode +
                ", versionName='" + versionName + '\'' +
                ", appIntroduce='" + appIntroduce + '\'' +
                '}';
    }
}
