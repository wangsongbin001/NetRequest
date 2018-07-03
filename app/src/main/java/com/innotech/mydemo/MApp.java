package com.innotech.mydemo;

import android.app.Application;

import com.innotech.netrequest.crash.MCrashHandler;

/**
 * Created by admin on 2018/4/18.
 */

public class MApp extends Application{

    private static String token;

    public static String getToken(){
        return token;
    }

    public static void setToken(String tempToken){
        token = tempToken;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        MCrashHandler.getInstance().init(this);
    }
}
