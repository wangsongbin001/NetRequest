package com.innotech.mydemo.main.viewmodel;

import android.content.Intent;
import android.widget.Toast;

import com.innotech.mydemo.MApp;
import com.innotech.mydemo.activity.MainMenuActivity;
import com.innotech.mydemo.bean.TokenBean;
import com.innotech.mydemo.bean.UserBean;
import com.innotech.mydemo.main.model.IUserModel;
import com.innotech.mydemo.main.model.imp.UserModelImp;
import com.innotech.netrequest.BaseActivity;

/**
 * Created by admin on 2018/4/18.
 */

public class UserVM {

    BaseActivity mActivity;
    IUserModel iUserModel;

    public UserVM(BaseActivity mActivity){
        this.mActivity = mActivity;
        iUserModel = new UserModelImp(this);
    }

    public void login(UserBean userBean){
         iUserModel.login(mActivity, userBean);
    }

    public void onLoginSuccess(TokenBean tokenBean){
         Toast.makeText(mActivity, "登陆成功", Toast.LENGTH_LONG).show();
        MApp.setToken(tokenBean.getToken());
         mActivity.startActivity(new Intent(mActivity, MainMenuActivity.class));
    }

    public void onLoginFailure(Throwable throwable){

    }
}

