package com.innotech.mydemo.main.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.innotech.mydemo.R;
import com.innotech.mydemo.main.base.V2BaseFragment;

public class V2TaskFragment extends V2BaseFragment {

    private WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_task, null);
        initViews(view);
        return view;
    }

    private void initViews(View view){
        webView = view.findViewById(R.id.webview);
        webView.loadUrl("www.baidu.com");
    }
}
