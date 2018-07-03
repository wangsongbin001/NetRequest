package com.innotech.netrequest.net;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.innotech.netrequest.util.Utils;
import com.innotech.netrequest.views.LoadingDialog;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;

import java.lang.ref.WeakReference;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by admin on 2018/4/19.
 */

public class LoadingFlowableTransformer implements FlowableTransformer {

    private WeakReference<Context> mContextRef;

    public LoadingFlowableTransformer(Context context) {
        mContextRef = new WeakReference<>(context);
    }

    @Override
    public Publisher apply(Flowable upstream) {
        return upstream.doOnSubscribe(new Consumer<Subscription>() {
            @Override
            public void accept(Subscription subscription) throws Exception {
                Context context = mContextRef.get();
                if (context != null && context instanceof Activity) {
                    if (Utils.isActivityFinishingOrDestroyed((Activity) context)) {
                        return;
                    }
                    ProgressDialog progressDialog = LoadingDialog.show(context);
                    progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
//                            disposable.dispose();
//                            Context ctx = mContextRef.get();
//                            if (ctx != null &&
//                                    !"MainFragmentActivity".equals(ctx.getClass().getSimpleName())) {
//                                // 取消网络请求时，关闭相应界面，防止界面异常
//                                ((Activity) ctx).finish();
//                            }
                        }
                    });
                }
            }
        }).doFinally(new Action() {
            @Override
            public void run() throws Exception {
                LoadingDialog.dismiss();
            }
        }).unsubscribeOn(AndroidSchedulers.mainThread());
    }
}
