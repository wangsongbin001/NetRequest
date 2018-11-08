package com.innotech.mydemo.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.innotech.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class GuideDialogAdapter extends RecyclerView.Adapter<GuideDialogAdapter.MyViewHolder>{

    private Context context;
    private List<String> list;
    private GuideDialog.GuideDialogCallBack guideDialogCallBack;

    public void setCallBack(GuideDialog.GuideDialogCallBack guideDialogCallBack){
        this.guideDialogCallBack = guideDialogCallBack;
    }

    public GuideDialogAdapter(Context context, List list){
        this.context = context;
        if(list == null){
            this.list = new ArrayList<>();
        }else {
            this.list = list;
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_guide_book_dialog, parent,
                false);
        MyViewHolder holder = new MyViewHolder(itemView);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        String model = list.get(position);
        holder.tv_dosomething.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(guideDialogCallBack != null){
                    guideDialogCallBack.onItemClick(holder.getLayoutPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void update(List list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView tv_title;
        private ImageView iv_tag;
        private TextView tv_tips;
        private ImageView iv_gold;
        private TextView tv_gold;
        private TextView tv_dosomething;
        private TextView tv_txt;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_tag = itemView.findViewById(R.id.iv_tag);
            tv_tips = itemView.findViewById(R.id.tv_tips);
            iv_gold = itemView.findViewById(R.id.iv_gold);
            tv_gold = itemView.findViewById(R.id.tv_gold);
            tv_dosomething = itemView.findViewById(R.id.tv_dosometing);
            tv_txt = itemView.findViewById(R.id.tv_txt);
        }
    }
}
