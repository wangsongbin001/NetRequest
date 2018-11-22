package com.innotech.mydemo.test;

import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;

import com.innotech.mydemo.R;

public class UnifiedAccountDialog extends Dialog{

    private TextView textView;
    VCountDownTimer vCountDownTimer;

    public UnifiedAccountDialog(Context context) {
        super(context, R.style.AlphaDialog);
        setContentView(R.layout.dialog_unifiedaccount);
        textView = findViewById(R.id.textview);
        textView.setEnabled(false);
    }

    @Override
    public void show() {
        super.show();
        vCountDownTimer = new VCountDownTimer(6000, textView);
        vCountDownTimer.start();
    }

    @Override
    public void dismiss() {
        super.dismiss();
        vCountDownTimer.cancel();
        vCountDownTimer = null;
    }


    class VCountDownTimer extends CountDownTimer {

        private TextView textView;

        public VCountDownTimer(long millisInFuture, TextView textView) {
            super(millisInFuture, 1000);
            this.textView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long left = millisUntilFinished/1000;
            textView.setText("确定(" +left + ")");

        }

        @Override
        public void onFinish() {
            textView.setText("确定");
            textView.setEnabled(true);
        }

    }
}
