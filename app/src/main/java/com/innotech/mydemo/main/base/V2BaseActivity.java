package com.innotech.mydemo.main.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import butterknife.ButterKnife;

/**
 * BaseActivity 封装
 * 1，隐藏TitleBar
 * 2，Activity + 多Fragment
 * 2.1,support v24.0.0后解决了fragment重叠的问题(未保存mHidden属性)。
 *
 */
public class V2BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏TitleBar
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        if(0 < getLayoutId()){
            setContentView(getLayoutId());
        }
        ButterKnife.bind(this);
        onActivityCreate(savedInstanceState);
        //初始化Fragment
        if(savedInstanceState == null){
            initFragments();
        }
    }

    protected void initFragments(){}

    public void onActivityCreate(Bundle savedInstanceState){}

    public int getLayoutId(){
        return 0;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
