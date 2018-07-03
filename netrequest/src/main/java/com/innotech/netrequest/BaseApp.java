package com.innotech.netrequest;

import android.app.Application;

/**
 * Created by admin on 2018/4/4.
 */

public class BaseApp extends Application{
    private static BaseApp app;

    public static BaseApp getSelf(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
