package com.innotech.mydemo.launch.presenter;

import com.innotech.mydemo.launch.bean.AdBean;
import com.innotech.mydemo.launch.bean.DownloadBean;

public interface ILanchPresenter {

    public void appStart();

    public AdBean getAdBean();

    public DownloadBean getDownloadBean();
}
