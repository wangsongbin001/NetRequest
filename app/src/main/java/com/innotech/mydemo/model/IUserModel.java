package com.innotech.mydemo.model;

import android.content.Context;

import com.innotech.mydemo.bean.UserBean;
import com.innotech.netrequest.BaseActivity;

/**
 * Created by admin on 2018/4/18.
 */

public interface IUserModel {

    public void login(BaseActivity context, UserBean userBean);
}
