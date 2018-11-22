package com.innotech.mydemo.net;

import com.innotech.mydemo.bean.TokenBean;
import com.innotech.netrequest.entities.ResponseBean;

import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by admin on 2018/4/9.
 */

public interface CuriserService {

    @POST("v1/account/login")
    Observable<TokenBean> login(@Body Map<String, Object> params);

    @POST("v1/account/login")
    Observable<ResponseBean> login2(@Body Map<String, Object> params);

    @POST("v1/account/login")
    Observable<String> login3(@Body Map<String, Object> params);

    //默认背压策略 BackpressureStrategy.BUFFER
    @POST("v1/account/login")
    Flowable<TokenBean> login4(@Body Map<String, Object> params);

//    @POST
//    Observable<LoginBean> login(@Url String url, @Body Map<String, Object> params);

    @GET("v1/company/")
    Observable<String> getProjectList(@Header("Authorization") String authorization);
}
