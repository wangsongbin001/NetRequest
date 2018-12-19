package com.innotech.mydemo.main.base;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;

import com.innotech.mydemo.utils.LogUtils;
import com.innotech.netrequest.util.LogUtil;

/**
 * Activity + 多Fragment的框架，对于在使用Fragment中遇到的坑，
 * 总结归纳，并将解决方法进行封装
 * 1，getActivity为null, fragment在dettach后为null
 * 2，Can not perform this action after onSaveInstanceState（在activity的onSave与 resume 间提交Fragment事务）
 * 2.1，使用commitAllowingStateLoss()， 可能导致本次提交无效
 * 2.2，利用startActivityForResult 与onActivityResult避免
 * 3，Fragment的重叠异常，
 * 3.1，support v24.0.0以前，发生内存重启的时候，使用FragmentManager没有保存FragmentState的mHidden属性。
 * 3.2，还有重复加载Fragment问题（savedInstanceState == null, 再去加载）
 * 4，Fragment嵌套的那些坑，getFragmentManager 与 getChildFragmentManager
 * 5，多个Fragment同时出栈的深坑BUG，FragmentManager,mAvailIndeices popBackStack并不是remove而是置为null
 *    support-v25.4.0，已经修复了。
 * 6，Fragment的转场动画
 * 参考：https://github.com/gpfduoduo/android-article/blob/master/Activity%20%2B%20%E5%A4%9AFrament%20%E4%BD%BF%E7%94%A8%E6%97%B6%E7%9A%84%E4%B8%80%E4%BA%9B%E5%9D%91.md
 */
public class V2BaseFragment extends Fragment{
    /**
     * 1,可能会出现内存泄漏，但是异步任务本身也会造成内存泄漏
     */
    public Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mActivity = (Activity) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.i("wang", "onResume");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtils.i("wang", "onHiddenChanged-hidden:" + hidden);
    }

    public void onVisiableChanged(boolean show){

    }

}
