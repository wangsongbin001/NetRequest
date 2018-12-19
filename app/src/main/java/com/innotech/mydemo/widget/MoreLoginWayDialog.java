package com.innotech.mydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.innotech.mydemo.R;

public class MoreLoginWayDialog extends Dialog implements View.OnClickListener {

    private TextView tv_dialog_cancel;
    private FrameLayout fl_login_way_wechat, fl_login_way_sms, fl_login_way_account, fl_login_way_hw;
    HorizontalScrollView horizontalScrollView;

    LoginWaySelectCallBack mLoginWaySelectCallBack;
    public MoreLoginWayDialog(Context context, LoginWaySelectCallBack mLoginWaySelectCallBack) {
        super(context, R.style.AlphaDialog);
        this.mLoginWaySelectCallBack = mLoginWaySelectCallBack;
        initViews(context);
        initListeners();
    }

    private void initViews(Context context) {
        setContentView(R.layout.dialog_more_login_way);
        fl_login_way_wechat = findViewById(R.id.fl_login_way_wechat);
        fl_login_way_sms = findViewById(R.id.fl_login_way_sms);
        fl_login_way_account = findViewById(R.id.fl_login_way_account);
        fl_login_way_hw= findViewById(R.id.fl_login_way_hw);
        tv_dialog_cancel = findViewById(R.id.tv_dialog_cancel);
        horizontalScrollView = findViewById(R.id.h_scrollview);
        //调整位置
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        getWindow().setAttributes(layoutParams);
    }

    private void initListeners() {
        fl_login_way_hw.setOnClickListener(this);
        fl_login_way_wechat.setOnClickListener(this);
        fl_login_way_sms.setOnClickListener(this);
        fl_login_way_account.setOnClickListener(this);
        tv_dialog_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(null == mLoginWaySelectCallBack){
            dismiss();
            return;
        }
        if (R.id.fl_login_way_wechat == v.getId()) {
            mLoginWaySelectCallBack.slected(0);
        } else if (R.id.fl_login_way_sms == v.getId()) {
            mLoginWaySelectCallBack.slected(0);
        } else if (R.id.fl_login_way_account == v.getId()) {
            mLoginWaySelectCallBack.slected(0);
        } else if (R.id.fl_login_way_hw == v.getId()) {
            mLoginWaySelectCallBack.slected(0);
        } else if(R.id.tv_dialog_cancel == v.getId()){
            mLoginWaySelectCallBack.cancel();
        }
        dismiss();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void dismiss() {
        horizontalScrollView.post(new Runnable() {
            @Override
            public void run() {
                horizontalScrollView.fullScroll(HorizontalScrollView.FOCUS_LEFT);
            }
        });
        super.dismiss();
    }

    public interface LoginWaySelectCallBack {
        void slected(int way);

        void cancel();
    }
}
