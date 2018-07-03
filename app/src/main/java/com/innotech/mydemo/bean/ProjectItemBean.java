package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;

/**
 * Created by admin on 2018/4/18.
 */

public class ProjectItemBean {

    @Expose
    private Long id;
    @Expose
    private String name;
    @Expose
    private String commit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommit() {
        return commit;
    }

    public void setCommit(String commit) {
        this.commit = commit;
    }

}
