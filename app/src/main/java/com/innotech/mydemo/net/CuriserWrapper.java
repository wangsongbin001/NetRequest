package com.innotech.mydemo.net;

import android.content.Context;

import com.innotech.netrequest.net.RetrofitUtils;
import com.innotech.netrequest.net.model.URLConfig;

/**
 * Created by admin on 2018/4/9.
 */

public class CuriserWrapper {
    public static CuriserService curiserService;

    public static CuriserService getService(Context context){
        if(curiserService == null){
            curiserService = RetrofitUtils.getRetrofit(context, URLConfig.BASE_URL2, null).create(CuriserService.class);
        }
        return curiserService;
    }
}
