package com.innotech.netrequest.net;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import com.innotech.netrequest.enums.EnumResCode;
import com.innotech.netrequest.util.Utils;

import java.lang.ref.WeakReference;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import retrofit2.HttpException;


/**
 * Created by dell on 2017/7/18.
 */

public class BaseErrorConsumer implements Consumer<Throwable> {
    private WeakReference<Context> mContextRef;

    public BaseErrorConsumer(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    public void accept(@NonNull Throwable e) throws Exception {
        onError(e);
    }

    /**
     * 全部的错误处理回调，子类可重写此方法特殊处理
     *
     * @param e
     */
    protected void onError(@NonNull Throwable e) {
        e.printStackTrace();
        if (e instanceof ApiException) {
            ApiException apiException = (ApiException) e;
            if (EnumResCode.REQUEST_SERVER_INTERAL_ERROR.equalsIgnoreCase(apiException.code)) {
                // 服务器内部错误，改成通用的提示
                onApiError(new ApiException(apiException.code, "操作失败，请稍后再试"));
            } else if (EnumResCode.REQUEST_USER_BE_KICKED.equalsIgnoreCase(apiException.code)) {
                // 用户被踢下线
                Context context = mContextRef.get();
                if (context != null && context instanceof Activity) {
                    if (Utils.isActivityFinishingOrDestroyed((Activity) context)) {
                        return;
                    }
                    showReloginDialog(context);
                }
            } else if (apiException.code == null) {
                // Json格式错误或不符合接口规范
                showToast("操作失败，请稍后再试");
            } else {
                onApiError(apiException);
            }
        } else if (e instanceof SocketTimeoutException) {
            showToast("网络连接超时");
        } else if (e instanceof ConnectException) {
            showToast("网络连接异常");
        } else if (e instanceof HttpException) {
            showToast("网络未知错误");
        } else {
            showToast("未知错误");
        }
    }

    private void showReloginDialog(Context context) {
        new AlertDialog.Builder(context)
                .setMessage("您的账号在另一设备登录，请您重新登录")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        login();
                    }
                })
                .setCancelable(false)
                .show();
    }

    private void login() {
//        Context context = mContextRef.get();
//        if (context == null) {
//            return;
//        }
//        App.getInstance().setIsLogined(false);
//        UserBean.setUserBean(null);
//        BillInfoBean.setBillInfo(null);
//        HttpUtils.setSessionId("");
//        SharedPreUtils.getInstance(context).saveValue(SharedPreUtils.USER_AUTOLOGIN, false);
//        Intent intent = new Intent(context, LoginActivity.class);
//        intent.putExtra(LoginActivity.RET_PAGE, "MainFragmentActivity");
//        context.startActivity(intent);
//        App.getInstance().finishAllActivityLeaveLogin();
    }

    private void showToast(String msg) {
        Context context = mContextRef.get();
        if (context != null) {
            Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
//            TooltipUtils.showToastL(context, msg);
        }
    }

    /**
     * Api接口定义的错误处理回调，子类可重写此方法特殊处理
     *
     * @param apiException
     */
    protected void onApiError(ApiException apiException) {
        if (apiException instanceof DataIsNullException) {
            // data字段为null默认不提示
            return;
        }
        showToast(apiException.msg);
    }
}
