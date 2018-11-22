package com.innotech.mydemo.main.model;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.innotech.mydemo.R;
import com.innotech.mydemo.main.base.V2BaseFragment;

public class MainMenuItem {
    public final static int TYPE_NEWS = 0;
    public final static int TYPE_VIDEO = 1;
    public final static int TYPE_TASK = 2;
    public final static int TYPE_PERSON = 3;

    /**
     * 名称
     */
    public String name;
    /**
     * 类型
     */
    public int type;
    /**
     * 对应的Fragment
     */
    public V2BaseFragment fragment;
    /**
     * 对应的tab,button;
     */
    public ViewHolder viewTab;


    public MainMenuItem setName(String name) {
        this.name = name;
        return this;
    }

    public MainMenuItem setType(int type) {
        this.type = type;
        return this;
    }

    public MainMenuItem setView(ViewHolder viewTab) {
        this.viewTab = viewTab;
        return this;
    }

    public MainMenuItem setFragment(V2BaseFragment baseFragment) {
        this.fragment = baseFragment;
        return this;
    }

    public static class ViewHolder {
        View view;
        Button btn;
        TextView tips;

        public ViewHolder(View view, View.OnClickListener onClickListener) {
            this.view = view;
            btn = view.findViewById(R.id.btn);
            tips = view.findViewById(R.id.tv_tips);
            btn.setOnClickListener(onClickListener);
        }

        public void updateUi(MainMenuItem item){
            if(null == item){
                return;
            }
            btn.setText(item.name);
            btn.setTag(item.type);
        }

        public void setBtnText(String text) {
            this.btn.setText(text);
        }

        public void setTipVisiable(int visiable) {
            if (visiable == View.GONE
                    || visiable == View.VISIBLE
                    || visiable == View.INVISIBLE) {
                this.tips.setVisibility(visiable);
            }
        }
    }
}