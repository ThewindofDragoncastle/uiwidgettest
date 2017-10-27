package com.example.uiwidgettest.byservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.chatgui;

public class MyService extends Service {
    public MyService() {
    }
    private DownloadBinder downloadBinder=new DownloadBinder();
    class DownloadBinder extends Binder
    {
        public void getProgress()
        {
            Toast.makeText(getBaseContext(),"查看进度被执行。",Toast.LENGTH_SHORT).show();
        }
        public void startDownload()
        {
            Toast.makeText(getBaseContext(),"下载开始。（\n仅仅是模拟下载，并不会下载）",Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
       return downloadBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Intent intent=new Intent(this,chatgui.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("前台服务运行中").setContentText("This a forebackground activityView!")
                .setAutoCancel(true).setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.apppicture).setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
                .setContentIntent(pendingIntent).build();
        startForeground(1,notification);
        Log.d("服务：","被创建");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("服务：","被摧毁");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("服务：","开启后立即执行成功！");
        return super.onStartCommand(intent, flags, startId);
    }
}
