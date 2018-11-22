package com.innotech.mydemo.widget.baserv;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class RViewHolder extends RecyclerView.ViewHolder{

    private SparseArray<View> mViews;
    private View convertView;
    private Context context;

    public RViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        this.context = context;
        convertView = itemView;
        mViews = new SparseArray<>();
    }

    public static RViewHolder get(Context context, ViewGroup parent, int layoutId){
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        RViewHolder viewHolder = new RViewHolder(context, view, parent);
        return viewHolder;
    }

    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (null == view)
        {
            view = convertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public RViewHolder setText(int viewId, String text){
        View view = mViews.get(viewId);
        if(null == view){
            view = convertView.findViewById(viewId);
        }
        if(view instanceof TextView){
            TextView textView = (TextView)view;
            textView.setText(text);
        }
        return this;
    }

    public RViewHolder setImageResource(int viewId, int resId){
        View view = mViews.get(viewId);
        if(null == view){
            view = convertView.findViewById(viewId);
        }
        if(view instanceof ImageView){
            ImageView imageView = (ImageView) view;
            imageView.setImageResource(resId);
        }
        return this;
    }



}
