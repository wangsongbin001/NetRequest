package com.innotech.mydemo.launch;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.os.IBinder;

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
//            Environment.getExternalStoragePublicDirectory(AppConfig.FILEPATH).mkdirs();
            download(url, fileName);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载apk
     * @param url
     * @param fileName
     */
    private void download(String url, String fileName){

    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /** 下载完成的接收器 */
    private class OnCompleteReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context arg0, Intent intent) {
//            // 通过intent获取发广播的id
//            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
//            if (id != downloadId) {
//                return;
//            }
//            Cursor c = downloadManager.query(new DownloadManager.Query().setFilterById(id));
//            if (c.moveToFirst()) {
//                int columnIndex = c.getColumnIndex(DownloadManager.COLUMN_STATUS);
//                if (c.getInt(columnIndex) == DownloadManager.STATUS_SUCCESSFUL) {
//                    String localFileUri = c.getString(c.getColumnIndex(DownloadManager.COLUMN_LOCAL_URI));
//                    SharedPreUtils.getInstance(DownloadService.this).saveValue(SharedPreUtils.DOWNLOADID,"-1");
//                    DownloadUtils.installApkByGuide(DownloadService.this,
//                            localFileUri.replaceFirst("file://", ""));
//                }
//            }
//            c.close();
//            stopSelf();
//            // 下载完成  退出app
//            App.getInstance().exit(getApplicationContext());
        }
    }

    /** 下载通知点击的监听器 */
    private class OnNotificationClick extends BroadcastReceiver{
        @Override
        public void onReceive(Context ctxt, Intent intent) {
//            showDownloadManagerView();
        }
    };

}
