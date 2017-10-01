package com.example.uiwidgettest.myreview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.thelight.tab.TabActivity;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by 40774 on 2017/9/30.
 */

public class MyBroadcast extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent) {
       SendNotifaciation sendNotifaciation=new SendNotifaciation();
        sendNotifaciation.send(context,"请查看诗文","正在充电");
    }
}
