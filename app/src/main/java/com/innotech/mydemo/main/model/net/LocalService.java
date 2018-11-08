package com.innotech.mydemo.main.model.net;

import com.innotech.mydemo.launch.bean.IndexBean;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface LocalService {

    @POST("index.php")
    Observable<IndexBean> appStart();

}
