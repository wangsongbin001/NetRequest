package com.innotech.netrequest.net.interceptor;

import android.content.Context;

import com.innotech.netrequest.BaseApp;
import com.innotech.netrequest.util.SharedPreUtils;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/4.
 * 保存在响应头的cookie
 */

public class CookieSavedInterceptor implements Interceptor{

    private Context context;

    public CookieSavedInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (!response.headers("Set-Cookie").isEmpty()) {
            HashSet<String> cookies = new HashSet<>();

            for (String header : response.headers("Set-Cookie")) {
                cookies.add(header);
            }

            SharedPreUtils.getInstance(context).getPerference().edit()
                    .putStringSet(SharedPreUtils.PREF_COOKIE, cookies)
                    .apply();
        }
        return response;
    }
}
