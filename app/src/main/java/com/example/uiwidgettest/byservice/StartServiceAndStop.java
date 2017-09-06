package com.example.uiwidgettest.byservice;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;

public class StartServiceAndStop extends AppCompatActivity implements View.OnClickListener {
private TextView display;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_service_and_stop);
        Button StartService=(Button)findViewById(R.id.StartService);
        StartService.setOnClickListener(this);
        Button StopService=(Button)findViewById(R.id.StopService);
        StopService.setOnClickListener(this);
        Button BindService=(Button)findViewById(R.id.BindService);
        BindService.setOnClickListener(this);
        Button unBindService=(Button)findViewById(R.id.unBindService);
        unBindService.setOnClickListener(this);
        Button StartForeground=(Button)findViewById(R.id.StartForeground);
        StartForeground.setOnClickListener(this);
        Button StartIntentService=(Button)findViewById(R.id.StartIntentService);
        StartIntentService.setOnClickListener(this);
        display=(TextView)findViewById(R.id.Serverdisplay);
    }
    private MyService.DownloadBinder downloadBinder;
private ServiceConnection connection=new ServiceConnection() {
    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        downloadBinder=(MyService.DownloadBinder)service;
        downloadBinder.getProgress();
        downloadBinder.startDownload();
        Log.d("StarServiceAndStop:","服务同绑定器连接");
    }

    @Override
    public void onServiceDisconnected(ComponentName name) {
        Log.d("StarServiceAndStop:","服务同绑定器断开");
    }
};
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.StartService:
               intent=new Intent(StartServiceAndStop.this,MyService.class);
                startService(intent);
                break;
            case R.id.StopService:
                display.setText("前台服务停止。");
                intent=new Intent(StartServiceAndStop.this,MyService.class);
                stopService(intent);break;
            case R.id.BindService:
                intent=new Intent(StartServiceAndStop.this,MyService.class);
                bindService(intent,connection,BIND_AUTO_CREATE);
                break;
            case R.id.unBindService:
               unbindService(connection);
                break;
            case R.id.StartForeground:
                display.setText("前台服务启动。");
                intent=new Intent(StartServiceAndStop.this,MyService.class);
                startService(intent);
                break;
            case  R.id.StartIntentService:
                Log.d("StartServiceAndStop:","主线程："+Thread.currentThread().getName());
                intent=new Intent(StartServiceAndStop.this,MyIntentService.class);
                startService(intent);
        }
    }
}
