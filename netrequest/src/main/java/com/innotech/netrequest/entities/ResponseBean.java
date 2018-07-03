package com.innotech.netrequest.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResponseBean {
    @SerializedName("status")
    @Expose
    public String resCode;
    @SerializedName("message")
    @Expose
    public String resMsg;

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public boolean isSuccessful() {
        return "000".equals(resCode);
    }

    @Override
    public String toString() {
        return "ResponseBean{" +
                "resCode='" + resCode + '\'' +
                ", resMsg='" + resMsg + '\'' +
                '}';
    }
}
