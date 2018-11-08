
package com.innotech.mydemo.widget.imp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.innotech.mydemo.R;
import com.innotech.mydemo.utils.ScreenUtil;
import com.innotech.mydemo.widget.IGuideBook;


public class GuideBookImp extends IGuideBook {

    public static final String tag = "qtt_gbvh";

    /**
     * ReadTimerView自定义控件的宽高，离屏幕上下左右的margin
     */
    private static final int GUIDE_BOOK_WIDTH = 46;
    private static final int GUIDE_BOOK_HOR_MARGIN = 8;
    private static final int GUIDE_BOOK_VER_MARGIN = 75;

    Activity activity;
    private int initLeftMargin;
    private int initBottomMargin;

    public static final int WHAT_SHOW_GUIDE_BOOK = 1001;
    public static final int WHAT_HIDE_GUIDE_BOOK = 1002;
    public static final long DELAY_HANDLER = 100;
    Handler mainHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case WHAT_SHOW_GUIDE_BOOK:
                    if (guideBookTouchListener != null) {
                        if (guideBookTouchListener.isAnimating()) {
                            mainHandler.sendEmptyMessageDelayed(WHAT_SHOW_GUIDE_BOOK, DELAY_HANDLER);
                        } else {
                            guideBookTouchListener.showGuideBook(iv_guidebook);
                        }
                    }
                    break;
                case WHAT_HIDE_GUIDE_BOOK:
                    if (guideBookTouchListener != null) {
                        if (guideBookTouchListener.isAnimating()) {
                            mainHandler.sendEmptyMessageDelayed(WHAT_HIDE_GUIDE_BOOK, DELAY_HANDLER);
                        } else {
                            guideBookTouchListener.hideGuideBook(iv_guidebook);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
    };

    public GuideBookImp(Activity context) {
        this.activity = context;
        initView(context);
    }

    private ViewGroup fl_content;
    private ImageView iv_guidebook;
    private GuideBookTouchListener guideBookTouchListener;

    private void initView(Activity activity) {
        Log.d(tag, "initView:" + activity);
        if (activity == null) {
            return;
        }
        fl_content = activity.findViewById(android.R.id.content);
        if (fl_content instanceof FrameLayout) {
            initGuideBookView(activity);
            initListener(activity);
            fl_content.addView(iv_guidebook);
        }
    }

    /**
     * 初始化控件
     *
     * @param activity
     */
    private void initGuideBookView(Activity activity) {
        iv_guidebook = new ImageView(activity);
//        FrameLayout.LayoutParams lp =
//                new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(ScreenUtil.dip2px(GUIDE_BOOK_WIDTH), FrameLayout.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.BOTTOM | Gravity.LEFT;

        initLeftMargin = ScreenUtil.getScreenWidth(activity) - ScreenUtil.dip2px(GUIDE_BOOK_HOR_MARGIN) - lp.width;
        initBottomMargin = ScreenUtil.dip2px(activity, GUIDE_BOOK_VER_MARGIN - 3);
        lp.setMargins(initLeftMargin, -ScreenUtil.getScreenWidth(activity), -100, initBottomMargin);
        iv_guidebook.setLayoutParams(lp);
        iv_guidebook.setImageResource(R.mipmap.icon_guidebook);
        iv_guidebook.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        iv_guidebook.setBackgroundColor(Color.BLACK);
    }

    /**
     * 初始化监听
     */
    private void initListener(Activity activity) {
        guideBookTouchListener = new GuideBookTouchListener(activity);
        iv_guidebook.setOnTouchListener(guideBookTouchListener);
    }

    @Override
    public void showGuideBook() {
        if (iv_guidebook != null && iv_guidebook.getVisibility() == View.VISIBLE) {
            mainHandler.removeMessages(WHAT_HIDE_GUIDE_BOOK);
            mainHandler.removeMessages(WHAT_SHOW_GUIDE_BOOK);
//            mainHandler.sendEmptyMessageDelayed(WHAT_SHOW_GUIDE_BOOK, DELAY_HANDLER);
            mainHandler.sendEmptyMessage(WHAT_SHOW_GUIDE_BOOK);
        }
    }

    @Override
    public void removeGuideBook() {
        if (fl_content != null && iv_guidebook != null) {
            fl_content.removeView(iv_guidebook);
        }
    }

    @Override
    public void hideGuideBook() {
        if (iv_guidebook != null && iv_guidebook.getVisibility() == View.VISIBLE) {
            mainHandler.removeMessages(WHAT_HIDE_GUIDE_BOOK);
            mainHandler.removeMessages(WHAT_SHOW_GUIDE_BOOK);
//            mainHandler.sendEmptyMessageDelayed(WHAT_HIDE_GUIDE_BOOK, DELAY_HANDLER);
            mainHandler.sendEmptyMessage(WHAT_HIDE_GUIDE_BOOK);
        }
    }

    @Override
    public void setCallBack(FloatTouchCallBack floatTouchCallBack) {
        if (floatTouchCallBack != null) {
            guideBookTouchListener.setmCallBack(floatTouchCallBack);
        }
    }

    @Override
    public void initPos(int marginLeft, int marginBottom) {
        if (marginLeft < 0) {
            marginLeft = initLeftMargin;
        }
        if (marginBottom < 0) {
            marginBottom = initBottomMargin;
        }
    }

    @Override
    public void setGuideVisiable(int visiable) {
        if (iv_guidebook != null) {
            switch (visiable) {
                case View.VISIBLE:
                case View.GONE:
                case View.INVISIBLE:
                    iv_guidebook.setVisibility(visiable);
                    break;
            }
        }
    }
}
