package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;


/**
 * Created by admin on 2018/4/9.
 */
public class LoginBean{
    @Expose
    int code;
    @Expose
    String message;
    @Expose
    TokenBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public TokenBean getData() {
        return data;
    }

    public void setData(TokenBean data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "LoginBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
