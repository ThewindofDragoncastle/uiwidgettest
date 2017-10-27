package com.example.uiwidgettest.mvpupdatechat.model.Service;


import com.example.uiwidgettest.mvpupdatechat.bufferdata.Medbufferdata;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContactLogin;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract;

/**
 * Created by 40774 on 2017/10/15.
 */

public interface Binder {
    //绑定器接口
//    数据回调
    void setDatacallback(Medbufferdata.DataCallback callback);
//    数据输出
    void sendToServer(String data);
}

