package com.innotech.netrequest.entities;

import com.google.gson.annotations.Expose;

public class ResultBean<T> extends ResponseBean {
    @Expose
    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ResultBean{" +
                "resCode='" + resCode + '\'' +
                ", data=" + data +
                ", resMsg='" + resMsg + '\'' +
                '}';
    }
}

