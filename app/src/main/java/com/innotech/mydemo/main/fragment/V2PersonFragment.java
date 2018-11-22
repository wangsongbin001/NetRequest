package com.innotech.mydemo.main.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.innotech.mydemo.R;
import com.innotech.mydemo.main.base.V2BaseFragment;
import com.innotech.mydemo.widget.baselv.LBaseAdapter;
import com.innotech.mydemo.widget.baselv.LViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class V2PersonFragment extends V2BaseFragment {

    @Bind(R.id.lv_menu)
    ListView lvMenu;
    private List<String> mData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_person, null);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        initDatas();
        lvMenu.setAdapter(new LBaseAdapter<String>(mActivity, mData, R.layout.p_list_item) {

            @Override
            public void convert(LViewHolder viewHolder, String item) {
                viewHolder.setText(R.id.tv_title, item);
            }
        });
    }

    private void initDatas(){
        mData = new ArrayList<>();
        for(int i=0;i<10;i++){
            mData.add("index-" + i);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
