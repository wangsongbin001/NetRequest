package com.innotech.netrequest.net.model;

import java.util.Date;

/**
 * Created by admin on 2018/4/10.
 */

public class NetConfig {

    private static String token;

    public static String getToken() {
        return token;
    }

    public synchronized static void saveToken(String newToken) {
        token = newToken;
    }

    private static Date serverDate;

    public static Date getServerDate() {
        return serverDate;
    }

    public synchronized static void setServerDate(Date date) {
        serverDate = date;
    }
}
