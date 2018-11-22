package com.innotech.mydemo.widget.baselv;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class LViewHolder {

    private final SparseArray<View> mViews;
    private View convertView;

    public LViewHolder(Context context, ViewGroup parent, int layoutId, int position){
        mViews = new SparseArray<>();
        convertView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        convertView.setTag(this);
    }

    public static LViewHolder get(Context context, View convertView, ViewGroup parent, int layoutId, int position){
        if(null == convertView){
            return new LViewHolder(context, parent, layoutId, position);
        }
        return (LViewHolder) convertView.getTag();
    }

    public View getConvertView(){
        return convertView;
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(null == view){
            view = convertView.findViewById(viewId);
        }
        mViews.put(viewId, view);
        return (T)view;
    }

    public LViewHolder setText(int viewId, String text){
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

    public LViewHolder setImageResource(int viewId, int resId){
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

    public LViewHolder setListener(int viewId, String text){
        return this;
    }

}
