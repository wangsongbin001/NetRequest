package com.innotech.mydemo.launch.util;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;

import com.innotech.mydemo.conf.AppConfig;
import com.innotech.mydemo.conf.Constant;
import com.innotech.mydemo.launch.LaunchActivity;
import com.innotech.mydemo.launch.observer.DownloadObserver;
import com.innotech.mydemo.launch.util.DownloadUtil;
import com.innotech.mydemo.utils.CommonSharePreUtil;
import com.innotech.netrequest.util.LogUtil;

/**
 * 用于管理下载时进度框
 * @author wang
 */
public class DownloadProgressDialogController {

    private static final int PROGRESS_MAX = 100;
    private static final int DOWNLOAD_PROGRESS_UPDATE_WAHT = 1001;
    /**
     * 启动页
     */
    LaunchActivity mLanchActivity;
    /**
     * 显示下载进度框的广播
     */
    DownLoadIdBroadCast broadcast;
    /**
     * 进度条展示框
     */
    ProgressDialog progressDialog;
    /**
     * 下载进度观察者
     * @param launchActivity
     */
    DownloadObserver downloadObserver;
    /**
     * 更新进度条
     */
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == DOWNLOAD_PROGRESS_UPDATE_WAHT){
                 int progress = (int) msg.obj;
                 if(progressDialog != null){
//                     LogUtil.i("wsb", "progress:" + progress);
                     progressDialog.setProgress(progress);
                     if(progress == PROGRESS_MAX){
                         progressDialog.dismiss();
                     }
                 }
            }
        }
    };

    public DownloadProgressDialogController(LaunchActivity launchActivity){
        this.mLanchActivity = launchActivity;
        register();
    }

    /**
     * 注册动态广播，用于接收当前下载的apk的downloadId
     */
    private void register(){
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.DOWNLOAD_TASK_LAUNCH);

        broadcast = new DownLoadIdBroadCast();
        mLanchActivity.registerReceiver(broadcast,filter);
    }

    /**
     * 下载广播接收者，下载id是-1时注册内容观察者
     */
    public class DownLoadIdBroadCast extends BroadcastReceiver {

        public DownLoadIdBroadCast(){}
        @Override
        public void onReceive(Context context, Intent intent) {
            long downLoadId = intent.getLongExtra("id", -1);

            if(downLoadId != -1) {
                downloadObserver = new DownloadObserver(mLanchActivity, handler, downLoadId);
                mLanchActivity.getContentResolver().registerContentObserver(
                        Uri.parse("content://downloads/"),
                        true, downloadObserver);
                LogUtil.i("wsb", "download step3" + ",downloadId:" + downLoadId);
            }
        }
    }

    /**
     *
     * @param downloadUrl
     * @return
     */
    public boolean checkIsDownloading(String downloadUrl){
        if (isDownloading(downloadUrl)){
            long id = CommonSharePreUtil.getInstance(mLanchActivity)
                    .getPerference().getLong(AppConfig.DOWNLOAD_ID, -1);
            if(id != -1) {
                startProgressDialog();
            }
            return true;
        }
        return false;
    }

    /**
     * 判断任务是否正在下载
     * @param url
     * @return
     */
    public boolean isDownloading(String url){
        Cursor c = ((DownloadManager)mLanchActivity.getSystemService(Context.DOWNLOAD_SERVICE))
                .query(new DownloadManager.Query().setFilterByStatus(DownloadManager.STATUS_RUNNING));
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

    /**
     * 显示弹窗
     */
    public void startProgressDialog(){
        displayProgressDialog();
        //创建观察者，监听下载进度。
        downloadObserver = new DownloadObserver(mLanchActivity, handler,
                CommonSharePreUtil.getInstance(mLanchActivity).getPerference().getLong(AppConfig.DOWNLOAD_ID, 0));
        mLanchActivity.getContentResolver().registerContentObserver(Uri.parse("content://downloads/")
                , true, downloadObserver);
    }

    /**
     * 展示进度条
     */
    public void displayProgressDialog(){
        // 创建ProgressDialog对象
        progressDialog = new ProgressDialog(mLanchActivity);
        // 设置进度条风格，风格为长形
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // 设置ProgressDialog 标题
        progressDialog.setTitle("下载提示");
        // 设置ProgressDialog 提示信息
        progressDialog.setMessage("当前下载进度:");
        // 设置ProgressDialog 标题图标
        //progressDialog.setIcon(R.drawable.a);
        // 设置ProgressDialog 进度条进度
        progressDialog.setProgress(PROGRESS_MAX);
        // 设置ProgressDialog 的进度条是否不明确
        progressDialog.setIndeterminate(false);
        // 设置ProgressDialog 是否可以按退回按键取消
        progressDialog.setCancelable(false);
        // 让ProgressDialog显示
        progressDialog.show();
    }

    public void onDestroy(){
        if(handler != null){
            handler.removeCallbacksAndMessages(null);
        }
        if(downloadObserver != null){
            mLanchActivity.getContentResolver().unregisterContentObserver(downloadObserver);
        }
        if(progressDialog != null){
            progressDialog.dismiss();
        }
    }

}
