package com.innotech.mydemo.launch.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdBean implements Serializable{
    /**
     * 广告跳转
     */
    @Expose
    @SerializedName("adH5Url")
    public String adH5Url;

    /**
     * 广告展示ImageUrl;
     */
    @Expose
    @SerializedName("adImgUrl")
    public String adImgUrl;

    /**
     * 广告的标题
     */
    @Expose
    @SerializedName("adTitle")
    public String adTitle;

    @Override
    public String toString() {
        return "AdBean{" +
                "adH5Url='" + adH5Url + '\'' +
                ", adImgUrl='" + adImgUrl + '\'' +
                ", adTitle='" + adTitle + '\'' +
                '}';
    }
}
