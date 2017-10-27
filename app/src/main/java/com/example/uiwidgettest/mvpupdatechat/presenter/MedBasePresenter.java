package com.example.uiwidgettest.mvpupdatechat.presenter;

/**
 * Created by 40774 on 2017/10/19.
 */

public class MedBasePresenter implements BaseActivity.DownActivityView {
    //基础媒介为所有媒介必须都能操作活动
    public static BaseActivity.ActivityView activityView;
    @Override
    public void setServiceInterface(BaseActivity.ActivityView service) {
        activityView=service;
    }
}
