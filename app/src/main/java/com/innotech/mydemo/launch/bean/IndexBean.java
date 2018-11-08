package com.innotech.mydemo.launch.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * wang
 */
public class IndexBean implements Serializable{
    /**
     * 广告
     */
    @Expose
    @SerializedName("adBean")
    public AdBean adBean;

    /**
     * 全局配置
     */
    @Expose
    @SerializedName("appConfigBean")
    public AppConfigBean appConfigBean;

    /**
     * 版本信息,包括Android 与 IOS
     */
    @Expose
    @SerializedName("android")
    public AppVersionBean android;
    @Expose
    @SerializedName("ios")
    public AppVersionBean ios;


    @Override
    public String toString() {
        return "IndexBean{" +
                "adBean=" + adBean +
                ", appConfigBean=" + appConfigBean +
                ", android=" + android +
                ", ios=" + ios +
                '}';
    }
}
