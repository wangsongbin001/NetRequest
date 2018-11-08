package com.innotech.mydemo.launch.bean;

import com.innotech.mydemo.launch.util.DownloadUtil;
import com.innotech.mydemo.utils.CommonUtil;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 下载信息的实体类封装。
 */
@Setter
@Getter
@Data
public class DownloadBean {
    /** 安装包下载地址 */
    private String downloadUrl;

    /** 安装包版本号 */
    private int versionNo;

    /** 安装包版本名称 */
    private String versionName;

    /** 安装包大小 kb*/
    private float appSize;

    /** 安装包介绍 */
    private String introduce;

    /** 安装包在本地是否已存在 */
    private boolean isDownloaded;

    /** 安装包本地路径 */
    private String locationPath;

    /** 获得安装包完整文件名 */
    public String getAppFileName(){
        return DownloadUtil.getAppPackageName(this.versionNo);
    }

}
