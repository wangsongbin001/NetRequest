package com.inotech.mine;

import android.content.Context;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.innotech.netrequest.RouterPageIndex;
import com.innotech.netrequest.baseservice.MineExportService;

@Route(path = RouterPageIndex.ExportService.SERVICE_MINE)
public class MineService implements MineExportService{

    @Override
    public String getMsg() {
        return "I am from mine";
    }

    @Override
    public void init(Context context) {

    }
}
