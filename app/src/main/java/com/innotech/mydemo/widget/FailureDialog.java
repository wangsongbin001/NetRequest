package com.innotech.mydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.innotech.mydemo.R;

public class FailureDialog extends Dialog implements View.OnClickListener{

    TextView tvTitle;
    TextView tvTips;
    TextView tvSure;

    public FailureDialog(@NonNull Context context) {
        super(context, R.style.AlphaDialog);
        initViews(context);
    }

    private void initViews(Context context){
        setContentView(R.layout.dialog_failure);
        tvTitle = findViewById(R.id.tv_title);
        tvTips = findViewById(R.id.tv_tips);
        tvSure = findViewById(R.id.tv_sure);
        tvSure.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }

    public void setTvTitleVisiable(int visiable){
        if(View.VISIBLE == visiable
                || View.GONE == visiable
                || View.INVISIBLE == visiable){
            tvTitle.setVisibility(visiable);
        }
    }
}
