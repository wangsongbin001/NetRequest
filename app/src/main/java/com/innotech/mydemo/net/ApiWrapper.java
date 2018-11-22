package com.innotech.mydemo.net;

import android.content.Context;

import com.innotech.netrequest.net.RetrofitUtils;
import com.innotech.netrequest.net.model.URLConfig;

public class ApiWrapper {

    public static ApiService apiService;

    public static ApiService getService(Context context){
        if(apiService == null){
            apiService = RetrofitUtils.getRetrofit(context, URLConfig.BASE_URL, null).create(ApiService.class);
        }
        return apiService;
    }
}
