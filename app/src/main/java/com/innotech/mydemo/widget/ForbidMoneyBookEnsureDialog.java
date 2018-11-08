package com.innotech.mydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.innotech.mydemo.R;

public class ForbidMoneyBookEnsureDialog extends Dialog implements View.OnClickListener {

    private TextView tv_sure;
    private TextView tv_cancel;

    private OnClickListener onClickListener;

    public void setOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public ForbidMoneyBookEnsureDialog(@NonNull Context context) {
        super(context, R.style.AlphaDialog);
        initViews(context);
        initListeners();
    }

    private void initViews(Context context) {
        setContentView(R.layout.dialog_forbid_moneybook);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_sure = findViewById(R.id.tv_ensure);
    }

    private void initListeners() {
        tv_sure.setOnClickListener(this);
        tv_cancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                if(onClickListener != null){
                    onClickListener.onClick(this, DialogInterface.BUTTON_NEGATIVE);
                }
                break;
            case R.id.tv_ensure:
                if(onClickListener != null){
                    onClickListener.onClick(this, DialogInterface.BUTTON_POSITIVE);
                }
                break;
            default:
                break;
        }
    }
}
