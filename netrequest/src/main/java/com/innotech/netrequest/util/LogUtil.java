
package com.innotech.netrequest.util;

import android.util.Log;

/**
 * Created by admin on 2018/4/8.
 */

public class LogUtil {
    public static boolean isDebug = true;

    public static void d(String tag, String msg){
        if(isDebug){
            Log.d(tag, msg);
        }
    }
}
