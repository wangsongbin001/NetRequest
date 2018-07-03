package com.innotech.mydemo.viewmodel;

import android.widget.Toast;

import com.innotech.mydemo.adapter.ProjectAdapter;
import com.innotech.mydemo.bean.ProjectItemBean;
import com.innotech.mydemo.model.IProjectModel;
import com.innotech.mydemo.model.imp.ProjectModelImp;
import com.innotech.netrequest.BaseAppCompatActivity;
import com.innotech.netrequest.BaseFragmentV4;
import com.innotech.netrequest.util.LogUtil;

import java.util.List;

/**
 * Created by admin on 2018/4/18.
 */

public class ProjectVM {
    BaseFragmentV4 baseFragmentV4;
    IProjectModel iProjectModel;
    ProjectAdapter projectAdapter;

    public ProjectVM(BaseFragmentV4 baseFragmentV4, ProjectAdapter projectAdapter){
        this.baseFragmentV4 = baseFragmentV4;
        this.projectAdapter = projectAdapter;
        iProjectModel = new ProjectModelImp(this);
        getProjectList();
    }


    public void getProjectList(){
        iProjectModel.getProjectList(baseFragmentV4);
    }

    public void onGetProjectListSuccess(List<ProjectItemBean> list){
        projectAdapter.refreshData(list);
        LogUtil.d("OkProJectVM", "" + list.toString());
        if(baseFragmentV4.mCtx != null){
            Toast.makeText(baseFragmentV4.getContext(), "加载成功！", Toast.LENGTH_LONG).show();
        }
    }

    public void onGetProjectListFailure(Throwable throwable){

    }
}
