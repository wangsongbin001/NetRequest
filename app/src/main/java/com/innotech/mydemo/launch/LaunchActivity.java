package com.innotech.mydemo.launch;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.innotech.mydemo.R;
import com.innotech.mydemo.launch.bean.AdBean;
import com.innotech.mydemo.launch.bean.DownloadBean;
import com.innotech.mydemo.launch.presenter.ILanchPresenter;
import com.innotech.mydemo.launch.presenter.imp.LaunchPresenterImp;
import com.innotech.mydemo.launch.util.AdsController;
import com.innotech.mydemo.launch.util.DownloadProgressDialogController;
import com.innotech.mydemo.launch.util.DownloadUtil;
import com.innotech.mydemo.main.MainActivity;
import com.innotech.mydemo.main.MainFragmentActivity;
import com.innotech.mydemo.utils.CommonUtil;
import com.innotech.mydemo.utils.DialogUtil;
import com.innotech.netrequest.BaseAppCompatActivity;
import com.innotech.netrequest.net.BaseErrorConsumer;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * 启动页，
 * 1，权限申请
 * 2，版本更新
 * 2.1，版本获取
 * 2.2，新版的apk下载（进度条没更新）
 * 2.3，新版apk的安装
 * 3，新手引导
 * 4，广告展示
 * 5，配置加载
 */
public class LaunchActivity extends BaseAppCompatActivity {

    @Bind(R.id.btn_request)
    Button btnRequest;
    //业务层控制
    ILanchPresenter mILanchPressenter;
    //版本更新封装
    DownloadProgressDialogController downloadProgressDialogManager;
    //广告封装
    AdsController mAdsController;
    @Bind(R.id.tv_version)
    TextView tvVersion;
    @Bind(R.id.fl_bg)
    FrameLayout flBg;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);
        ButterKnife.bind(this);
        initViews();
        mILanchPressenter = new LaunchPresenterImp(this);
        downloadProgressDialogManager = new DownloadProgressDialogController(this);
        mAdsController = new AdsController(this);
        checkPermission();
    }

    private void initViews() {
        tvVersion.setText("当前版本:" + CommonUtil.getAppVersionName(getApplicationContext()));
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
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
                    }, "下一步");
        } else {
            mILanchPressenter.appStart();
        }
    }

    /**
     * 申请权限
     *
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
    public void showUpdateDialog(DownloadBean downloadBean) {
        //判断文件是否已经在下载
        if (downloadProgressDialogManager.checkIsDownloading(downloadBean.getDownloadUrl())) {
            return;
        }
        //判断文件是否下载完成
        if (!downloadBean.isDownloaded()) {
            showNewVerDialog(downloadBean);
        } else {
            showInstallDialog(downloadBean);
        }
    }

    public void showInstallDialog(final DownloadBean downloadBean) {
        DialogUtil.showDialog(this, "最新版本：" + downloadBean.getVersionName(),
                "检测到你已下载了新版本，请安装更新", null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DownloadUtil.installApkByGuide(LaunchActivity.this,
                                downloadBean.getLocationPath());
                        finish();
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showAds(mILanchPressenter.getAdBean());
                    }
                }, "立即安装", "", true, true);
    }

    public void showNewVerDialog(final DownloadBean downloadBean) {
        DialogUtil.showDialog(this, "最新版本:" + downloadBean.getVersionName(),
                downloadBean.getIntroduce(), null, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        DownloadUtil.startDownload(LaunchActivity.this,
                                downloadBean.getDownloadUrl(),
                                downloadBean.getAppFileName(),
                                downloadBean.getAppSize());
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                downloadProgressDialogManager.startProgressDialog();
                            }
                        }, 100);
                    }
                }, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        showAds(mILanchPressenter.getAdBean());
                    }
                }, "立即更新", "", true, true);
    }

    public void showAds(AdBean adBean) {
        if (adBean != null) {
            mAdsController.showAds(adBean.adImgUrl);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdsController.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        downloadProgressDialogManager.onDestroy();
        mAdsController.onDestroy();
    }

    public void finishCurrent() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
