package com.innotech.mydemo.launch.util;

import android.content.Context;
import android.content.Intent;

import com.innotech.mydemo.launch.ApkDownloadService;
import com.innotech.mydemo.utils.CommonUtil;
import com.innotech.mydemo.utils.ToastUtil;

public class DownloadUtil {

    public static void startDownload(Context context, String url, String fileName, float size) {
        Float tempFileSize = size * 1024;
        if (CommonUtil.detectSdcardIsExist()) {
            if (CommonUtil.isAvaiableSpace(tempFileSize)) {
                Intent intent = new Intent(context, ApkDownloadService.class);
                intent.putExtra("downloadUrl", url);
                intent.putExtra("fileName", fileName);
                context.startService(intent);
            } else {
                ToastUtil.showToastS(context, "存储卡空间不足");
            }
        } else {
            ToastUtil.showToastS(context, "请检查存储卡是否安装");
        }
    }
}
