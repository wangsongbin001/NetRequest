package com.innotech.netrequest.net.interceptor;

import android.content.Context;
import android.util.Log;

import com.innotech.netrequest.BaseApp;
import com.innotech.netrequest.util.SharedPreUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/4.
 * 在请求中添加cookie
 */

public class CookieReadInterceptor implements Interceptor {
    private Context context;

    public CookieReadInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        HashSet<String> cookies = (HashSet<String>) SharedPreUtils.getInstance(context)
                .getPerference().getStringSet(SharedPreUtils.PREF_COOKIE, new HashSet<String>());
        for (String cookie : cookies) {
            builder.addHeader("Cookie", cookie);
            Log.v("OkHttp", "Adding Header: " + cookie);
        }
        return chain.proceed(builder.build());
    }
}
