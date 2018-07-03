package com.innotech.mydemo.model.net;

import com.innotech.mydemo.bean.AdBean;
import com.innotech.mydemo.bean.ResVersionBean;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface ApiService {

    @POST("app/version/newVersion")
    Observable<ResVersionBean> getVersionInfo();

    @POST("app/appIndex/getLaunchAd")
    Observable<AdBean> getAD(@Body Map<String, Object> params);

//    @POST
//    Observable<T> getAd(@Url String url, @Body Map<String, Object> params, Class<T> tClass);
//    @FormUrlEncoded
//    @POST
//    Observable<ResultBean<T>> login(@Url String url, @Body Map<String, Object> params, Class<T> tClass);

    @POST
    Observable<String> uploadFile(@Url String url, RequestBody var1);

    @POST
    Observable<String> downloadFile(@Url String url, @Body Map<String, Object> params);
}
