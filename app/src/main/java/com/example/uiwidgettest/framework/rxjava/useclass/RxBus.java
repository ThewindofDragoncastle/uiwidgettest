package com.example.uiwidgettest.framework.rxjava.useclass;


import io.reactivex.Observable;
import io.reactivex.observers.SerializedObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by 40774 on 2017/10/11.
 */

public class RxBus {
    //用此代替事件主线
    private static volatile RxBus rxBus;
    private final Subject<Object> subject=
            PublishSubject.create().toSerialized();
    private RxBus(){};
    public static RxBus getInstance()
    {
        if(rxBus==null)
        {
            synchronized (RxBus.class)
            {
                if(rxBus==null)
                    rxBus=new RxBus();
            }
        }
        return rxBus;
    }
    public void post(Object ob)
    {
        subject.onNext(ob);
    }
    public void complete()
    {
        subject.onComplete();
    }
    public <T> Observable<T> toObservale(Class<T> eventype)
    {
        return subject.ofType(eventype);
    }
}
