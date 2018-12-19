package com.innotech.mydemo.test;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.innotech.mydemo.R;
import com.innotech.mydemo.launch.DownloadProgressDialogManager;
import com.innotech.mydemo.login.bind.BindWechatActivity;
import com.innotech.mydemo.utils.LogUtils;
import com.innotech.mydemo.utils.ToastUtil;
import com.innotech.mydemo.widget.FailureDialog;
import com.innotech.mydemo.widget.ForbidMoneyBookEnsureDialog;
import com.innotech.mydemo.widget.GuideDialog;
import com.innotech.mydemo.widget.IGuideBook;
import com.innotech.mydemo.widget.InterceptDialog;
import com.innotech.mydemo.widget.MoreLoginWayDialog;
import com.innotech.mydemo.widget.imp.FloatTouchCallBack;

import java.util.ArrayList;
import java.util.List;

public class TestActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        ProgressDialog progressDialog = new ProgressDialog(this);
    }

    private void initViews(){
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_show:
                IGuideBook.getInstance(this).showGuideBook();
                break;
            case R.id.btn_hide:
                IGuideBook.getInstance(this).hideGuideBook();
                break;
            case R.id.btn_release:
                IGuideBook.releaseBookGuide();
                break;
            case R.id.btn_init:
                IGuideBook.getInstance(this).setCallBack(new FloatTouchCallBack() {
                    @Override
                    public void onTouchClick() {
                        super.onTouchClick();
                        showDialog(null);
                    }
                });
                break;
            case R.id.btn_showdialog:
                showDialog(null);
                break;
            case R.id.btn_vis:
                IGuideBook.getInstance(this).setGuideVisiable(View.VISIBLE);
                break;
            case R.id.btn_gone:
                IGuideBook.getInstance(this).setGuideVisiable(View.GONE);
                break;
            case R.id.btn_update_dialog:
                if (dialog != null) {
                    List list = new ArrayList();
                    list.add("");
                    list.add("");
                    dialog.showNewList(list);
//                    showDialog(list);
                }
                break;
            case R.id.btn_forbid_dialog:
                ForbidMoneyBookEnsureDialog forbidMoneyBookEnsureDialog = new ForbidMoneyBookEnsureDialog(this);
                forbidMoneyBookEnsureDialog.setOnClickListener(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(DialogInterface.BUTTON_POSITIVE == which){
                            Toast.makeText(TestActivity.this, "点击确定",Toast.LENGTH_LONG).show();;
                        }else if(DialogInterface.BUTTON_NEGATIVE == which){
                            Toast.makeText(TestActivity.this, "点击取消",Toast.LENGTH_LONG).show();;
                        }
                    }
                });
                forbidMoneyBookEnsureDialog.show();
                break;
            case R.id.btn_intercept:
                showInterceptDialog();
                break;
            case R.id.btn_he:
                showHeDialog();
                break;
            case R.id.btn_failure:
                showFailureDialog();
                break;
            case R.id.btn_more_way:
                showMoreWayDialog();
                break;
            case R.id.btn_bindwx:
                startActivity(new Intent(this, BindWechatActivity.class));
                break;
            default:
                break;
        }
    }

    MoreLoginWayDialog moreLoginWayDialog = null;
    public void showMoreWayDialog(){
        if(null == moreLoginWayDialog) {
            moreLoginWayDialog = new MoreLoginWayDialog(this,
                    new MoreLoginWayDialog.LoginWaySelectCallBack() {
                        @Override
                        public void slected(int way) {
                            ToastUtil.showToastS(TestActivity.this, "" + way);
                        }

                        @Override
                        public void cancel() {
                            ToastUtil.showToastS(TestActivity.this, "cancel");
                        }
                    });
        }
        moreLoginWayDialog.show();
    }

    private void showFailureDialog(){
        FailureDialog failureDialog = new FailureDialog(this);
        failureDialog.show();
    }

    private void showHeDialog(){
        UnifiedAccountDialog unifiedAccountDialog = new UnifiedAccountDialog(this);
        unifiedAccountDialog.show();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        LogUtils.i("qtt","onBackPressed");
        showInterceptDialog();
    }

    private void showInterceptDialog(){
        InterceptDialog interceptDialog = new InterceptDialog(this);
        interceptDialog.setOnClickListener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(DialogInterface.BUTTON_POSITIVE == which){
                    Toast.makeText(TestActivity.this, "点击确定",Toast.LENGTH_LONG).show();;
                }else if(DialogInterface.BUTTON_NEGATIVE == which){
                    Toast.makeText(TestActivity.this, "点击取消",Toast.LENGTH_LONG).show();;
                }
            }
        });
        interceptDialog.setCanceledOnTouchOutside(false);
        interceptDialog.show();
    }

    GuideDialog dialog;

    private void showDialog(List list) {
        dialog = new GuideDialog(this, list);
        dialog.setCallBack(new GuideDialog.GuideDialogCallBack() {
            @Override
            public void onForbid() {
                Toast.makeText(TestActivity.this, "禁止", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onItemClick(int position) {
                Toast.makeText(TestActivity.this, "position" + position, Toast.LENGTH_LONG).show();
            }

            @Override
            public void onClose() {
                Toast.makeText(TestActivity.this, "关闭", Toast.LENGTH_LONG).show();
            }
        });
        dialog.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        IGuideBook.releaseBookGuide();
    }
}
