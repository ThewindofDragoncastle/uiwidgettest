package com.example.uiwidgettest.ui.sendorrecieve;

import com.example.uiwidgettest.MyLog;

import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by 40774 on 2017/9/5.
 */

public class Send {
    //对发送数据的包装
    static boolean first=true;//给服务器发送一次当前信息
    public static void sendtoserver(final DataOutputStream dos, final String sb, final String type)
    {

    new Thread(new Runnable() {
        @Override
        public void run() {
            try {
                dos.writeUTF(type+":"+sb);
                dos.flush();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }).start();
    }
    public static void sendcurrentinfo(final DataOutputStream dos,final String account)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    if(first)
                        dos.writeUTF("账号信息:"+account+":");
                    MyLog.i("Send:","当前账号："+account);
                       dos.flush();
                }catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
