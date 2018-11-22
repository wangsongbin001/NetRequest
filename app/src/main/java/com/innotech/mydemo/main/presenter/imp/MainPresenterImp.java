package com.innotech.mydemo.main.presenter.imp;

import com.innotech.mydemo.main.MainActivity;
import com.innotech.mydemo.main.model.MainMenuItem;
import com.innotech.mydemo.main.presenter.IMainPresenter;

import java.util.ArrayList;
import java.util.List;

public class MainPresenterImp implements IMainPresenter{
    MainActivity mainActivity;

    public MainPresenterImp(MainActivity mainActivity){
        this.mainActivity = mainActivity;
    }

    @Override
    public void initData() {
        List<MainMenuItem> menus = new ArrayList<>();
        menus.add(new MainMenuItem().setName("新闻").setType(0));
        menus.add(new MainMenuItem().setName("视频").setType(1));
        menus.add(new MainMenuItem().setName("新任务").setType(2));
        menus.add(new MainMenuItem().setName("我").setType(3));
        mainActivity.initViews(menus);
    }
}
