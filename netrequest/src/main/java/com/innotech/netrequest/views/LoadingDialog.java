package com.innotech.netrequest.views;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.animation.Animation;

import com.innotech.netrequest.R;


/**
 * Created by admin on 2018/4/9.
 */

public class LoadingDialog {
    private static ProgressDialog progressDialog;
    private static Animation anim;

    public static boolean isShow() {
        return progressDialog != null && progressDialog.isShowing();
    }

    public static ProgressDialog createProgressDialog(Context paramContext, String text) {
        progressDialog = new ProgressDialog(paramContext, R.style.dialog_custom);
        if (text == null || "".equals(text))
            text = "加载中...";
        progressDialog.setMessage(text);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        return progressDialog;
    }

    public static void dismiss(){
        if(!isShow()){
            return;
        }
        if (anim != null) {
            anim.cancel();
            anim = null;
        }
        if(progressDialog != null){
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    public static ProgressDialog show(Context context) {
        if (isShow() && progressDialog.getContext() == context) {
            return progressDialog;
        }
        dismiss();

        progressDialog = createProgressDialog(context, "");
        progressDialog.show();

        ProgressDialogSetting(progressDialog);
        return progressDialog;
    }

    /**
     * 展示loading
     *
     * @param paramContext
     * @param text         dialog message
     */
    public static ProgressDialog show(Context paramContext, String text) {

        dismiss();

        progressDialog = createProgressDialog(paramContext, text);
        progressDialog.show();

        ProgressDialogSetting(progressDialog);
        return progressDialog;

    }

    /**
     * 展示loading
     *
     * @param paramContext
     * @param paramBoolean 是否可以手动取消
     * @param text         dialog message
     */
    public static ProgressDialog show(Context paramContext, String text, boolean paramBoolean) {

        dismiss();

        progressDialog = createProgressDialog(paramContext, text);
        progressDialog.setCancelable(paramBoolean);
        progressDialog.show();

        ProgressDialogSetting(progressDialog);
        return progressDialog;
    }

    public static void ProgressDialogSetting(ProgressDialog progressDialog) {
        progressDialog.setContentView(R.layout.common_loading);
    }
}
