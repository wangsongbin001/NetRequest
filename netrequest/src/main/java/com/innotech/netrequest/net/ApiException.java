package com.innotech.netrequest.net;

import java.io.IOException;

/**
 * Created by liupei on 15/11/10.
 */
public class ApiException extends IOException {
    public final String code;
    public final String msg;

    public ApiException(String code) {
        this.code = code;
        this.msg = null;
    }

    public ApiException(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
