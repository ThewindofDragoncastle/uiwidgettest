package com.example.uiwidgettest.broadcast.seivice;

import android.content.ComponentName;
import android.content.ServiceConnection;
import android.os.IBinder;

import com.example.uiwidgettest.MyLog;

/**
 * Created by 40774 on 2017/9/16.
 */

public class StartService {
    public static ReceiveDatantentService.WeReceive weReceive;
    public  static DataListener dataListener;
    public static  ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyLog.d("开启服务：","服务被绑定");
            weReceive= (ReceiveDatantentService.WeReceive)service;
            weReceive.SetDataListener(dataListener);
            //应该先设置监听器否则唤醒了线程她还是会睡觉
            synchronized(SysS.WERECEIVE) {
                SysS.WERECEIVE.notify();
                MyLog.d("开启服务：","唤醒SysS.WERECEIVE锁定的线程。");
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
MyLog.d("开启服务：","解绑成功");
        }
    };
    public static void setDataListener(DataListener dataListener1)
    {
         dataListener=dataListener1;
    }
//    public static ServiceConnection getConnection()
//    {
//        MyLog.d("开启服务：","返回conn"+connection);
//        return connection;
//    }
//    public ReceiveDatantentService.WeReceive getWeReceive()
//    {
//        return weReceive;
//    }
}
