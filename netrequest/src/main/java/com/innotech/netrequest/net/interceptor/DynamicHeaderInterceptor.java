package com.innotech.netrequest.net.interceptor;

import android.content.Context;
import android.text.TextUtils;

import com.innotech.netrequest.net.model.NetConfig;
import com.innotech.netrequest.util.SharedPreUtils;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by admin on 2018/4/10.
 * 可动态更新的统一请求头，封装例如：时间，deviceId，token。
 * 这个是需要与服务端，配合定制（读写涉及到并发的问题）
 */

public class DynamicHeaderInterceptor implements Interceptor{

    private Context context;

    public DynamicHeaderInterceptor(Context context){
        this.context = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //添加
        request = request.newBuilder()
                .addHeader("deviceid", "")//Utils.getDeviceId();
                .addHeader("token", NetConfig.getToken())
                .build();
        Response response = chain.proceed(request);
        //保存
        String strDate = response.header("Date");
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        try {
            NetConfig.setServerDate(sdf.parse(strDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String token = response.header("token");
        if (!TextUtils.isEmpty(token) && !token.equals(NetConfig.getToken())) {
            NetConfig.saveToken(token);
            SharedPreUtils.getInstance(context).saveValue("token", token);
        }
        return response;
    }

}
