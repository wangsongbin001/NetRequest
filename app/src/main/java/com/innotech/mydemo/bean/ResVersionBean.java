package com.innotech.mydemo.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class ResVersionBean implements Serializable {
    @Expose
    public PackageBean android;
    @Expose
    public PackageBean ios;

    public class PackageBean implements Serializable
    {
        @Expose
        public String downloadUrl;
        @Expose
        public String versionName;
        @Expose
        public int versionCode;
        @Expose
        public int enumCode;
        @Expose
        public String description;
        @Expose
        public float appSize;
        @Expose
        public int isOpen;

        @Override
        public String toString() {
            return "PackageBean{" +
                    "downloadUrl='" + downloadUrl + '\'' +
                    ", versionName='" + versionName + '\'' +
                    ", versionCode=" + versionCode +
                    ", enumCode=" + enumCode +
                    ", description='" + description + '\'' +
                    ", appSize=" + appSize +
                    ", isOpen=" + isOpen +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ResVersionBean{" +
                "android=" + android +
                ", ios=" + ios +
                '}';
    }
}
