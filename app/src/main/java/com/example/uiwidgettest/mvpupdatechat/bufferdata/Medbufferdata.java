package com.example.uiwidgettest.mvpupdatechat.bufferdata;

import android.content.Context;

import com.example.uiwidgettest.mvpupdatechat.presenter.BaseModelService;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContactLogin;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract;

/**
 * Created by 40774 on 2017/10/19.
 */

public interface Medbufferdata {
    //接口以及其实现类作用：作为代替服务的Model存在，处理或转发服务的逻辑
    interface DataCallback extends BaseModelService.Callback
    {
        //向上层转发回调数据
        void getData(String data);
        void NetError();
        //  处理服务的逻辑操作 弹出通知 服务回调
        void sendNotication(Context context,String data);
    }
    interface init
    {
        //本类的行为规范
        //在服务器连接上时，在获取到connMel
        void initData();
    }
    interface senddata extends BaseModelService.Down
    {
//        上层对本层的调用以实现向服务器发送数据
        void sendData(String data);
    }
    interface setData extends BaseModelService.Down
    {
//        上层对本层的调用设置各个对服务调用的接口
        void setCommService(PresenterContactLogin.LoginServiceCallback service);
        void setIntactConn(PresenterContractIntract.IntactServiceCallBack conn);
        void setCommService(PresenterContractChat.CommService service);
        void setCurrentLoginAccount(String account);
        String getCurrentLoginAccount();
    }
}
