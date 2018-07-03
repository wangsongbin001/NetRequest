package com.innotech.mydemo.bean;

/**
 * Created by admin on 2018/4/18.
 */

import android.databinding.ObservableField;
import android.support.v4.view.ViewPager;

/**
 * 记录底部菜单的状态
 *
 * @author allen
 */

public class BottomMenuBean {
    public ObservableField<Integer> index = new ObservableField<>();
    private ViewPager viewPager;

    public void setViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
    }

    public void setCurrentItem() {
        if (viewPager != null) {
            viewPager.setCurrentItem(index.get());
        }
    }

}
