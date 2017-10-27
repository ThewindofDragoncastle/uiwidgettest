package com.example.uiwidgettest.mvpupdatechat.presenter;

import android.support.v4.app.Fragment;

public interface BaseActivity {
    interface Callback {}
    interface Down {}
    interface ActivityView extends BaseActivity.Callback
    {    //媒介对活动的回调
        void bindService();
        void unbindService();
        void startService();
        void stopService();
        void ShowPrepareDialog(String title);
        void HidePrepareDialog();
        void ShowNetErrorWarning(String title);
        void showErrorToast();
        void HideNetWarning();
        void ShowProBar();
        void HideProBar();
    }
    interface DownActivityView extends BaseActivity.Down
    {    //活动对碎片的调用
        //        在活动中
        void setServiceInterface(ActivityView service);
    }
}
