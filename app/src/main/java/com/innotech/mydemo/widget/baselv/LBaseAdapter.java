package com.innotech.mydemo.widget.baselv;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

public abstract class LBaseAdapter<T> extends BaseAdapter {

    protected List<T> mDatas;
    protected int layoutId;
    protected Context context;

    public LBaseAdapter(Context context, List<T> mDatas, int layoutId) {
        this.context = context;
        if (null == mDatas) {
            mDatas = new ArrayList<>();
        }
        this.mDatas = mDatas;
        this.layoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LViewHolder viewHolder = LViewHolder.get(context, convertView, parent, layoutId, position);
        convert(viewHolder, mDatas.get(position));
        return viewHolder.getConvertView();
    }

    public abstract void convert(LViewHolder viewHolder, T item);
}
