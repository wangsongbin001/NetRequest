package com.innotech.mydemo.widget.imp;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;

import com.innotech.mydemo.utils.LogUtils;
import com.innotech.mydemo.utils.ScreenUtil;

public class GuideBookTouchListener implements View.OnTouchListener {

    private static final int MIN_CLICK_DELAY_TIME = 1000;
    private static long lastClickTime;
    TranslateAnimation animation;
    int lastX;
    int lastY;
    int screenWidth;
    int screenHeight;
    int statusHeight;
    long time;
    boolean click = false;
    boolean isMove = false;
    int spaceX;
    int spaceY;
    int homeRedY;
    boolean moveTop = false;
    int indexX;
    int indexY;
    private WindowManager.LayoutParams mWmParams;
    private WindowManager mWindowManager;
    FloatTouchCallBack mCallBack;
    private volatile boolean isAnimating = false;
    public boolean isAnimating(){
        return isAnimating;
    }

    public GuideBookTouchListener(Context context) {
        this.initListener(context);
    }

    public GuideBookTouchListener(Context context, WindowManager.LayoutParams mWmParams) {
        this.mWmParams = mWmParams;
        this.mWindowManager = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        this.initListener(context);
    }

    private void initListener(Context context) {
        this.screenWidth = ScreenUtil.getScreenWidth(context);
        this.screenHeight = ScreenUtil.getScreenHeight(context);
        this.statusHeight = 0;
        this.spaceX = ScreenUtil.dip2px(context, 8);
        this.spaceY = ScreenUtil.dip2px(context, 75.0F);
        this.homeRedY = ScreenUtil.dip2px(context, 165.0F);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int moveX;
        int moveY;
        switch (event.getAction()) {
            case 0:
                this.click = true;
                this.time = System.currentTimeMillis();
                this.lastX = (int) event.getRawX();
                this.lastY = (int) event.getRawY();
                if (null != this.mCallBack) {
                    this.mCallBack.onTouchDonw();
                }
                break;
            case 1:
                if (null != this.mCallBack) {
                    this.mCallBack.onTouchUp();
                }

                this.click = System.currentTimeMillis() - this.time < 200L;
                v.performClick();
                if (null != this.mCallBack) {
                    if (this.click && !this.isMove) {
                        long curClickTime = System.currentTimeMillis();
                        if (curClickTime - lastClickTime >= 1000L) {
                            lastClickTime = curClickTime;
                            this.mCallBack.onTouchClick();
                        }
                    } else if (this.isMove) {
                        this.mCallBack.onTouchMove();
                    }
                }

                if (this.isMove) {
                    this.indexX = this.lastX >= this.screenWidth / 2 ? this.screenWidth - v.getWidth() - this.spaceX : this.spaceX;
                    this.indexY = this.lastY - this.statusHeight < 2 * v.getHeight() ? this.screenHeight - 2 * v.getHeight() - this.statusHeight : (this.screenHeight - this.lastY - this.spaceY < v.getHeight() / 2 ? this.spaceY : this.screenHeight - this.lastY - v.getHeight() / 2);
                    if (this.moveTop && this.indexY < this.homeRedY && this.indexX > this.spaceX) {
                        this.indexY = this.homeRedY;
                    }

                    Log.d("updateViewPositionUp", "indexY: " + this.indexY + "...lastY:" + this.lastY);
                    moveX = this.indexX + v.getWidth() / 2 - this.lastX;
                    moveY = this.screenHeight - this.lastY - this.indexY - v.getHeight() / 2;
                    this.upDataViewAnimation(v, moveX, moveY);
                    this.isMove = false;
                }
                break;
            case 2:
                if (this.isMove) {
                    this.lastX = this.lastX < 0 ? 0 : (this.lastX > this.screenWidth ? (this.lastX = this.screenWidth) : this.lastX);
                    this.lastY = this.lastY < 0 ? 0 : (this.lastY > this.screenHeight ? (this.lastY = this.screenHeight) : this.lastY);
                    moveX = this.lastX - v.getWidth() / 2;
                    moveY = this.screenHeight - this.lastY - v.getHeight() / 2;
                    this.updateViewPosition(v, moveX, moveY);
                } else if (Math.abs((int) event.getRawX() - this.lastX) > 5 || Math.abs((int) event.getRawY() - this.lastY) > 5) {
                    this.isMove = true;
                }

                Log.d("updateViewPosition move", "move" + this.isMove + "...lastX:" + this.lastX + "..." + (this.lastX - v.getWidth() / 2) + "...lastY:" + this.lastY + "..." + (this.screenHeight - this.lastY - v.getHeight() / 2));
                this.lastX = (int) event.getRawX();
                this.lastY = (int) event.getRawY();
                if (null != this.mCallBack) {
                    this.mCallBack.onTouchMove();
                }
            default:
                break;
        }

        return true;
    }

    public void upDataViewAnimation(final View v, int moveX, int moveY) {
        this.animation = new TranslateAnimation(0.0F, (float) moveX, 0.0F, (float) moveY);
        this.animation.setDuration(200L);
        this.animation.setFillEnabled(true);
        this.animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.clearAnimation();
                GuideBookTouchListener.this.updateViewPosition(v, GuideBookTouchListener.this.indexX, GuideBookTouchListener.this.indexY);
//                PreferenceUtil.setParam(v.getContext(), "read_timer_position_x", GuideBookTouchListener.this.indexX);
//                PreferenceUtil.setParam(v.getContext(), "read_timer_position_y", GuideBookTouchListener.this.indexY);
                if (GuideBookTouchListener.this.mCallBack != null) {
                    GuideBookTouchListener.this.mCallBack.onAnimationEnd();
                }

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        v.startAnimation(this.animation);
    }

    private void updateViewPosition(View v, int indexX, int indexY) {
        if (null != this.mWmParams && null != this.mWindowManager) {
            this.mWmParams.x = indexX;
            this.mWmParams.y = indexY;
            this.mWindowManager.updateViewLayout(v, this.mWmParams);
        } else if (v.getLayoutParams() instanceof android.widget.FrameLayout.LayoutParams) {
            android.widget.FrameLayout.LayoutParams params = (android.widget.FrameLayout.LayoutParams) v.getLayoutParams();
            params.setMargins(indexX, -screenHeight, -screenWidth, indexY);
            v.setLayoutParams(params);
        }

    }

    public void setmCallBack(FloatTouchCallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    public void setMoveScreenHeight(View view, boolean moveTop) {
        this.moveTop = moveTop;
        if (moveTop && this.indexY < this.homeRedY && this.indexX > this.spaceX) {
            this.indexY = this.homeRedY;
            int[] location = new int[2];
            view.getLocationOnScreen(location);
            int moveY = this.screenHeight - location[1] - view.getHeight() - this.indexY;
            this.upDataViewAnimation(view, 0, moveY);
        }

    }

    public void setMoveScreenHeight(View view) {
        if (this.indexY < this.screenHeight / 2) {
            if (this.indexX <= 0) {
                this.indexX = this.spaceX;
            }

            int[] location = new int[2];
            view.getLocationOnScreen(location);
            this.indexY = this.screenHeight / 2;
            int moveY = this.screenHeight - location[1] - view.getHeight() - this.indexY;
            this.upDataViewAnimation(view, 0, moveY);
        }

    }

    /**
     * @param view
     */
    public void showGuideBook(View view) {
        if (view == null) {
            return;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        LogUtils.i("qtt", "indexX:" + this.indexX + ",screenwidth:" + this.screenWidth);
        if (this.indexX <= 0) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            this.indexX = layoutParams.leftMargin;
            this.indexY = layoutParams.bottomMargin;
            LogUtils.i("qtt", "layoutparams:" + layoutParams.leftMargin);
        }
        LogUtils.i("qtt", "location:" + location[0] + " " + location[1] + ", indexX" + this.indexX + ", spaceX:" + spaceX);
        this.indexX = this.indexX >= this.screenWidth / 2 ? this.screenWidth - view.getWidth() - this.spaceX : this.spaceX;
        int moveX = this.indexX - location[0];
        LogUtils.i("qtt", "moveX:" + moveX + ",indexX:" + this.indexX + ",view.getWidth:" + view.getWidth());
        this.upDataViewAnimation(view, moveX, 0, new DecelerateInterpolator());
    }

    public void hideGuideBook(View view) {
        if (view == null) {
            return;
        }
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        if (this.indexX <= 0) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) view.getLayoutParams();
            this.indexX = layoutParams.leftMargin;
            this.indexY = layoutParams.bottomMargin;
            LogUtils.i("qtt", "layoutparams:" + layoutParams.leftMargin);
        }
        LogUtils.i("qtt", "location:" + location[0] + " " + location[1]);
        this.indexX = this.indexX >= this.screenWidth / 2 ? this.screenWidth - this.spaceX : this.spaceX - view.getWidth();
        int moveX = this.indexX - location[0];
        this.upDataViewAnimation(view, moveX, 0, new AccelerateInterpolator());
    }

    public void upDataViewAnimation(final View v, int moveX, int moveY, Interpolator interpolator) {
        this.animation = new TranslateAnimation(0.0F, (float) moveX, 0.0F, (float) moveY);
        this.animation.setDuration(100L);
        this.animation.setFillEnabled(true);
        this.animation.setInterpolator(interpolator);
        this.animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                v.clearAnimation();
                GuideBookTouchListener.this.updateViewPosition(v, GuideBookTouchListener.this.indexX, GuideBookTouchListener.this.indexY);
//                PreferenceUtil.setParam(v.getContext(), "read_timer_position_x", GuideBookTouchListener.this.indexX);
//                PreferenceUtil.setParam(v.getContext(), "read_timer_position_y", GuideBookTouchListener.this.indexY);
                if (GuideBookTouchListener.this.mCallBack != null) {
                    GuideBookTouchListener.this.mCallBack.onAnimationEnd();
                }
                isAnimating = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        v.startAnimation(this.animation);
        isAnimating = true;
    }

}
