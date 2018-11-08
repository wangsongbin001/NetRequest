package com.innotech.mydemo.widget;

import android.app.Dialog;
import android.content.Context;
import android.databinding.generated.callback.OnClickListener;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.innotech.mydemo.R;

import java.util.ArrayList;
import java.util.List;

public class GuideDialog extends Dialog implements View.OnClickListener {

    private TextView tv_forbid;
    private ImageView iv_close;
    private RecyclerView rv_guide;
    private GuideDialogAdapter adapter;
    private List list;

    public GuideDialog(@NonNull Context context) {
        super(context, R.style.AlphaDialog);
        initViews(context);
        initListeners();
    }

    public GuideDialog(@NonNull Context context, List list) {
        super(context, R.style.AlphaDialog);
        this.list = list;
        initViews(context);
        initListeners();
    }

    private void initViews(Context context) {
        setContentView(R.layout.dialog_guide);
        tv_forbid = findViewById(R.id.tv_forbid);
        iv_close = findViewById(R.id.iv_close);
        rv_guide = findViewById(R.id.rv_guide);
        List list = new ArrayList();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        if(this.list == null){
            this.list = list;
        }
        adapter = new GuideDialogAdapter(context, this.list);
        rv_guide.setLayoutManager(new LinearLayoutManager(context));
        rv_guide.setAdapter(adapter);
    }

    private void initListeners() {
        tv_forbid.setOnClickListener(this);
        iv_close.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_forbid:
                if (guideDialogCallBack != null) {
                    guideDialogCallBack.onForbid();
                }
                break;
            case R.id.iv_close:
                if (guideDialogCallBack != null) {
                    guideDialogCallBack.onClose();
                }
                break;
            default:
                break;
        }
        dismiss();
    }

    public void showNewList(List list) {
        adapter.update(list);
        show();
    }

    /**
     * 回调
     */
    private GuideDialogCallBack guideDialogCallBack;

    public void setCallBack(GuideDialogCallBack guideDialogCallBack) {
        this.guideDialogCallBack = guideDialogCallBack;
        if(adapter != null){
            adapter.setCallBack(guideDialogCallBack);
        }
    }

    public interface GuideDialogCallBack {
        void onForbid();

        void onItemClick(int position);

        void onClose();
    }

}
