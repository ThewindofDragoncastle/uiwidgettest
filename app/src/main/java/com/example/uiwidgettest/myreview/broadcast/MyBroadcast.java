package com.example.uiwidgettest.myreview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by 40774 on 2017/9/30.
 */
//接收充电响应
public class MyBroadcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
       SendNotifaciation sendNotifaciation=new SendNotifaciation();
        sendNotifaciation.send(context,"请查看诗文","正在充电");
    }
}
