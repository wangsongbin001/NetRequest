package com.innotech.mydemo.test.javapoet;

import android.util.Log;

public class Child extends People{

    @Override
    public void work() {
        super.work();
        Log.i("jp", "work");
    }

}
