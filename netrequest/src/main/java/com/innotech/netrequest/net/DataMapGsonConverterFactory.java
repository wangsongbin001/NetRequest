package com.innotech.netrequest.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.innotech.netrequest.entities.ResponseBean;
import com.innotech.netrequest.entities.ResultBean;
import com.innotech.netrequest.enums.EnumResCode;
import com.innotech.netrequest.util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 提取data字段，status字段非"000"抛出ApiException
 * Created by liufang03 on 2017/8/29.
 */

public class DataMapGsonConverterFactory extends Converter.Factory {
    
    public static DataMapGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new DataMapGsonConverterFactory(gson);
    }

    private final Gson gson;

    private DataMapGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(type);
        if(rawType == String.class){
            return new DataStringResponseConvert();
        }
        if (rawType != ResultBean.class) {
            TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
            return new DataMapGsonResponseBodyConverter<>(adapter, rawType != ResponseBean.class);
        }
        return super.responseBodyConverter(type, annotations, retrofit);
    }

    private static class DataMapGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
        private final TypeAdapter<T> adapter;

        private final boolean withData;

        DataMapGsonResponseBodyConverter(TypeAdapter<T> adapter, boolean withData) {
            this.adapter = adapter;
            this.withData = withData;
        }

        @Override
        public T convert(ResponseBody value) throws IOException {
            try {
                String json = value.string();
                LogUtil.d("MNetRequest", "value:" + json);
                JSONObject jsonObject = new JSONObject(json);
                String status = null;
                String code = null;
                try{ status = jsonObject.getString("status");}catch (Exception e){}
                try{ code = jsonObject.getString("code");}catch (Exception e){}
                LogUtil.d("MNetRequest", "status:" + status + ",code:" + code);
                if ("000".equals(status) || EnumResCode.REQUEST_SUCCESS.equals(code)) {
                    if (withData) {
                        json = jsonObject.get("data").toString();
                        if ("null".equalsIgnoreCase(json)) {
                            throw new DataIsNullException();
                        }
                    }
                    return adapter.fromJson(json);
                } else {
                    throw new ApiException(status, jsonObject.getString("message"));
                }
            } catch (JSONException e) {
                throw new IOException(e);
            } finally {
                value.close();
            }
        }
    }

    /**
     * 当服务器返回的数据格式，比较特殊时，直接使用String返回ReposonseBody
     */
    private static class DataStringResponseConvert implements Converter<ResponseBody, String>{

        @Override
        public String convert(ResponseBody value) throws IOException {
            return value.string();
        }
    }
}
