package com.innotech.mydemo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.innotech.mydemo.BR;
import com.innotech.mydemo.R;
import com.innotech.mydemo.bean.ProjectItemBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/4/18.
 */

public class ProjectAdapter extends RecyclerView.Adapter{

    private List<ProjectItemBean> mData;
    private LayoutInflater mInflater;

    public ProjectAdapter(Context context,List<ProjectItemBean> mData){
        this.mInflater = LayoutInflater.from(context);
        this.mData = mData;
        if(this.mData == null){
            this.mData = new ArrayList<>();
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewDataBinding viewDataBinding = DataBindingUtil.inflate(mInflater, R.layout.list_item,  null, false);
        MViewHolder mViewHolder = new MViewHolder(viewDataBinding.getRoot());
        mViewHolder.setViewDataBinding(viewDataBinding);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        MViewHolder mViewHolder = (MViewHolder) holder;
        ProjectItemBean projectItemBean = mData.get(position);
        mViewHolder.getViewDataBinding().setVariable(BR.projectItemBean, projectItemBean);
    }

    public void refreshData(List<ProjectItemBean> lists){
        mData.clear();
        mData.addAll(lists);
        notifyDataSetChanged();
    }

    public void addMore(List<ProjectItemBean> lists){
        mData.addAll(lists);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class MViewHolder extends RecyclerView.ViewHolder{
        ViewDataBinding viewDataBinding;

        public MViewHolder(View itemView) {
            super(itemView);
            viewDataBinding = DataBindingUtil.bind(itemView);
        }

        public ViewDataBinding getViewDataBinding(){
            return viewDataBinding;
        }

        public void setViewDataBinding(ViewDataBinding viewDataBinding){
            this.viewDataBinding = viewDataBinding;
        }
    }

}
