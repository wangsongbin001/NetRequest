package com.innotech.netrequest.net.interceptor;

import android.content.Context;

import com.innotech.netrequest.util.LogUtil;
import com.innotech.netrequest.util.NetworkUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/12.
 */

public class MCacheOfflineInterceptor implements Interceptor{

    private Context context;
    public MCacheOfflineInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
//        LogUtil.d("MNetRequest", "method:" + request.method());Cache只存储GET类型的请求响应
        if(!NetworkUtil.isNetworkAvailable(context) && "GET".equals(request.method())){
            request = request.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached")
                    .build();
        }

        return chain.proceed(request);
    }
}
