package com.innotech.netrequest.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.io.Serializable;
import java.util.HashSet;

/**
 * Created by admin on 2018/4/4.
 * SharedPreferenceUtil
 */

public class SharedPreUtils {

    public final static String CACHE = "CACHE";
    public final static String PREF_COOKIE = "pref_cookie";
    public final static String TOKEN = "token";

    private static volatile SharedPreUtils instance;
    private SharedPreferences sp;

    public static SharedPreUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (SharedPreUtils.class) {
                if (instance == null) {
                    instance = new SharedPreUtils(context.getApplicationContext());
                }
            }
        }
        return instance;
    }

    private SharedPreUtils(Context context) {
        sp = context.getSharedPreferences(CACHE, Context.MODE_PRIVATE);
    }

    /**
     * SharedPreferences通过key取值
     *
     * @param key
     * @param dfValue 默认值
     * @return
     */
    public String getValue(String key, String dfValue) {
        String value = sp.getString(key, dfValue);
        if ("null".equals(value)) {
            value = dfValue;
        }
        return value;
    }

    /**
     * SharedPreferences通过key存value
     *
     * @param key
     * @param value
     */
    public void saveValue(String key, String value) {
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * sp单例
     * @return
     */
    public SharedPreferences getPerference(){
        return sp;
    }
}
