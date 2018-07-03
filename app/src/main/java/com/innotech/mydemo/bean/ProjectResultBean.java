package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;

import java.util.List;

import lombok.Data;

/**
 * Created by admin on 2018/4/19.
 */

@Data
public class ProjectResultBean{
    @Expose
    private int code;
    @Expose
    private int limit;
    @Expose
    private String message;
    @Expose
    private int offset;
    @Expose
    List<ProjectItemBean> data;

}
