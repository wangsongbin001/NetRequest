package com.innotech.mydemo.launch.observer;

import android.app.DownloadManager;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.Handler;
import android.os.Message;

public class DownloadObserver extends ContentObserver{
    /**
     * 下载任务编号
     */
    private long downloadId;
    /**
     * 主线程处理类
     */
    private Handler handler;
    /**
     * 上下文对象
     */
    private Context context;


    public DownloadObserver(Context context, Handler handler, long downloadId){
        super(handler);
        this.downloadId = downloadId;
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

        // 实例化查询类，这里需要一个刚刚的downid
        DownloadManager.Query query = new DownloadManager.Query().setFilterById(downloadId);
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        // 这个就是数据库查询啦
        Cursor cursor = downloadManager.query(query);
        while (cursor.moveToNext()) {
            int mDownload_so_far = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_BYTES_DOWNLOADED_SO_FAR));
            int mDownload_all = cursor.getInt(cursor.getColumnIndexOrThrow(DownloadManager.COLUMN_TOTAL_SIZE_BYTES));
            if (mDownload_all > 0) {
                // 进度 = 已下载字节数 / 总字节数
                int mProgress = (mDownload_so_far * 100) / mDownload_all;

                // 获得Message并赋值
                Message msg = Message.obtain();
                msg.what = 1001;
                msg.obj = mProgress;
                if(handler != null) {
                    handler.sendMessage(msg);
                }
            }
        }
        cursor.close();
    }
}
