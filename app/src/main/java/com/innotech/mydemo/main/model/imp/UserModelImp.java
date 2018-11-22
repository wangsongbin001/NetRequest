package com.innotech.mydemo.main.model.imp;

import com.innotech.mydemo.bean.TokenBean;
import com.innotech.mydemo.bean.UserBean;
import com.innotech.mydemo.main.model.IUserModel;
import com.innotech.mydemo.net.CuriserWrapper;
import com.innotech.mydemo.main.viewmodel.UserVM;
import com.innotech.netrequest.BaseActivity;
import com.innotech.netrequest.net.BaseErrorConsumer;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2018/4/18.
 */

public class UserModelImp implements IUserModel{

    private UserVM userVM;

    public UserModelImp(UserVM userVM){
        this.userVM = userVM;
    }

    @Override
    public void login(BaseActivity context, UserBean userBean) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("username", userBean.userName.get());
        params.put("password", userBean.password.get());
        context.addDisposable(CuriserWrapper.getService(context).login(params).subscribe(new Consumer<TokenBean>() {
            @Override
            public void accept(TokenBean tokenBean) throws Exception {
                 userVM.onLoginSuccess(tokenBean);
            }
        }, new BaseErrorConsumer(context)));
    }
}
