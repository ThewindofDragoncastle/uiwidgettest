package com.example.uiwidgettest.mvpupdatechat.presenter.chat;


import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.json.HandleJson;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat.setCommService;
import com.example.uiwidgettest.mvpupdatechat.view.adapter.Message;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat.*;

/**
 * Created by 40774 on 2017/10/17.
 */

public class MedChatPresenter implements init,setInterface,
        CommService,chatview,setCommService {
    private HandleJson handleJson=new HandleJson();
    public Bufferdata bufferdata=Bufferdata.getIntance();
    private static MedChatPresenter presenter;
    private chatviewcallback chatviewCallback;
    private MedChatPresenter()
    {}
    private Observer<String> obs=new Observer<String>() {
        @Override
        public void onSubscribe(@NonNull Disposable disposable) {

        }

        @Override
        public void onNext(@NonNull String s) {
            String target=handleJson.HandleChatMessageFromAccount(s);
            if(target.equals(Bufferdata.getTarget())) {
                //如果当前对象与服务器传送的对象相等则显示
                s = handleJson.HandleChatMessageInfo(s);
                MyLog.d("MedChatPresenter:", s);
                Message message = new Message();
                message.setMessage(s);
                chatviewCallback.insertDatetoDB(s, true);
                chatviewCallback.displayData(message);
            }
        }

        @Override
        public void onError(@NonNull Throwable throwable) {

        }

        @Override
        public void onComplete() {

        }
    };
    public static MedChatPresenter getInstance()
    {
        if(presenter==null)
            presenter=new MedChatPresenter();
        return presenter;
    }
    @Override
    public void sendData(String data) {
        bufferdata.sendData(handleJson.WrapChatMessage(data));
    }

    @Override
    public void setCommService(CommService service) {
        bufferdata.setCommService(service);
    }

    @Override
    public void getData(String data) {
        MyLog.d("MedChatPresenter:",data);
        Observable.just(data).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io()).subscribe(obs);
    }

    @Override
    public void start() {
//        添加对登录账号的表
        chatviewCallback.establishDatabase();
//        查询登录账号的表
        chatviewCallback.querydatabases(5);
        bufferdata.sendData(handleJson.WrapRequestunreadInfo());
        setCommService(this);
    }

    @Override
    public void setChatviewCallback(chatviewcallback chatviewCallback) {
        this.chatviewCallback=chatviewCallback;
    }

}
