package com.innotech.mydemo;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.innotech.netrequest.crash.MCrashHandler;

import java.nio.MappedByteBuffer;

/**
 * Created by admin on 2018/4/18.
 */

public class MApp extends Application {

    private static String token;
    /**
     * 单例
     */
    private static MApp instance;
    public static MApp getInstance(){
        return instance;
    }
    public static MApp get(){
        return instance;
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String tempToken) {
        token = tempToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MCrashHandler.getInstance().init(this);
        instance = this;
    }

    public void exit(Context context) {
        try {
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.killBackgroundProcesses(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }
}
