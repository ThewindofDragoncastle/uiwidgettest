package com.example.uiwidgettest.framework.mvp.useclass;

import com.example.uiwidgettest.framework.mvp.useinterface.DatainfoContract;
import com.example.uiwidgettest.framework.mvp.useinterface.LoadTaskCallBack;
import com.example.uiwidgettest.framework.mvp.useinterface.NetTask;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 40774 on 2017/10/13.
 */

public class DataInfoPresenter implements DatainfoContract.Presenter,LoadTaskCallBack {
    private DatainfoContract.View addview;
    private NetTask netTask;
    private CompositeDisposable mdisposable;
    public DataInfoPresenter(DatainfoContract.View addView, NetTask netTask)
    {
        this.addview=addView;
        this.netTask=netTask;
        mdisposable=new CompositeDisposable();
    }
    @Override
    public void getDataInfo(final String ip) {
          netTask.execute(ip,this);
    }

    @Override
    public void onSucess(Object o) {
        if (addview.IsActive())
        addview.setDataInfo((Hero) o);
    }

    @Override
    public void onStart() {
        if(addview.IsActive())
             addview.showLoading();
    }

    @Override
    public void onFailed() {
        if(addview.IsActive()) {
            addview.showError();
            addview.hideLoading();
        }
    }

    @Override
    public void onFinish() {
        if(addview.IsActive())
            addview.hideLoading();
    }
}
