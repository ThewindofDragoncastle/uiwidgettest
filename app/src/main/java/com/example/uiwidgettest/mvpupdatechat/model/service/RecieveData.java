package com.example.uiwidgettest.mvpupdatechat.model.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Medbufferdata;
import com.example.uiwidgettest.mvpupdatechat.view.activity.CurrentData;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class RecieveData extends IntentService {
    private final String IP="39.108.123.220";
    private DataOutputStream dos;
    private DataInputStream dis;
    private Medbufferdata.DataCallback callback;
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        MyLog.d("RecieveData","服务开启");
        return super.onStartCommand(intent, flags, startId);
    }

    public RecieveData() {
        super("RecieveData");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ConnModel();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        try {
            synchronized (RecieveData.class)
           {
                RecieveData.class.wait();
           }
            Socket socket = new Socket(IP, 30000);
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
            String data = null;
            MyLog.d("RecieveData:","休眠结束。");
            while ((data=dis.readUTF())!=null)
            {
                MyLog.d("RecieveData:","data:"+data);
                if(CurrentData.getCurrentfragment()!=null) {
                    callback.getData(data);
                }
                  else
                {
//             如果碎片为空说明此时已经不在服务界面 所以弹出通知
                   callback.sendNotication(this,data);
                }

            }
        } catch (IOException e) {
            MyLog.e("RecieveData:",e.toString());
                callback.NetError();
        } catch (InterruptedException e) {
            MyLog.e("RecieveData:",e.toString());
               callback.NetError();
        }
    }
 public  class ConnModel extends Binder implements com.example.uiwidgettest.mvpupdatechat.model.service.Binder {


     @Override
     public void setDatacallback(Medbufferdata.DataCallback callback1) {
         callback=callback1;
     }

     @Override
     public void sendToServer(String data) {
         try {
             dos.writeUTF(data);
         } catch (IOException e) {
             MyLog.e("RecieveData:",e.toString());
             callback.NetError();
         }
         catch (NullPointerException e)
         {
             //如果报空指针异常说明 连接不上网络
             MyLog.e("RecieveData:",e.toString());
             callback.NetError();
         }
     }
 }
}
