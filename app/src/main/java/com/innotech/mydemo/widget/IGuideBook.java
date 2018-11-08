package com.innotech.mydemo.widget;

import android.app.Activity;

import com.innotech.mydemo.widget.imp.FloatTouchCallBack;
import com.innotech.mydemo.widget.imp.GuideBookImp;


public abstract class IGuideBook {

    private static IGuideBook iGuideBook;

    public static IGuideBook getInstance(Activity activity){
        if(iGuideBook == null){
            synchronized (IGuideBook.class){
                if(iGuideBook == null){
                    iGuideBook = new GuideBookImp(activity);
                }
            }
        }
        return iGuideBook;
    }

    public static IGuideBook get(){
        return iGuideBook;
    }

    public static void releaseBookGuide(){
        if(iGuideBook != null){
            iGuideBook.removeGuideBook();
            iGuideBook = null;
        }
    }

    /**
     * 展示秘籍
     */
    public abstract void showGuideBook();

    /**
     * 移除秘籍
     */
    protected abstract void removeGuideBook();

    /**
     * 隐藏秘籍
     */
    public abstract void hideGuideBook();

    /**
     * 设置回调
     * @param floatTouchCallBack
     */
    public abstract void setCallBack(FloatTouchCallBack floatTouchCallBack);

    /**
     * 初始化位置
     */
    public abstract void initPos(int marginLeft, int marginBottom);

    public abstract void setGuideVisiable(int visiable);
}
