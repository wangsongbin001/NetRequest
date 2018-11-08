package com.innotech.mydemo.utils;

import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    /**
     * 短消息
     * @param context
     * @param msg
     */
    public static void showToastS(Context context, String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长消息
     * @param context
     * @param msg
     */
    public static void showToastL(Context context, String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_LONG).show();
    }
}
