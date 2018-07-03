package com.innotech.mydemo.model.imp;

import com.google.gson.Gson;
import com.innotech.mydemo.MApp;
import com.innotech.mydemo.bean.ProjectResultBean;
import com.innotech.mydemo.model.IProjectModel;
import com.innotech.mydemo.model.net.CuriserWrapper;
import com.innotech.mydemo.viewmodel.ProjectVM;
import com.innotech.netrequest.BaseFragmentV4;
import com.innotech.netrequest.net.ApiException;

import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by admin on 2018/4/18.
 */

public class ProjectModelImp implements IProjectModel {

    private ProjectVM projectVM;

    public ProjectModelImp(ProjectVM projectVM) {
        this.projectVM = projectVM;
    }

    @Override
    public void getProjectList(BaseFragmentV4 baseFragmentV4) {
        baseFragmentV4.addDisposable(CuriserWrapper.getService(baseFragmentV4.mCtx)
                .getProjectList(MApp.getToken())
                .map(new Function<String, ProjectResultBean>() {
                    @Override
                    public ProjectResultBean apply(String s) throws Exception {
                        Gson gson = new Gson();
                        return gson.fromJson(s, ProjectResultBean.class);
                    }
                }).subscribe(new Consumer<ProjectResultBean>() {
                    @Override
                    public void accept(ProjectResultBean projectResultBean) throws Exception {
                        if (projectResultBean.getCode() == 0) {
                            projectVM.onGetProjectListSuccess(projectResultBean.getData());
                        } else {
                            projectVM.onGetProjectListFailure(
                                    new ApiException(projectResultBean.getCode() + "",
                                            projectResultBean.getMessage()));
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        projectVM.onGetProjectListFailure(throwable);
                    }
                }));
    }
}
