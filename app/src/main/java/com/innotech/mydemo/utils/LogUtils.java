package com.innotech.mydemo.utils;

import android.util.Log;

import com.innotech.mydemo.BuildConfig;

public class LogUtils {

    public static void i(String tag, String msg){
        if(BuildConfig.DEBUG){
            Log.i(tag, msg);
        }
    }
}
