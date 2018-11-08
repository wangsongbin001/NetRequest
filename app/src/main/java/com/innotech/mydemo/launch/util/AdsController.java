package com.innotech.mydemo.launch.util;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.innotech.mydemo.R;
import com.innotech.mydemo.launch.LaunchActivity;
import com.innotech.mydemo.main.MainActivity;
import com.innotech.mydemo.main.MainFragmentActivity;

public class AdsController implements View.OnClickListener{
    public static final long TOTAL = 3000;
    private LaunchActivity mActivity;
    private ImageView imgAds;
    private TextView tvTips;
    VCountDownTimer mVCountDownTimer;
    private long current = TOTAL;

    public AdsController(LaunchActivity mActivity){
        this.mActivity = mActivity;
        initViews(mActivity);
    }

    private void initViews(Activity activity){
        imgAds = activity.findViewById(R.id.img_ad);
        tvTips = activity.findViewById(R.id.tv_tips);
        tvTips.setOnClickListener(this);
        imgAds.setOnClickListener(this);
    }

    public void showAds(String ads){
        Glide.with(mActivity)
                .load(ads)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        tvTips.setVisibility(View.VISIBLE);
                        countDownStart(TOTAL);
                        return false;
                    }

                })
                .into(imgAds);
    }

    /**
     * 倒计时三秒
     */
    public void countDownStart(long mm){
        mVCountDownTimer = new VCountDownTimer(mm, tvTips);
        mVCountDownTimer.start();
    }



    @Override
    public void onClick(View v) {
       switch(v.getId()){
           case R.id.img_ad:
               Intent intent = new Intent(mActivity, MainFragmentActivity.class);
               mActivity.startActivity(intent);
               if(mVCountDownTimer != null){
                   mVCountDownTimer.cancel();
               }
               break;
           case R.id.tv_tips:
               mActivity.finishCurrent();
               break;
       }
    }

    public long getCurrent(){
        return current;
    }

    public void onResume(){
        if(mVCountDownTimer != null){
            mVCountDownTimer = new VCountDownTimer(current + 1000, tvTips);
            mVCountDownTimer.start();
        }
    }

    public void onDestroy(){
        if(mVCountDownTimer != null){
            mVCountDownTimer.cancel();
        }
    }

    class VCountDownTimer extends CountDownTimer{

        private TextView textView;

        public VCountDownTimer(long millisInFuture, TextView textView) {
            super(millisInFuture, 1000);
            this.textView = textView;
        }

        @Override
        public void onTick(long millisUntilFinished) {
            long left = millisUntilFinished/1000;
            textView.setText("跳过" +left + "");
            current = millisUntilFinished;
        }

        @Override
        public void onFinish() {
            mActivity.finishCurrent();
        }

    }
}
