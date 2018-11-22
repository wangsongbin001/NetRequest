package com.innotech.mydemo.widget.baserv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class RBaseAdapter<T> extends RecyclerView.Adapter<RViewHolder>{

    protected Context context;
    protected List<T> mDatas;
    protected int layoutId;

    public RBaseAdapter(Context context, List<T> mDatas, int layoutId) {
        super();
        this.context = context;
        if(null == mDatas){
            mDatas = new ArrayList<>();
        }
        this.mDatas = mDatas;
        this.layoutId = layoutId;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return RViewHolder.get(context, parent, layoutId);
    }

    @Override
    public void onBindViewHolder(RViewHolder holder, int position) {
        convert(holder, mDatas.get(position), position);
    }

    public abstract void convert(RViewHolder holder, T item, int position);
}
