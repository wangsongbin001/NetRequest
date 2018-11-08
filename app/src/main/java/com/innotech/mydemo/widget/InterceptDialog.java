package com.innotech.mydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.innotech.mydemo.R;

public class InterceptDialog extends Dialog implements View.OnClickListener{

    private TextView tv_refuse;
    private ImageView iv_agree;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public InterceptDialog(@NonNull Context context) {
        super(context, R.style.AlphaDialog);
        initViews(context);
        initListeners();
    }

    private void initViews(Context context){
        setContentView(R.layout.dialog_intercept);
        tv_refuse = findViewById(R.id.tv_refuse);
        iv_agree = findViewById(R.id.iv_agree);
    }

    private void initListeners(){
        tv_refuse.setOnClickListener(this);
        iv_agree.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(R.id.tv_refuse == v.getId()){
            onClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
        }else if(R.id.iv_agree == v.getId()){
            onClickListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
        }
        dismiss();
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}
