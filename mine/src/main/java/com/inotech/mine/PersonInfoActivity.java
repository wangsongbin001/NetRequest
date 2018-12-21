package com.inotech.mine;

import android.app.Activity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.innotech.netrequest.RouterPageIndex;
import com.innotech.netrequest.util.LogUtil;

/**
 * @author wang
 * ARouter的使用
 *1，在每一个需要使用ARouter框架的Module中arguments = [moduleName: project.getName()]
 *2，添加相关依赖    implementation rootProject.ext.dependencies["arouter-api"]
 *                   annotationProcessor rootProject.ext.dependencies["arouter-compiler"]
 *3，添加注释：在支持路由的页面上添加注解
 *4, 初始化sdk
 *5，发起路由器操作
 *6，添加混淆策略
 *7，解析参数，自动解析,
 *8, 组件之间为彼此提供服务
 *   8.1在commlib中，声明ExportService 接口，extends IProvider
 *   8.2在业务Module中，实现接口，
 *   8.3在ARouter.getInstance.inject(this) + Autowired,接收
 */
@Route(path = RouterPageIndex.PAGE_PERSON_INFO)
public class PersonInfoActivity extends Activity{
    @Autowired
    long key1;
    @Autowired
    String key3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personinfo);
        ARouter.getInstance().inject(this);
        LogUtil.i("wang", "key1:" + key1 + ",key3:" + key3);
    }
}
