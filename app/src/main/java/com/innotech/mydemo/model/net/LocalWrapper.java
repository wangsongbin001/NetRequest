package com.innotech.mydemo.model.net;

import android.content.Context;

import com.innotech.netrequest.net.RetrofitUtils;
import com.innotech.netrequest.net.model.URLConfig;

public class LocalWrapper {

    public static LocalService localService;

    public static LocalService getService(Context context){
        if(localService == null){
            localService = RetrofitUtils.getRetrofit(context, URLConfig.BASE_URL_LOCAL, null).create(LocalService.class);
        }
        return localService;
    }
}
