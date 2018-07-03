package com.innotech.netrequest.crash;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by admin on 2018/4/26.
 */

public class CrashInfoUploadService extends IntentService {

    public CrashInfoUploadService(){
        super("CrashInfoUploadService");
    }

    public CrashInfoUploadService(String name) {
        super(name);
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        try{
            ArrayList<String> listFiles = intent.getStringArrayListExtra("files");
            if(listFiles == null){
                stopSelf();
            }
            Log.i("MCrashHandler", "" + listFiles.toString());
            //上传服务器（后台无声）
            //删除文件
        }catch (Exception e){

        }

    }


}
