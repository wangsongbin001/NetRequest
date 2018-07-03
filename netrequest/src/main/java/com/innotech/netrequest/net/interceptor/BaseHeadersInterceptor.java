package com.innotech.netrequest.net.interceptor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class BaseHeadersInterceptor<T> implements Interceptor {
    private Map<String, T> headers;

    public BaseHeadersInterceptor(Map<String, T> headers) {
        this.headers = headers;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request.Builder builder = original.newBuilder()
                .header("Charset", "UTF-8")
                .header("Accept-Language", "zh-CN, zh;q=0.8, en-US;q=0.5, en;q=0.3")
                .method(original.method(), original.body());
        Response response = chain.proceed(this.interceptor(builder.build()));
        return response;
    }

    private static String getValueEncoded(String value) throws UnsupportedEncodingException {
        if (value == null) {
            return "null";
        } else {
            String newValue = value.replace("\n", "");
            int i = 0;

            for (int length = newValue.length(); i < length; ++i) {
                char c = newValue.charAt(i);
                if (c <= 31 || c >= 127) {
                    return URLEncoder.encode(newValue, "UTF-8");
                }
            }

            return newValue;
        }
    }

    Request interceptor(Request request) throws UnsupportedEncodingException {
        Request.Builder builder = request.newBuilder();
        if (this.headers != null && this.headers.size() > 0) {
            Set<String> keys = this.headers.keySet();
            Iterator var4;
            String headerKey;
            var4 = keys.iterator();
            while (var4.hasNext()) {
                headerKey = (String) var4.next();
                builder.addHeader(headerKey, this.headers.get(headerKey) == null ? "" : getValueEncoded((String) this.headers.get(headerKey))).build();
            }
        }
        return builder.build();
    }
}
