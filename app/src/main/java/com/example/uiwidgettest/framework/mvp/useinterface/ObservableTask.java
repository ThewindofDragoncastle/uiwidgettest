package com.example.uiwidgettest.framework.mvp.useinterface;

import io.reactivex.Observable;

/**
 * Created by 40774 on 2017/10/14.
 */

public interface ObservableTask {
    Observable getObservable(String url);
}
