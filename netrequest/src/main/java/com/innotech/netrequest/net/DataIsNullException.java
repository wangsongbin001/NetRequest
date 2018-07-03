package com.innotech.netrequest.net;

import com.innotech.netrequest.net.ApiException;

/**
 * Created by liufang03 on 2017/8/17.
 */

public class DataIsNullException extends ApiException {
    public DataIsNullException() {
        super("-1", "data字段为null");
    }
}
