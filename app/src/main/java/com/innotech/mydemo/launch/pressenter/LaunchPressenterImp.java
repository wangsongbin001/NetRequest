package com.innotech.mydemo.launch.pressenter;

import android.widget.Toast;

import com.innotech.mydemo.launch.LaunchActivity;
import com.innotech.mydemo.launch.bean.AppVersionBean;
import com.innotech.mydemo.launch.bean.DownloadBean;
import com.innotech.mydemo.launch.bean.IndexBean;
import com.innotech.mydemo.launch.util.DownloadUtil;
import com.innotech.mydemo.main.model.net.LocalWrapper;
import com.innotech.mydemo.utils.ToastUtil;
import com.innotech.netrequest.net.BaseErrorConsumer;
import com.innotech.netrequest.net.LoadingTransformer;
import com.innotech.netrequest.util.NetworkUtil;

import java.io.File;

import io.reactivex.functions.Consumer;

public class LaunchPressenterImp implements ILanchPressenter{

    LaunchActivity launchActivity;
    public  LaunchPressenterImp(LaunchActivity launchActivity){
        this.launchActivity = launchActivity;
    }

    @Override
    public void appStart() {
        if(!NetworkUtil.isNetworkAvailable(launchActivity)){
             ToastUtil.showToastL(launchActivity, "网络不可用，请检查您的网络！");
             return;
        }
        LocalWrapper.getService(launchActivity)
                .appStart()
                .compose(new LoadingTransformer(launchActivity))
                .subscribe(new Consumer<IndexBean>() {
                    @Override
                    public void accept(IndexBean indexBean) throws Exception {
                        launchActivity.showUpdateDialog(createDownloadBean(indexBean.android));
                    }
                }, new BaseErrorConsumer(launchActivity));
    }

    private void checkVersionAndAd(){

    }

    /**
     * 实例化安装信息类
     * @param androidInfo
     * @return
     */
    private DownloadBean createDownloadBean(AppVersionBean androidInfo){

        DownloadBean appPackage = new DownloadBean();
        appPackage.setLocationPath(DownloadUtil.getAppfilePath(androidInfo.versionCode));
        appPackage.setDownloadUrl(androidInfo.downloadUrl);
        appPackage.setAppSize(androidInfo.appKBsize);
        appPackage.setVersionNo(androidInfo.versionCode);
        appPackage.setVersionName(androidInfo.versionName);
        appPackage.setIntroduce(androidInfo.appIntroduce);

        // 检索文件路径是否存在
        File file = new File(appPackage.getLocationPath());
        if (file.exists()) {
            appPackage.setDownloaded(true);
        } else {
            appPackage.setDownloaded(false);
        }
        return appPackage;
    }
}
