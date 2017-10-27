package com.example.uiwidgettest.mvpupdatechat.bufferdata;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.mvpupdatechat.json.HandleJson;
import com.example.uiwidgettest.mvpupdatechat.model.Service.ConnwithService;
import com.example.uiwidgettest.mvpupdatechat.model.Service.RecieveData;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContactLogin;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat;
import com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractIntract;
import com.example.uiwidgettest.mvpupdatechat.view.activity.CurrentData;
import com.example.uiwidgettest.mvpupdatechat.view.activity.MainPage;
import com.example.uiwidgettest.mvpupdatechat.view.fragment.FragmentCollector;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.example.uiwidgettest.mvpupdatechat.bufferdata.Medbufferdata.*;

/**
 * Created by 40774 on 2017/10/19.
 */

public class Bufferdata implements DataCallback,setData,senddata,init{
    private HandleJson handleJson=new HandleJson();
    private static String target="";
    private static Bufferdata bufferdata;
    public static RecieveData.ConnModel connModel;
    private static String CurrentAccount=null;
    private PresenterContractChat.CommService chat;
    private PresenterContactLogin.LoginServiceCallback loginServiceCallback;
    private PresenterContractIntract.IntactServiceCallBack intactServiceCallBack;
    private Bufferdata()
    {

    }
    public static Bufferdata getIntance()
    {
        if(bufferdata==null)
            bufferdata=new Bufferdata();
        return bufferdata;
    }
    //再进入聊天界面时，标记聊天的目标是谁
    public static String getTarget() {
        return target;
    }

    public static void setTarget(String target) {
        Bufferdata.target = target;
    }
    @Override
    public void setIntactConn(PresenterContractIntract.IntactServiceCallBack conn) {
        intactServiceCallBack =conn;
    }

    @Override
    public void setCommService(PresenterContractChat.CommService service) {
        chat=service;
    }

    @Override
    public void setCurrentLoginAccount(String account) {
        CurrentAccount=account;
    }

    @Override
    public String getCurrentLoginAccount() {
        return CurrentAccount;
    }

    @Override
    public void setCommService(PresenterContactLogin.LoginServiceCallback service) {
        loginServiceCallback =service;
    }

    @Override
    public void getData(String data) {
//        这里如果获取到消息是在非聊天界面 就应该发送通知
        switch (handleJson.GetHeadData(data))
        {
            case "login":
                //从逻辑上讲转发的对象必须是合符碎片的
                if(CurrentData.getCurrentfragment().equals(FragmentCollector.GetLogin()))
                loginServiceCallback.OnNext(data);break;
            case "intact":
                if(CurrentData.getCurrentfragment().equals(FragmentCollector.GetIntact()))
                intactServiceCallBack.Complete(data);break;
            case "chat":
                    if (CurrentData.getCurrentfragment().equals(FragmentCollector.GetChat()))
                        chat.getData(data);
                    else
                        intactServiceCallBack.sendUnreadMessageForResult(data);
                //希望服务器更新返回联系人数据
                break;
        }

    }

    @Override
    public void NetError() {
        loginServiceCallback.OnError();
    }

    @Override
    public void sendData(String data)
    {
        connModel.sendToServer(data);
    }

    @Override
    public void initData() {
        //初始化数据
        connModel=ConnwithService.getConnModel();
        connModel.setDatacallback(this);
    }
    @Override
    public void sendNotication(Context context, String data) {
//        将消息上传到服务器
        MyLog.d("sss",data);
        // 不需要结果仅仅向服务器上传未读数据
        intactServiceCallBack.sendUnreadMessageNoforResult(data);
        data=handleJson.HandleChatMessageInfo(data);
        //应该传入目标账户和自己账户
        Intent intent = new Intent(context,MainPage.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        NotificationManager manager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder= new NotificationCompat.Builder(context);
         builder.setContentIntent(pi)
                .setPriority(NotificationCompat.PRIORITY_MAX).setContentIntent(pi)
                .setContentTitle("你有未读消息")
                .setWhen(System.currentTimeMillis())
                .setContentText(data)
                .setSmallIcon(R.mipmap.apppicture)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.apppicture))
                .setAutoCancel(true)
                .build();
        manager.notify(1, builder.build());
    }
}
