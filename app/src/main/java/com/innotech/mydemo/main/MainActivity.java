package com.innotech.mydemo.main;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.innotech.mydemo.R;
import com.innotech.mydemo.main.base.V2BaseActivity;
import com.innotech.mydemo.main.fragment.V2NewsFragment;
import com.innotech.mydemo.main.fragment.V2PersonFragment;
import com.innotech.mydemo.main.model.MainMenuItem;
import com.innotech.mydemo.main.presenter.IMainPresenter;
import com.innotech.mydemo.main.presenter.imp.MainPresenterImp;
import com.innotech.mydemo.main.util.MainUiController;
import com.innotech.mydemo.utils.ToastUtil;
import com.innotech.netrequest.util.LogUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;

/**
 * 1，tabs, 后端可配
 * 2，tabs与Fragment绑定点击事件
 */
public class MainActivity extends V2BaseActivity implements View.OnClickListener {

    @Bind(R.id.ll_tabs)
    LinearLayout llTabs;
    MainMenuItem currentItem;
    Map<Integer, MainMenuItem> mapTabs;
    FragmentManager fragmentManager;
    /**
     * mvp, 业务逻辑
     */
    IMainPresenter iMainPresenter;
    /**
     * UI，控制
     */
    MainUiController mainUIController;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void onActivityCreate(Bundle savedInstanceState) {
        super.onActivityCreate(savedInstanceState);
        iMainPresenter = new MainPresenterImp(this);
        mainUIController = new MainUiController(this);
        iMainPresenter.initData();
    }

    public void initViews(List<MainMenuItem> menus) {
        mapTabs = new HashMap();
        if (llTabs.getChildCount() > 0) {
            llTabs.removeAllViews();
        }
        //先添加Button;
        MainMenuItem item = null;
        for (int i = 0; i < menus.size(); i++) {
            item = menus.get(i);

            View view = LayoutInflater.from(this).inflate(R.layout.item_main_tab, null);
            MainMenuItem.ViewHolder viewHolder = new MainMenuItem.ViewHolder(view, this);
            viewHolder.updateUi(item);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT);
            lp.weight = 1;
            llTabs.addView(view, lp);
            item.setView(viewHolder);
        }
        //添加Fragments
        for (int i = 0; i < menus.size(); i++) {
            item = menus.get(i);
            if (MainMenuItem.TYPE_NEWS == item.type) {
                item.setFragment(new V2NewsFragment());
                mapTabs.put(item.type, item);
            } else if (MainMenuItem.TYPE_VIDEO == item.type) {
                item.setFragment(new V2NewsFragment());
                mapTabs.put(item.type, item);
            } else if (MainMenuItem.TYPE_TASK == item.type) {
                item.setFragment(new V2PersonFragment());
                mapTabs.put(item.type, item);
            } else if (MainMenuItem.TYPE_PERSON == item.type) {
                item.setFragment(new V2PersonFragment());
                mapTabs.put(item.type, item);
            }
        }

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        LogUtil.i("wang", "fragmentTransaction:" + fragmentTransaction);
        for (MainMenuItem every : mapTabs.values()) {
            fragmentTransaction.add(R.id.fl_content, every.fragment);
        }
        fragmentTransaction.commitAllowingStateLoss();

        currentItem = mapTabs.get(MainMenuItem.TYPE_NEWS);
        toggleFragment(currentItem);
    }

    public void toggleFragment(MainMenuItem currentItem) {
        if (!fragmentManager.isDestroyed()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                if (!isDestroyed() && !fragmentManager.isDestroyed()) {
                    FragmentTransaction fragmentTransaction1 = fragmentManager.beginTransaction();
                    fragmentTransaction1.show(currentItem.fragment);
                    for (MainMenuItem every : mapTabs.values()) {
                        if (!every.equals(currentItem)) {
                            fragmentTransaction1.hide(every.fragment);
                        }
                    }
                    fragmentTransaction1.commitAllowingStateLoss();
                } else {
                    return;
                }
            } else {
                FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.show(currentItem.fragment);
                for (MainMenuItem every : mapTabs.values()) {
                    if (!every.equals(currentItem)) {
                        fragmentTransaction2.hide(every.fragment);
                    }
                }
                fragmentTransaction2.commitAllowingStateLoss();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int type = (int) v.getTag();
        LogUtil.i("wang", "onClick " + type);
        currentItem = mapTabs.get(type);
        toggleFragment(currentItem);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        iMainPresenter = null;
        mainUIController = null;
    }
}
