package com.example.uiwidgettest.mobilemultimedia;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

import java.io.File;

public class Notification_1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Button SendLowestNotification=(Button)findViewById(R.id.SendLowestNotification);
        Button SendFirstNotification=(Button)findViewById(R.id.SendFirstNotification);
        Button SendSecondNotification=(Button)findViewById(R.id.SendSecondNotification);
        Button SendThirdNotification=(Button)findViewById(R.id.SendThirdNotification);
        Button SendFourthNotification=(Button)findViewById(R.id.SendFourthNotification);
        SendFirstNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification_1.this,NotificationNews.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,intent,0);
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("感动仪陇")
                        .setContentText("家住南充的陈某品学兼优，被老师学生多次夸赞...").
                                setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.apppicture).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                        .build();
                notificationManager.notify(1,notification);
            }
        });
        SendSecondNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification_1.this,NotificationNews.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,intent,0);
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("感动仪陇")
                        .setContentText("家住南充的陈某品学兼优，被老师学生多次夸赞...").
                                setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.apppicture).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .build();
                notificationManager.notify(2,notification);
            }
        });
        SendThirdNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification_1.this,NotificationNews.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,intent,0);
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("感动仪陇")
                        .setContentText("家住南充的陈某品学兼优，被老师学生多次夸赞...").
                                setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.apppicture).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .setPriority(NotificationCompat.PRIORITY_LOW)
                        .build();
                notificationManager.notify(3,notification);
            }
        });
        SendFourthNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification_1.this,NotificationNews.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,intent,0);
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("感动仪陇")
                        .setContentText("家住南充的陈某品学兼优，被老师学生多次夸赞...").
                                setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.apppicture).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
.setPriority(NotificationCompat.PRIORITY_MIN)
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                notificationManager.notify(4,notification);
            }
        });
        SendLowestNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification_1.this,NotificationNews.class);
                PendingIntent pendingIntent=PendingIntent.getActivity(getBaseContext(),0,intent,0);
                NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                Notification notification=new NotificationCompat.Builder(getBaseContext())
                        .setContentTitle("感动仪陇")
                        .setContentText("家住南充的陈某品学兼优，被老师学生多次夸赞...").
                        setWhen(System.currentTimeMillis())
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .setSmallIcon(R.mipmap.toicon1).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
//                        .setSound(Uri.fromFile(new File("/system/media/audio/notifications/Leaf.ogg")))
//                        .setVibrate(new long[]{0,1000,2000,1000})
//                        首先要添加权限 其次振动不能让用户觉得不爽
//                        .setVibrate(new long[]{0,1000})
//                        其实使用默认更好
                        .setDefaults(NotificationCompat.DEFAULT_ALL)
                        .build();
                notificationManager.notify(5,notification);
            }
        });
    }
}
