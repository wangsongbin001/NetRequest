package com.innotech.mydemo.launch.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.FileProvider;

import com.innotech.mydemo.BuildConfig;
import com.innotech.mydemo.conf.AppConfig;
import com.innotech.mydemo.launch.ApkDownloadService;
import com.innotech.mydemo.utils.CommonUtil;
import com.innotech.mydemo.utils.ToastUtil;

import java.io.File;

/**
 * @author wang
 */
public class DownloadUtil {
    /**
     * 启动下载service, 后台下载
     * @param context
     * @param url
     * @param fileName
     * @param size kb
     */
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

    /**
     * 开始安装apk文件
     *
     * @param context
     * @param localFilePath
     */
    public static void installApkByGuide(Context context, String localFilePath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File file = new File(localFilePath);
        Uri uri;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            uri = FileProvider.getUriForFile(context,
                    BuildConfig.FILES_AUTHORITY, file);
        } else {
            uri = Uri.fromFile(file);
        }
        intent.setDataAndType(uri, "application/vnd.android.package-archive");
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.showToastL(context, "打开安装程序失败");
        }
    }

    /**
     * 生成本地安装包的文件名称
     * @param versionNo
     * @return
     */
    public static String getAppPackageName(int versionNo){
        StringBuffer fileName = new StringBuffer();
        fileName.append(AppConfig.APPNAME);
        fileName.append("_v");
        fileName.append(versionNo);
        fileName.append(".apk");
        return fileName.toString();
    }

    /**
     * 生成本地安装包的文件路径
     * @param versionNo
     * @return
     */
    public static String getAppfilePath(int versionNo){
        StringBuilder filePath = new StringBuilder();
        filePath.append(Environment.getExternalStorageDirectory().getAbsolutePath());
        filePath.append(AppConfig.FILEPATH);
        filePath.append(File.separator);
        filePath.append(getAppPackageName(versionNo));
        return filePath.toString();
    }

}
