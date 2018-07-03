package com.innotech.mydemo.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.innotech.mydemo.R;
import com.innotech.mydemo.adapter.MyPagerAdapter;
import com.innotech.mydemo.bean.BottomMenuBean;
import com.innotech.mydemo.databinding.ActivityMainMenuBinding;
import com.innotech.mydemo.fragment.MyFragment;
import com.innotech.mydemo.fragment.ProjectFragment;
import com.innotech.mydemo.viewmodel.MainMenuVM;
import com.innotech.netrequest.BaseActivity;
import com.innotech.netrequest.BaseAppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/18.
 */

public class MainMenuActivity extends BaseAppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainMenuBinding activityMainMenuBinding = DataBindingUtil.setContentView(this, R.layout.activity_main_menu);

        MainMenuVM mainMenuVM = new MainMenuVM(this);
        activityMainMenuBinding.setMainmenuVM(mainMenuVM);

        BottomMenuBean bottomMenuBean = new BottomMenuBean();
        bottomMenuBean.setViewPager(activityMainMenuBinding.vp);
        bottomMenuBean.index.set(0);
        activityMainMenuBinding.setBottomMenu(bottomMenuBean);

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new ProjectFragment());
        fragmentList.add(new MyFragment());

        activityMainMenuBinding.vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager(), fragmentList));

    }
}
