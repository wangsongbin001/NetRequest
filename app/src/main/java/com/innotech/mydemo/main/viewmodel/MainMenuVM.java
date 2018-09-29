package com.innotech.mydemo.main.viewmodel;

import com.innotech.mydemo.bean.BottomMenuBean;
import com.innotech.netrequest.BaseAppCompatActivity;

/**
 * Created by admin on 2018/4/18.
 */

public class MainMenuVM {

    private BaseAppCompatActivity mActivity;

    public MainMenuVM(BaseAppCompatActivity mActivity){
        this.mActivity = mActivity;
    }

    public void switchMenu(BottomMenuBean bottomMenuBean, Integer index){
        bottomMenuBean.index.set(index);
        bottomMenuBean.setCurrentItem();
    }
}
