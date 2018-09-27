package com.innotech.mydemo.launch;

import android.Manifest;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.innotech.mydemo.R;
import com.innotech.mydemo.launch.bean.AppVersionBean;
import com.innotech.mydemo.launch.pressenter.ILanchPressenter;
import com.innotech.mydemo.launch.pressenter.LaunchPressenterImp;
import com.innotech.mydemo.utils.CommonUtil;
import com.innotech.mydemo.utils.DialogUtil;
import com.innotech.netrequest.net.BaseErrorConsumer;
import com.innotech.netrequest.util.LogUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 启动页，
 * 1，权限申请
 * 2，版本更新
 *    2.1，版本获取
 *    2.2，新版的apk下载
 *    2.3，新版apk的安装
 * 3，新手引导
 * 4，配置加载
 * 5，广告展示
 */
public class LaunchActivity extends AppCompatActivity{

    @Bind(R.id.btn_request)
    Button btnRequest;

    ILanchPressenter mILanchPressenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        mILanchPressenter = new LaunchPressenterImp(this);
    }

    @OnClick({R.id.btn_request})
    public void onClick(View view) {
//        int versionCode = CommonUtil.getAppVersionCode(this);
//        String versionName = CommonUtil.getAppVersionName(this);

//        LogUtil.i("wsb", "versionCode:" + versionCode + ";versionName:" + versionName);
//        mILanchPressenter.appStart();
//        Intent intent = new Intent(this, IntroduceActivity.class);
//        startActivity(intent);


    }

    /**
     * 检查权限
     */
    private void checkPermission(){
        //权限申请:1,检查权限。2，申请权限
        final RxPermissions rxPermissions = new RxPermissions(this);
        if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !rxPermissions.isGranted(Manifest.permission.READ_EXTERNAL_STORAGE)
                || !rxPermissions.isGranted(Manifest.permission.ACCESS_NETWORK_STATE)) {
            DialogUtil.showDialog(this,
                    "请求权限",
                    "为了正常使用此应用，需要权限：设备信息，存储空间",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            requestRermission(rxPermissions);
                        }
                    });
        } else {
            mILanchPressenter.appStart();
        }
    }

    /**
     * 申请权限
     * @param rxPermissions
     */
    private void requestRermission(RxPermissions rxPermissions) {
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_NETWORK_STATE)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean granted) throws Exception {
                        if (granted) {//
                            mILanchPressenter.appStart();
                        } else {
                            Toast.makeText(LaunchActivity.this, "未开启必要权限，无法使用此应用！", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new BaseErrorConsumer(this));
    }

    /**
     * 展示版本更新提示框
     */
    public void showUpdateDialog(AppVersionBean versionBean){
        DialogUtil.showDialog(this, "最新版本" + versionBean.versionName,
                versionBean.appIntroduce, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
    }

}
