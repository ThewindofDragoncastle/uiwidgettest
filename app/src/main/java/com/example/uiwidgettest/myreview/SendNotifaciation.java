package com.example.uiwidgettest.myreview;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
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

public class SendNotifaciation {
    public void send(Context context,String content,String title)
    {
        Toast.makeText(context,content,Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder build=new NotificationCompat.Builder(context);
        Intent intentq=new Intent(context, TabActivity.class);
        PendingIntent pi=PendingIntent.getActivity(context,1,intentq,0);
        build.setAutoCancel(true).setContentText(content)
                .setContentIntent(pi).setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.apppicture))
                .setSmallIcon(R.drawable.apppicture)
                .setContentTitle(title);
        Notification notification=build.build();
        NotificationManager manager=(NotificationManager)context. getSystemService(NOTIFICATION_SERVICE);
        manager.notify(1,notification);
    }
}
