package com.innotech.mydemo.launch;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.webkit.MimeTypeMap;

import com.innotech.mydemo.MApp;
import com.innotech.mydemo.conf.AppConfig;
import com.innotech.mydemo.conf.Constant;
import com.innotech.mydemo.launch.util.DownloadUtil;
import com.innotech.mydemo.utils.CommonSharePreUtil;
import com.innotech.mydemo.utils.ToastUtil;
import com.innotech.mydemo.utils.VerifyUtil;
import com.innotech.netrequest.util.LogUtil;

import java.io.File;

public class ApkDownloadService extends Service{
    /**
     * 下载管理器
     */
    DownloadManager mDownloadManager;
    /**
     * 任务完成接收
     */
    OnCompleteReceiver onComplete;
    /**
     * 通知栏点击接收
     */
    BroadcastReceiver onNotificationClick;
    /**
     * 下载编号
     */
    private long downloadId;
    /**
     * 文件名
     */
    private String fileName;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化下载管理器和监听器
        mDownloadManager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        onComplete = new OnCompleteReceiver();
        onNotificationClick = new OnNotificationClick();
        // 注册监听器
        registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        registerReceiver(onNotificationClick, new IntentFilter(DownloadManager.ACTION_NOTIFICATION_CLICKED));
    }

    @Override
    public void onDestroy() {
        // 注销完成监听器和通知栏点击监听器
        unregisterReceiver(onComplete);
        unregisterReceiver(onNotificationClick);
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String url = intent.getStringExtra("downloadUrl");
            fileName = intent.getStringExtra("fileName");
            LogUtil.i("wsb", "download step1," + "downloadUrl:" + url + ",fileName:" + fileName);
            Environment.getExternalStoragePublicDirectory(AppConfig.FILEPATH).mkdirs();
            download(url, fileName);
        }
        long lastDownloadId = CommonSharePreUtil.getInstance(this).getPerference()
                .getLong(AppConfig.DOWNLOAD_ID, -1);
        if(-1 != lastDownloadId){
            downloadId = lastDownloadId;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载apk
     * @param url
     * @param fileName
     */
    private void download(String url, String fileName){
        //校验下载地址
        if(!VerifyUtil.isUrl(url)){
            ToastUtil.showToastL(getApplicationContext(),"下载地址不正确");
            return;
        }
        //检查下载地址是否正在下载
        if(isDownloading(url)){
            return;
        }

        //创建下载实例，加入下载任务管理器
        downloadId = mDownloadManager.enqueue(createRequest(url, fileName));
        CommonSharePreUtil.getInstance(this).getPerference().edit()
                .putLong(AppConfig.DOWNLOAD_ID, downloadId)
                .apply();

        // 把当前下载id发送给广播接收者
        Intent intent = new Intent();
        intent.putExtra("id", downloadId);
        intent.setAction(Constant.DOWNLOAD_TASK_LAUNCH);
        sendBroadcast(intent);

        LogUtil.i("wsb", "download step2");
    }

    /**
     * 创建下载任务请求
     * @param url
     * @param fileName
     * @return
     */
    private DownloadManager.Request createRequest(String url, String fileName){
        Uri uri = Uri.parse(url);
        //getContentResolver().registerContentObserver(uri,true,new DownloadObserver(handler,this,downloadid));
        DownloadManager.Request request = new DownloadManager.Request(uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(true);
        // 根据文件后缀设置mime
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        int startIndex = fileName.lastIndexOf(".");
        String tmpMimeString = fileName.substring(startIndex + 1).toLowerCase();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(tmpMimeString);
        request.setMimeType(mimeString);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setTitle(fileName);
        request.addRequestHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
        request.setDescription("更新下载");

        File fileDir = Environment.getExternalStoragePublicDirectory(AppConfig.FILEPATH);
        if (fileDir.mkdirs() || fileDir.isDirectory()) {
            request.setDestinationInExternalPublicDir(AppConfig.FILEPATH, fileName);
        }
        // request.setDestinationInExternalPublicDir(AppConfig.FILEPATH, fileName);
        return request;
    }

    /**
     * 判断任务是否正在下载
     * @param url
     * @return
     */
    public boolean isDownloading(String url){
        Cursor c = mDownloadManager.query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
        if (c != null && c.moveToFirst()) {
            String tmpURI = c.getString(c.getColumnIndex(DownloadManager.COLUMN_URI));
            if (tmpURI.equals(url)){
                if(!c.isClosed()){
                    c.close();
                }
                return true;
            }
        }
        if(c != null && !c.isClosed()){
            c.close();
        }
        return false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** 下载完成的接收器 */
    private class OnCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent intent) {
            // 通过intent获取发广播的id
            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if (id != downloadId) {
                return;
            }
            Cursor c = mDownloadManager.query(new DownloadManager.Query().setFilterById(id));
            if (c.moveToFirst()) {
                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
                if (c.getInt(columnIndex) == DownloadManager.STATUS_SUCCESSFUL) {
                    String localFileUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
                    CommonSharePreUtil.getInstance(ApkDownloadService.this).getPerference()
                            .edit().putLong(AppConfig.DOWNLOAD_ID, -1).apply();
                    LogUtil.i("wsb", "step 4: install");
                    //安装Apk,
                    DownloadUtil.installApkByGuide(ApkDownloadService.this,
                            localFileUri.replaceFirst("file://", ""));
                }
            }
            c.close();
            stopSelf();
           // 下载完成  退出app
            MApp.getInstance().exit(getApplicationContext());
        }
    }

    /** 下载通知点击的监听器 */
    private class OnNotificationClick extends BroadcastReceiver{
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            showDownloadManagerView();
        }
    };

    /**
     * 展示下载管理界面
     */
    private void showDownloadManagerView(){
        Intent intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
