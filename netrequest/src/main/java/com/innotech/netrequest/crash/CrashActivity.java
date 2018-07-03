package com.innotech.netrequest.crash;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Window;
import android.widget.TextView;

import com.innotech.netrequest.R;

/**
 * Created by admin on 2018/4/24.
 */

public class CrashActivity extends Activity{

    private TextView tv_ex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_crash);

        tv_ex = findViewById(R.id.tv_ex);

        if(getIntent() != null && !TextUtils.isEmpty(getIntent().getStringExtra("ex"))){
            tv_ex.setText(getIntent().getStringExtra("ex"));
        }else{
            tv_ex.setText("");
        }
    }
}
