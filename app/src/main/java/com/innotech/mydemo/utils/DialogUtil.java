package com.innotech.mydemo.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.innotech.mydemo.R;

public class DialogUtil {

    public static AlertDialog showDialog(Context context, String title, String message,
                                         DialogInterface.OnClickListener positiveListener, String positiveText){
        return showDialog(context, title, message,
                null,
                positiveListener,
                null,
                positiveText,
                null,
                false,
                false);
    }

    public static AlertDialog showDialog(Context context, String title, String message,
                                 DialogInterface.OnClickListener positiveListener,
                                 DialogInterface.OnClickListener negativeListener){
        return showDialog(context, title, message,
                null,
                positiveListener, negativeListener,
                "确定", "取消",
                true, true);
    }

    public static AlertDialog showDialog(Context context, String title, String message,
                                  View body,
                                  final DialogInterface.OnClickListener positiveListener,
                                  final DialogInterface.OnClickListener negativeListener,
                                  String positiveText,
                                  String negativeText,
                                  boolean touchOutside,
                                  boolean cancelable){
        final AlertDialog alertDialog = new AlertDialog.Builder(context, R.style.dialog_custom).create();
        alertDialog.show();

        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_common_custom);
        TextView tvTitle = (TextView) window.findViewById(R.id.tv_title);
        FrameLayout flBody = (FrameLayout) window.findViewById(R.id.fl_body);
        FrameLayout flBottomBtns = (FrameLayout) window.findViewById(R.id.fl_bottom_btns);
        TextView tvTxt = (TextView) window.findViewById(R.id.tv_txt);
        TextView tvSure = (TextView) window.findViewById(R.id.btn_custom_dialog_sure);
        TextView tvCancle = (TextView) window.findViewById(R.id.btn_custom_dialog_cancle);
        ImageView ivCancle = (ImageView) window.findViewById(R.id.iv_cancle);

        if (!TextUtils.isEmpty(title)) {// 包含标题
            tvTitle.setText(title);
        } else {// 不包含标题
            tvTitle.setVisibility(View.GONE);
            ViewGroup.MarginLayoutParams lp = (ViewGroup.MarginLayoutParams) flBody
                    .getLayoutParams();
            lp.topMargin = 0;
            flBody.setLayoutParams(lp);
        }

        if (body != null) {// 包含内容View的时候，显示内容View
            tvTxt.setVisibility(View.GONE);
            flBody.addView(body);
        } else if (!TextUtils.isEmpty(message)) {// 包含内容文本的时候，显示内容文本
            tvTxt.setText(message);
        } else {
            tvTxt.setVisibility(View.GONE);
        }

        //如果没有取消按钮，不显示取消按钮
        if (TextUtils.isEmpty(negativeText) && !TextUtils.isEmpty(positiveText)) {
            tvCancle.setVisibility(View.GONE);
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) tvSure.getLayoutParams();
            params.width = CommonUtil.dp2px(context, 171);
            params.gravity = Gravity.CENTER_HORIZONTAL;
            tvSure.setLayoutParams(params);
        } else if (TextUtils.isEmpty(negativeText) && TextUtils.isEmpty(positiveText)) {
            flBottomBtns.setVisibility(View.GONE);
        }

        tvSure.setText(positiveText);
        tvCancle.setText(negativeText);


        View.OnClickListener vPositiveListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (positiveListener == null) {
                    alertDialog.dismiss();
                } else {
                    positiveListener.onClick(alertDialog, DialogInterface.BUTTON_POSITIVE);
                }
            }
        };

        tvSure.setOnClickListener(vPositiveListener);
        View.OnClickListener vNegativeListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (negativeListener == null) {
                    alertDialog.cancel();
                } else {
                    negativeListener.onClick(alertDialog, DialogInterface.BUTTON_NEGATIVE);
                }
            }
        };

        tvCancle.setOnClickListener(vNegativeListener);
        ivCancle.setOnClickListener(vNegativeListener);

        WindowManager.LayoutParams params = window.getAttributes();
        params.width = context.getResources().getDisplayMetrics().widthPixels
                - CommonUtil.dp2px(context, 30 * 2);
        window.setAttributes(params);
        alertDialog.setCanceledOnTouchOutside(touchOutside);
        alertDialog.setCancelable(cancelable);
        return alertDialog;
    }
}
