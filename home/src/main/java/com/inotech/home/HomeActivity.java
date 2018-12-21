package com.inotech.home;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.innotech.netrequest.RouterPageIndex;
import com.innotech.netrequest.baseservice.MineExportService;

/**
 * @author wang
 */
@Route(path = RouterPageIndex.PAGE_HOME)
public class HomeActivity extends Activity {

    @Autowired(name = RouterPageIndex.ExportService.SERVICE_MINE)
    MineExportService mineService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ARouter.getInstance().inject(this);
    }

    public void onClick(View view){
        Toast.makeText(this, "" + mineService.getMsg(), Toast.LENGTH_SHORT).show();
    }
}
