package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by admin on 2018/4/9.
 */
public class TotalBean{
    @Expose
    int code;
    @Expose
    String message;
    @Expose
    int total;

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

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "TotalBean{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", total=" + total +
                '}';
    }
}
