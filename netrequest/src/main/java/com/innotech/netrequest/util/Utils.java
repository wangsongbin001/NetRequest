package com.innotech.netrequest.util;

import android.app.Activity;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class Utils {

    public static boolean isActivityFinishingOrDestroyed(Activity activity) {
        boolean isDestroyed = false;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            isDestroyed = activity.isDestroyed();
        }
        return isDestroyed || activity.isFinishing();
    }

    public static <T> T checkNotNull(T object, String message) {
        if(object == null) {
            throw new NullPointerException(message);
        } else {
            return object;
        }
    }

    public static RequestBody createFile(File file) {
        checkNotNull(file, "file not null!");
        return RequestBody.create(MediaType.parse("multipart/form-data; charset=utf-8"), file);
    }

    @NonNull
    public static RequestBody createBody(File file, String mediaType) {
        checkNotNull(file, "file not null!");
        if(TextUtils.isEmpty(mediaType)) {
            throw new NullPointerException("contentType not be null");
        } else {
            return RequestBody.create(MediaType.parse(mediaType), file);
        }
    }
}
