package com.innotech.netrequest.net;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * 切换到主线程
 * Created by admin on 2017/8/29.
 */

public class ObserveOnMainCallAdapterFactory extends CallAdapter.Factory {

    private final Scheduler mScheduler;

    public ObserveOnMainCallAdapterFactory() {
        mScheduler = AndroidSchedulers.mainThread();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (getRawType(returnType) != Observable.class) {
            return null;
        }
        // noinspection unchecked
        final CallAdapter<Object, Observable<?>> delegate = (CallAdapter<Object, Observable<?>>)
                retrofit.nextCallAdapter(this, returnType, annotations);
        return new CallAdapter<Object, Object>() {
            @Override
            public Type responseType() {
                return delegate.responseType();
            }

            @Override
            public Object adapt(Call<Object> call) {
                Observable<?> o = delegate.adapt(call);
                return o.observeOn(mScheduler);
            }
        };
    }
}
