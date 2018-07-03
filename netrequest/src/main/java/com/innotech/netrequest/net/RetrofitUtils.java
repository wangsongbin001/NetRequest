
package com.innotech.netrequest.net;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.innotech.netrequest.BuildConfig;
import com.innotech.netrequest.net.interceptor.BaseHeadersInterceptor;
import com.innotech.netrequest.net.interceptor.MCacheInterceptor;
import com.innotech.netrequest.net.interceptor.CookieReadInterceptor;
import com.innotech.netrequest.net.interceptor.CookieSavedInterceptor;
import com.innotech.netrequest.net.interceptor.MCacheOfflineInterceptor;
//import com.innotech.netrequest.retrofit.LoggingInterceptor;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.schedulers.Schedulers;
import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    static Map<String, Retrofit> retrofits = new HashMap<String, Retrofit>();
    private static Retrofit mRetrofit;

    /**
     * 单例模式
     *
     * @return
     */
    public static Retrofit getRetrofit(Context context, String baseUrl, Map<String, Object> headers) {
        if (TextUtils.isEmpty(baseUrl) || context == null) {
            return null;
        }
        mRetrofit = retrofits.get(baseUrl);
        if (mRetrofit == null) {
            synchronized (RetrofitUtils.class) {
                if (mRetrofit == null) {
                    mRetrofit = buildRetrofit(context, baseUrl, headers);
                    retrofits.put(baseUrl, mRetrofit);
                }
            }
        }
        return mRetrofit;
    }

    private static Retrofit buildRetrofit(Context context, String baseUrl, Map<String, Object> headers) {

        if(headers == null){//共同的请求头
            headers = new HashMap<>();
        }

        context = context.getApplicationContext();

        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        // 开发环境添加log
        if (!"release".equals(BuildConfig.BUILD_TYPE)) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(logging);
        }

        //设置缓存目录和大小
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File httpCacheDirectory = new File(context.getCacheDir(), "http_cache");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        httpClientBuilder
                .addInterceptor(new BaseHeadersInterceptor(headers))//添加共同的请求头
//                .addInterceptor(new LoggingInterceptor())
                .addInterceptor(new CookieReadInterceptor(context))//设置支持cookie
                .addInterceptor(new CookieSavedInterceptor(context))
                .connectTimeout(20, TimeUnit.SECONDS) // 设置超时时间
                .readTimeout(20, TimeUnit.SECONDS)
                .addNetworkInterceptor(new MCacheInterceptor(context))//设置支持缓存（限GET）
                .addInterceptor(new MCacheOfflineInterceptor(context))
                .connectionPool(new ConnectionPool(5, 8, TimeUnit.SECONDS))
                .cache(cache);//设置缓存。

        OkHttpClient client = httpClientBuilder.build();

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();
        //生成Retrofit
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(new ObserveOnMainCallAdapterFactory())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .addConverterFactory(DataMapGsonConverterFactory.create(gson))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client).build();
    }


}
