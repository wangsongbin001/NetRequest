package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by admin on 2018/4/8.
 */
public class TokenBean implements Serializable{
    @Expose
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "TokenBean{" +
                "token='" + token + '\'' +
                '}';
    }
}
