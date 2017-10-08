package com.example.uiwidgettest.myreview.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.myreview.service.GetConnectionService;
import com.example.uiwidgettest.myreview.service.MusicStatusListener;
import com.example.uiwidgettest.myreview.service.PlayService;

public class MusicPause extends BroadcastReceiver  {
    //接收三个广播。
    MusicStatusListener listener;
    @Override
    public void onReceive(Context context, Intent intent) {
        MyLog.d("MusicPause静态广播：","收到的广播："+intent.getAction());
        PlayService.MusicBinder binder=GetConnectionService.binder;
        if(GetConnectionService.binder!=null)
        {
            listener=binder.getMusicStatusListener();
            switch (intent.getAction())
            {
                case "com.example.uiwidgettest.PAUSEMUSIC":Pause();break;
                case "com.example.uiwidgettest.BACKMUSIC":Back();break;
                case "com.example.uiwidgettest.FORWARDMUSIC":Forward();break;
            }
        }
        else
            Toast.makeText(MyApplication.getContext(),"后台服务不可使用。",Toast.LENGTH_SHORT).show();


    }
   private void Pause()
{
    listener.pause();
    MyLog.d("MusicPause静态广播：","音乐暂停");
}
   private void Back()
{
      listener.back();
}
    private void Forward()
    {
        listener.forward();
    }
}
