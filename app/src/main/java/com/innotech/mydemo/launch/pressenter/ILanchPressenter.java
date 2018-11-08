package com.innotech.mydemo.launch.pressenter;

import com.innotech.mydemo.launch.bean.AdBean;
import com.innotech.mydemo.launch.bean.DownloadBean;

public interface ILanchPressenter {

    public void appStart();

    public AdBean getAdBean();

    public DownloadBean getDownloadBean();
}
