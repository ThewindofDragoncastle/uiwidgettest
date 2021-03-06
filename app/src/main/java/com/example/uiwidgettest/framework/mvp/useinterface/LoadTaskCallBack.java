package com.example.uiwidgettest.framework.mvp.useinterface;

import io.reactivex.disposables.Disposable;

/**
 * Created by 40774 on 2017/10/13.
 */

public interface LoadTaskCallBack <T>{
    void onSucess(T t);
    void onStart();
    void onFailed();
    void onFinish();
}
