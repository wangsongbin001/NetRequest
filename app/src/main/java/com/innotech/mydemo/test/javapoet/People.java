package com.innotech.mydemo.test.javapoet;

import android.util.Log;

public class People {
    public static final String tag = "jp";

    public void work(){
        Log.i("jp", "work");
    }

   @Deprecated
    public void rest(){
        Log.i("jp", "rest");
    }
}
