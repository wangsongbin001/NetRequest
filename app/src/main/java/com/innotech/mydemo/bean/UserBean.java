package com.innotech.mydemo.bean;

import android.databinding.ObservableField;

/**
 * Created by admin on 2018/4/18.
 */

public class UserBean {
     public final ObservableField<String> userName = new ObservableField<String>();
     public final ObservableField<String> password = new ObservableField<String>();
     public final ObservableField<String> token = new ObservableField<String>();
}
