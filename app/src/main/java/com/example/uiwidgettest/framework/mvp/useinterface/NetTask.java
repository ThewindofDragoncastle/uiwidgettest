package com.example.uiwidgettest.framework.mvp.useinterface;

import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;

/**
 * Created by 40774 on 2017/10/13.
 */

public interface NetTask<T> {
    void execute(T data, LoadTaskCallBack callBack);
}
