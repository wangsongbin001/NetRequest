package com.innotech.mydemo.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.innotech.mydemo.MApp;

public class ScreenUtil {

    public static int WIDTH;
    public static int HEIGHT;
    public static float DENSITY;

    public ScreenUtil() {
    }

    public static String getOreintation(Context context) {
        return context == null ? "" : context.getResources().getConfiguration().orientation + "";
    }

    public static int dip2px(Context context, float dpValue) {
        float scale = 3.0F;
        if (DENSITY <= 0.0F && context != null) {
            scale = context.getResources().getDisplayMetrics().density;
        } else if (DENSITY > 0.0F) {
            scale = DENSITY;
        }

        return (int)(dpValue * scale + 0.5F);
    }

    public static int px2dip(Context context, float pxValue) {
        float scale = 3.0F;
        if (DENSITY <= 0.0F && context != null) {
            scale = context.getResources().getDisplayMetrics().density;
        } else if (DENSITY > 0.0F) {
            scale = DENSITY;
        }

        return (int)(pxValue / scale + 0.5F);
    }

    public static int dip2px(float dpValue) {
        float scale = MApp.get().getResources().getDisplayMetrics().density;
        return (int)(dpValue * scale + 0.5F);
    }

    public static float px2sp(float pxValue) {
        return px2sp((Context)null, pxValue);
    }

    public static float px2sp(Context context, float pxValue) {
        Resources r = getResources(context);
        float scale = r.getDisplayMetrics().scaledDensity;
        return pxValue / scale;
    }

    public static int getScreenWidth(Context context) {
        initScreenSize(context);
        return WIDTH;
    }

    public static int getScreenHeight(Context context) {
        initScreenSize(context);
        return HEIGHT;
    }

    public static int getDeviceWidth(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    public static int getDeviceHeight(Context context) {
        WindowManager wm = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return context.getResources().getDimensionPixelSize(resourceId);
    }

    public static int getStatusHeight2(Context context) {
        int statusBarHeight1 = -1;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight1 = context.getResources().getDimensionPixelSize(resourceId);
        }

        return statusBarHeight1;
    }

    public static int getStatusHeight(Activity activity) {
        Rect outRect = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(outRect);
        return outRect.top;
    }

    private static void initScreenSize(Context context) {
        if (WIDTH <= 0 || HEIGHT <= 0) {
            DisplayMetrics dm = context.getResources().getDisplayMetrics();
            WIDTH = dm.widthPixels;
            HEIGHT = dm.heightPixels;
            if (WIDTH > HEIGHT) {
                int temp = WIDTH;
                WIDTH = HEIGHT;
                HEIGHT = temp;
            }

            DENSITY = dm.density;
        }

    }

    public static int dp2px(float dpValue) {
        return dp2px((Context)null, dpValue);
    }

    public static int dp2px(Context context, float dpValue) {
        Resources r = getResources(context);
        float scale = (float)r.getDisplayMetrics().densityDpi;
        return (int)(dpValue * (scale / 160.0F) + 0.5F);
    }

    public static float px2dp(float pxValue) {
        return px2dp((Context)null, pxValue);
    }

    public static float px2dp(Context context, float pxValue) {
        Resources r = getResources(context);
        float scale = (float)r.getDisplayMetrics().densityDpi;
        return pxValue * 160.0F / scale;
    }

    public static int sp2px(float spValue) {
        return sp2px((Context)null, spValue);
    }

    public static int sp2px(Context context, float spValue) {
        Resources r = getResources(context);
        float scale = r.getDisplayMetrics().scaledDensity;
        return (int)(spValue * scale + 0.5F);
    }

    public static Resources getResources(Context context) {
        return context == null ? Resources.getSystem() : context.getResources();
    }

    public static boolean isFullScreen(Activity activity) {
        int flag = activity.getWindow().getAttributes().flags;
        return (flag & 1024) == 1024;
    }

    public static void printView(View view, ScreenUtil.ScreenCallBack listener) {
        view.post(new ScreenUtil.PrintScreenThread(view, listener));
    }

    public interface ScreenCallBack {
        void onScreenBitmapGet(Bitmap var1);
    }

    public static class PrintScreenThread implements Runnable {
        private View rootView;
        private ScreenUtil.ScreenCallBack callable;

        public PrintScreenThread(View view, ScreenUtil.ScreenCallBack callable) {
            this.rootView = view;
            this.callable = callable;
        }
        @Override
        public void run() {
            if (this.rootView.isHardwareAccelerated()) {
                this.rootView.setLayerType(1, (Paint)null);
            }

            this.rootView.setDrawingCacheEnabled(true);
            this.rootView.setDrawingCacheBackgroundColor(-1);
            this.rootView.buildDrawingCache();
            Bitmap bitmap = this.rootView.getDrawingCache();
            this.callable.onScreenBitmapGet(bitmap);
            this.rootView.setDrawingCacheEnabled(false);
        }
    }
}
