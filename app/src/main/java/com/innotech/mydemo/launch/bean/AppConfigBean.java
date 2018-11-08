package com.innotech.mydemo.launch.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AppConfigBean implements Serializable{
    /**
     * 推荐链接
     */
    @Expose
    @SerializedName("remindH5Url")
    public String remindH5Url;

    @Override
    public String toString() {
        return "AppConfigBean{" +
                "remindH5Url='" + remindH5Url + '\'' +
                '}';
    }
}
