package com.example.uiwidgettest.framework.mvp.useinterface;

import com.example.uiwidgettest.framework.mvp.useclass.*;
import com.example.uiwidgettest.framework.mvp.useinterface.BaseView;
import com.example.uiwidgettest.myreview.json.*;

import io.reactivex.disposables.Disposable;

/**
 * Created by 40774 on 2017/10/13.
 */

public interface DatainfoContract {
    interface Presenter
    {
        void getDataInfo(String ip);
    }
    interface View extends BaseView<Presenter>
    {
        //从碎片到媒介
        void setDataInfo(com.example.uiwidgettest.framework.mvp.useclass.Hero hero);
        void showLoading();
        void hideLoading();
        void showError();
        boolean IsActive();
    }
}
