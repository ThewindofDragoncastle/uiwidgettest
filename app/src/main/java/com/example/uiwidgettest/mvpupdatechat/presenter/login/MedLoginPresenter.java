package com.example.uiwidgettest.mvpupdatechat.presenter.login;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.framework.rxjava.useclass.RxBus;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.json.HandleJson;
import com.example.uiwidgettest.mvpupdatechat.model.Service.RecieveData;
import com.example.uiwidgettest.mvpupdatechat.presenter.MedBasePresenter;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContactLogin;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by 40774 on 2017/10/16.
 */
//bufferdata 是 媒介层同模型层的直接交互
public  class MedLoginPresenter extends MedBasePresenter
        implements
        PresenterContactLogin.SetPresenterInterface,
        PresenterContactLogin.DownPresent,PresenterContactLogin.LoginServiceCallback
        ,PresenterContactLogin.setLoginServiceCallBack,PresenterContactLogin.UpStatusCallback
{
    //初始化操作
    public  static PresenterContactLogin.UpView view;
    public  static PresenterContactLogin.UpStatusCallback callback;
    public  static PresenterContactLogin.setLoginServiceCallBack setcommServiceCallBack;
    //同服务器通信
    //服务是否处于正在接收的状态
    private final String IP="39.108.123.220";
    //初始化一切
    private static MedLoginPresenter basePresenter;
    public Bufferdata bufferdata=Bufferdata.getIntance();
    private HandleJson handleJson=new HandleJson();
    private MedLoginPresenter() {}
    public static MedLoginPresenter getInstance()
    {
        if (basePresenter==null)
            basePresenter=new MedLoginPresenter();
        return basePresenter;
    }
    @Override
    public void sendtoServer(String account,String password) {
        setcommServiceCallBack.sendtoService(handleJson.WrapLoginInfo(account,password));
    }
    @Override
    public void setViewInterface(PresenterContactLogin.UpView view) {
        MedLoginPresenter.view=view;
    }

    @Override
    public void setUpStatusCallback(PresenterContactLogin.UpStatusCallback callback) {
           MedLoginPresenter.callback=callback;
    }

    @Override
    public void setCommService(PresenterContactLogin.setLoginServiceCallBack setcommServiceCallBack) {
        MedLoginPresenter.setcommServiceCallBack = setcommServiceCallBack;
    }

    @Override
    public void start() {
        RxBus.getInstance().toObservale(String.class).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable disposable) {

                    }

                    @Override
                    public void onNext(@NonNull String s) {

                    }

                    @Override
                    public void onError(@NonNull Throwable throwable) {

                    }

                    @Override
                    public void onComplete() {
//                        接收到服务器绑定成功的消息 为服务器设置初始属性
                        Bufferdata.getIntance().initData();
                        setcommServiceCallBack.setCommService();

                        activityView.HidePrepareDialog();
                        synchronized (RecieveData.class)
                        {
                            RecieveData.class.notify();
                        }
                        MyLog.d("LoginPresenter:","取消初始化进度条");
                    }
                });
        //          初始化所有接口
        {
            //这个应该放在后面先定义事情总线
//          开始只会被执行一次
            activityView.ShowPrepareDialog("初始化");
            activityView.bindService();
            activityView.startService();
            view.getSave("user");
            MyLog.d("MedLoginPresenter:","开启服务");
        }
    }

    private Observer<String> obs=new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable disposable) {
        }

        @Override
        public void onNext(@NonNull String s) {
            if(handleJson.HandleLoginInfo(s))
                callback.Correct();
            else
                callback.Error();
            MyLog.d(IP,"data:"+s);
        }

        @Override
        public void onError(@NonNull Throwable throwable) {
            callback.NetError();
        }

        @Override
        public void onComplete() {
        }
    };

    @Override
    public void OnNext(String data) {
        Observable.just(data).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io()).subscribe(obs);
    }

    @Override
    public void OnError() {
        MyLog.d("MedLoginPresenter:","出现错误");
        callback.NetError();
    }

    @Override
    public void setCurrentAccount(String account) {
        bufferdata.setCurrentLoginAccount(account);
    }

    @Override
    public void setCommService() {
        bufferdata.setCommService(this);
    }

    @Override
    public void sendtoService(String data) {
        callback.Start();
        bufferdata.sendData(data);
    }

    @Override
    public void Start() {
        MedBasePresenter.activityView.ShowProBar();
    }

    @Override
    public void Correct() {
        activityView.HideProBar();
        view.savePassword("user");
        view.setCurrentOnServer();
        view.IntoIntact();
    }

    @Override
    public void Error() {
        activityView.HideProBar();
        view.showPasswordError();
    }

    @Override
    public void NetError() {
        activityView.HideProBar();
        activityView.ShowNetErrorWarning("无法连接服务器");
        activityView.showErrorToast();
    }
}
