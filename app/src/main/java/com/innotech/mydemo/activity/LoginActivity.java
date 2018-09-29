package com.innotech.mydemo.activity;

import android.os.Bundle;

import com.innotech.mydemo.bean.UserBean;
import com.innotech.mydemo.main.viewmodel.UserVM;
import com.innotech.netrequest.BaseActivity;

/**
 * Created by admin on 2018/4/18.
 */

public class LoginActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        ActivityLoginBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        UserVM loginVM = new UserVM(this);
//        binding.setLoginVM(loginVM);

        UserBean userBean = new UserBean();
        userBean.userName.set("max");
        userBean.password.set("123456");
//        binding.setUserBean(userBean);
    }
}
