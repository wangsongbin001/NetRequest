package com.innotech.netrequest.net.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.innotech.netrequest.util.LogUtil;
import com.innotech.netrequest.util.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/7.
 */

public class MCacheInterceptor implements Interceptor{
    protected Context context;
    protected static final int maxStale = 259200;//3 days
    protected static final int maxStaleOnline = 60;

    public MCacheInterceptor(Context context) {
        this.context = context;
    }

    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response originalResponse = chain.proceed(request);
        LogUtil.d("MNetRequest", "body:" + originalResponse.body().toString());
        LogUtil.d("MNetRequest", "cache:" + originalResponse.cacheResponse());
        LogUtil.d("MNetRequest", "network:" + originalResponse.networkResponse());
        LogUtil.d("MNetRequest", "thread:" + Thread.currentThread());
        if (NetworkUtil.isNetworkAvailable(context)) {
//            int maxAge = 60;
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + maxStaleOnline)
                    .build();
        } else {
//            int maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    }

}
