package com.innotech.mydemo.launch.pressenter;

import android.util.Log;
import android.widget.Toast;

import com.innotech.mydemo.launch.LaunchActivity;
import com.innotech.mydemo.launch.bean.IndexBean;
import com.innotech.mydemo.model.net.LocalWrapper;
import com.innotech.netrequest.net.BaseErrorConsumer;
import com.innotech.netrequest.net.LoadingTransformer;
import com.innotech.netrequest.util.NetworkUtil;

import io.reactivex.functions.Consumer;

public class LaunchPressenterImp implements ILanchPressenter{

    LaunchActivity launchActivity;
    public  LaunchPressenterImp(LaunchActivity launchActivity){
        this.launchActivity = launchActivity;
    }

    @Override
    public void appStart() {
        if(!NetworkUtil.isNetworkAvailable(launchActivity)){
             Toast.makeText(launchActivity, "网络不可用，请检查您的网络！", Toast.LENGTH_LONG).show();
             return;
        }
        LocalWrapper.getService(launchActivity)
                .appStart()
                .compose(new LoadingTransformer(launchActivity))
                .subscribe(new Consumer<IndexBean>() {
                    @Override
                    public void accept(IndexBean indexBean) throws Exception {
                        Log.i("wsb", indexBean.toString());
                    }
                }, new BaseErrorConsumer(launchActivity));
    }

    private void checkVersionAndAd(){

    }
}
