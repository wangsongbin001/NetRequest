package com.innotech.mydemo.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.innotech.netrequest.util.SharedPreUtils;

public class CommonSharePreUtil {

    static CommonSharePreUtil instance;
    SharedPreferences sharedPreferences;

    private CommonSharePreUtil(Context context){
        sharedPreferences = context.getSharedPreferences("CACHE", Context.MODE_PRIVATE);
    }

    public static CommonSharePreUtil getInstance(Context context) {
        if (instance == null) {
            synchronized(SharedPreUtils.class) {
                if (instance == null) {
                    instance = new CommonSharePreUtil(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    public SharedPreferences getPerference() {
        return this.sharedPreferences;
    }

}
