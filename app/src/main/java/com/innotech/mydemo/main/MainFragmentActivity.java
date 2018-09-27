package com.innotech.mydemo.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.innotech.mydemo.R;
import com.innotech.mydemo.bean.ResVersionBean;
import com.innotech.netrequest.entities.ResponseBean;
import com.innotech.mydemo.bean.TokenBean;
import com.innotech.mydemo.bean.TotalBean;
import com.innotech.mydemo.model.net.ApiWrapper;
import com.innotech.mydemo.model.net.CuriserWrapper;
import com.innotech.netrequest.BaseActivity;
import com.innotech.netrequest.net.ApiException;
import com.innotech.netrequest.net.BaseErrorConsumer;
import com.innotech.netrequest.net.LoadingFlowableTransformer;
import com.innotech.netrequest.net.LoadingTransformer;

import org.reactivestreams.Publisher;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by admin on 2018/4/4.
 */

public class MainFragmentActivity extends BaseActivity{

    private Context context;

    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainfragment);
        context = this;

        ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, 1000);
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn1:
                test1();
                break;
            case R.id.btn2:
                test2();
                break;
            case R.id.btn3:
                test3();
                break;
            case R.id.btn4:
                test4();
                break;
            case R.id.btn5:
                test5();
                break;
            case R.id.btn6:
                test6();
                break;
        }
    }

    /**
     * baseUrl:http://carriers-test.ywopt.com/
     * 数据返回类型
     * {
     *     "code":0,
     *     "message":"",
     *     "data":{}
     * }
     */
    private void test1(){
        Map<String, Object> params = new HashMap<>();
        params.put("username", "max");
        params.put("password", "123456");

        addDisposable(CuriserWrapper.getService(this)
                .login(params)
                .compose(new LoadingTransformer(this))
                .subscribe(new Consumer<TokenBean>() {
                    @Override
                    public void accept(TokenBean tokenBean) throws Exception {
                        Log.i("MNetRequest", "login:" + tokenBean.toString());
                    }
                },new BaseErrorConsumer(context)));
    }

    /**
     * baseUrl:https://hgzzs.vmoney.cn/
     * 数据返回类型
     * {
     *     "status":"000",
     *     "message":"",
     *     "data":{}
     * }
     */
    private void test2(){
        ApiWrapper.getService(this)
                .getVersionInfo()
                .retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Observable<Throwable> throwableObservable) throws Exception {
                        return null;
                    }
                })
                .subscribe(new Consumer<ResVersionBean>() {
            @Override
            public void accept(ResVersionBean resVersionBean) throws Exception {
                Log.i("MNetRequest", "login:" + resVersionBean.toString());
            }
        });
    }

    /**
     * baseUrl:https://hgzzs.vmoney.cn/
     * 数据返回类型
     * {
     *     "code": 0,
     *     "message":""
     * }
     */
    private void test3(){
        Map<String, Object> params = new HashMap<>();
        params.put("username", "max");
        params.put("password", "123456");
        addDisposable(CuriserWrapper.getService(this)
                .login2(params)
                .compose(new LoadingTransformer(this))
                .subscribe(new Consumer<ResponseBean>() {
                    @Override
                    public void accept(ResponseBean responseBean) throws Exception {
                        Log.i("MNetRequest", "login:" + responseBean.toString());
                    }
                },new BaseErrorConsumer(context)));
    }

    /**
     * baseUrl:https://hgzzs.vmoney.cn/
     * 数据返回类型
     * {
     *     "status":"000",
     *     "message":"",
     *     "total":100
     * }
     */
    private void test4(){
        Map<String, Object> params = new HashMap<>();
        params.put("username", "max");
        params.put("password", "123456");
        addDisposable(CuriserWrapper.getService(this)
                .login3(params)
                .compose(new LoadingTransformer(this))
                .map(new Function<String, TotalBean>() {
                    @Override
                    public TotalBean apply(String json) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(json, TotalBean.class);
                    }
                })
                .subscribe(new Consumer<TotalBean>() {
                    @Override
                    public void accept(TotalBean totalBean) throws Exception {
                        Log.i("MNetRequest", "login:" + totalBean.toString());
                    }
                },new BaseErrorConsumer(context)));
    }

    private void test5(){
        Map<String, Object> params = new HashMap<>();
        params.put("username", "max");
        params.put("password", "123456");
        addDisposable(CuriserWrapper.getService(this)
                .login4(params)
                .onBackpressureBuffer()
                .compose(new LoadingFlowableTransformer(this))
                .subscribe(new Consumer<TokenBean>() {
                    @Override
                    public void accept(TokenBean tokenBean) throws Exception {
                        Log.i("MNetRequest", "login:" + tokenBean.toString());
                    }
                },new BaseErrorConsumer(context)));
    }

    private void test6(){
//        Intent intent = new Intent(this, BaseActivity.class);
//        startActivity(intent);
        throw new NullPointerException();
    }
}
