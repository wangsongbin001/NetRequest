package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangchunyang on 2017/5/4.
 */
public class AdBean implements Serializable {
    @Expose
    public boolean adSwitch; //启动页广告开关
    @Expose
    public List<AdData> adList;

    public static class AdData implements Serializable {
        @Expose
        public String adImgUrl; //启动页广告的图片地址
        @Expose
        public String adClickJumpUrl; //启动页广告点击跳转的链接
    }
}
