package com.innotech.mydemo.main.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innotech.mydemo.R;
import com.innotech.mydemo.main.adapter.ProjectAdapter;
import com.innotech.mydemo.databinding.FragmentProjectBinding;
import com.innotech.mydemo.main.viewmodel.ProjectVM;
import com.innotech.netrequest.BaseFragmentV4;

/**
 * Created by admin on 2018/4/18.
 */

public class ProjectFragment extends BaseFragmentV4 {

    private FragmentProjectBinding binding;
    private ProjectAdapter adapter;
    private ProjectVM projectVM;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_project, container, false);
        initRecycleView();
        projectVM = new ProjectVM(this, adapter);
        return binding.getRoot();
    }

    private void initRecycleView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.xrvList.setLayoutManager(layoutManager);
        adapter = new ProjectAdapter(getActivity(), null);
        binding.xrvList.setAdapter(adapter);
        binding.xrvList.setPullRefreshEnabled(false);
        binding.xrvList.setLoadingMoreEnabled(false);

    }

}
