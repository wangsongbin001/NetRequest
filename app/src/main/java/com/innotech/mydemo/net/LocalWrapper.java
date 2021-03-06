package com.innotech.mydemo.net;

import android.content.Context;

import com.innotech.netrequest.net.RetrofitUtils;
import com.innotech.netrequest.net.model.URLConfig;

public class LocalWrapper {

    public static LocalService localService;

    public static LocalService getService(Context context){
        if(localService == null){
            localService = RetrofitUtils.getRetrofit(context, URLConfig.BASE_URL_ONLINE, null).create(LocalService.class);
        }
        return localService;
    }
}
