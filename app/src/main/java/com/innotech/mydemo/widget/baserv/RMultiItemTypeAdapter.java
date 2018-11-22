package com.innotech.mydemo.widget.baserv;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

public abstract class RMultiItemTypeAdapter<T> extends RBaseAdapter<T>{

    public RMultiItemTypeInterface rMultiItemTypeInterface;

    public RMultiItemTypeAdapter(Context context, List mDatas, RMultiItemTypeInterface rMultiItemTypeInterface) {
        super(context, mDatas, -1);
        this.rMultiItemTypeInterface = rMultiItemTypeInterface;
    }

    @Override
    public int getItemViewType(int position) {
        if(null != rMultiItemTypeInterface){
            return rMultiItemTypeInterface.getItemViewType(mDatas.get(position), position);
        }
        return super.getItemViewType(position);
    }

    @Override
    public RViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        int layoutId = rMultiItemTypeInterface.getLayoutId(viewType);
        return RViewHolder.get(context, parent, layoutId);
    }

    public interface RMultiItemTypeInterface<T>{
        int getItemViewType(T item, int position);

        int getLayoutId(int viewType);
    }


}
