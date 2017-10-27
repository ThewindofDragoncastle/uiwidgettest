package com.example.uiwidgettest.mvpupdatechat.model.Service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.framework.rxjava.useclass.RxBus;

/**
 * Created by 40774 on 2017/10/15.
 */

public class ConnwithService {
    private static RecieveData.ConnModel connModel=null;
    private static ServiceConnection connection=new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        connModel=(RecieveData.ConnModel) service;
        //向正在处于订阅的MedPresenter发送指令，关闭“正在准备进度框”的指令
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //故意休息两秒 造成延时
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                RxBus.getInstance().complete();
                MyLog.d("ServiceConnection:","绑定服务成功");
            }
        }).start();

    }

    @Override
    public void onServiceDisconnected(ComponentName name) {

    }
};
public static ServiceConnection getConnection()
{
    return connection;
}
    public static RecieveData.ConnModel getConnModel()
{
    return connModel;
}
}
