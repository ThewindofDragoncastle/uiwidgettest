package com.example.uiwidgettest.myreview.service;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

/**
 * Created by 40774 on 2017/10/8.
 */

public class GetConnectionService {
    public static BinderListener binderListener;
    public static PlayService.MusicBinder binder;
    public static ServiceConnection conn=new ServiceConnection() {
        @Override
    public  void onServiceConnected(ComponentName name, IBinder service) {
            binder=(PlayService.MusicBinder)service;
            binderListener.Binder(binder);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
}
