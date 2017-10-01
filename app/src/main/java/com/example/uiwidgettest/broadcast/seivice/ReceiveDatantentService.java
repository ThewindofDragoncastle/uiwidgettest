package com.example.uiwidgettest.broadcast.seivice;
//回调数据后再回调给活动 经过教训最后得出回调函数应该放在服务中 否则不能一开始就传入回调函数
//最后通过绑定器返回数据 不需要回调函数返回数据
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.chatgui;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
//利用回调函数解绑服务
public class ReceiveDatantentService extends IntentService {
    private static String password="";
    //临时保存密码
    private static WeReceive receive;
    private static final int LOGINACTIVITY=0;
    private static final int INTACTACTIVITY=1;
    private static String targetaccount;
    private static final int CHATGUI=2;
    private static String account=null;
    private static int currentActivity=-1;
    private static final String TAG="ReceiveDatantentService:";
    private static DataOutputStream dataOutputStream;
    private static Socket socket;
    private static DataListener dataListener;
    public  static ServiceConnection connection;
    private static DataInputStream dis;
    private static Intent sintent;
    public ReceiveDatantentService()
    {
        super("接收数据服务");
    }

    @Override
    public void onCreate() {
        receive=new WeReceive();
        super.onCreate();

//                Socket socket = new Socket("39.108.123.220", 30000);
            //在服务被创造时连接网络服务器
            MyLog.d(TAG,"连接服务器成功，服务被创造");
    }

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        dataListener=(DataListener)intent.getSerializableExtra("DataListener");
        MyLog.d(TAG,"服务开始运行。");
        return super.onStartCommand(intent, flags, startId);
    }
    @Override
    public IBinder onBind(Intent intent) {
        return receive;
    }
    public class WeReceive extends Binder implements Serializable
    {
        public void SetDataListener(DataListener dataListener1)
        {
           dataListener =dataListener1;       //将监听器传递进来
             dataListener.Already();
        }
        public String getTargetAccount()
        {
            return   targetaccount;   //把登录账号传递进来
        }
        public String getCurrentAccount()
        {
            return  account;
        }
        public void SetCurrentAccount(String ac)
        {
            account=ac;     //把登录账号传递进来
        }
        public void SetTargetAccount(String ac)
        {
            targetaccount=ac;     //把登录账号传递进来
        }
        public void SetCurrentActivity(int i)
        {
            currentActivity=i;       //把活动传递进来
        }
        public void SetPassword(String spassword)
        {//写入密码以方便重新连接
            password=spassword;
        }
        public boolean WriteToService(final String content)  {
            try {
            if(dataOutputStream!=null)
            {
                    dataOutputStream.writeUTF(content);
                    dataOutputStream.flush();
                MyLog.d("ReceiveDatantentService:","写入服务器成功:"+content);
                return true;
            }
            else {
                MyLog.d("ReceiveDatantentService:", "输出流为空写入服务器失败");
                return false;
            }
            } catch (IOException e) {
                e.printStackTrace();

                    //如果出现问题就重新开启线程，开启服务
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                socket.close();
                                onHandleIntent(sintent);//重新开启服务
                            MyLog.d("ReceiveDatantentService:","Socket重新开启");
                                //应当发送登录请求让客户端知道你的账号
                            dataOutputStream=new DataOutputStream(socket.getOutputStream());
                            dataListener.Already();
                            dis=new DataInputStream(socket.getInputStream());
//                                WriteToService(content);//再次请求
               receive.WriteToService("登录:"+account+":"+"孤傲的服务器连接:");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    }).start();

                MyLog.d("ReceiveDatantentService:","输出流不为空但写入服务器失败");
                return false;
            }
        }
//      public Socket getSocket()
//      {
//          return  socket;//返回socket但是不能使用获得流
//      }

    }
    @Override
    protected  void onHandleIntent(@Nullable Intent intent) {
        sintent=intent;
        MyLog.d(TAG,"服务异步处理被调用！");
        MyLog.i(TAG,"当前线程："+Thread.currentThread().getName());
//        dataListener.TounbindService();//利用回调函数解绑服务
//        解绑应当由活动调用额呵呵呵呵
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        {
            onwait();
            dataListener.ReturnData( "多一点儿，人文精神，多一丝玄之又玄的深意");
            try{
                socket = new Socket("39.108.123.220", 30000);
                dataOutputStream=new DataOutputStream(socket.getOutputStream());
                dataListener.Already();
                dis=new DataInputStream(socket.getInputStream());
                String content=null;
                while ((content=dis.readUTF())!=null)
                {
                    MyLog.i(TAG,"从服务器收到的信息："+content+" 头文件："+content.split(":")[0]);
                    if(content.split(":")[0].equals("强制下线"))
                    {
                        Intent intent1=new Intent("com.example.uiwidgettest.GOFORITOUTLINE");
//        manager.sendBroadcast(intent);
                        sendBroadcast(intent1);
                    }
                    else if (!content.split(":")[0].equals("消息"))
                    dataListener.ReturnData(content);//将数据回调给活动
//                    "多一点儿，人文精神，多一丝玄之又玄的深意"
                    else if(content.split(":")[0].equals("消息")&&currentActivity!=CHATGUI)//不是在chatgui界面而又收到没有字头的数据那么一定是消息
                    {
                        SetNotification(content.split(":")[1]);//通知用户
                    }
                    else if(content.split(":")[0].equals("消息")&&currentActivity==CHATGUI)
                    {
                        dataListener.ReturnData(content.split(":")[1]);
                    }
                }

            }catch (IOException e) {
                MyLog.d(TAG, "服务显示，服务器断开");

            }
        }
    }
private void SetNotification(String data)
{
    //应该传入目标账户和自己账户
    Intent intent=new Intent(this, chatgui.class);

//    intent.putExtra("WeReceive",receive);//对象
    intent.putExtra("Message",data);//信息
//    intent.putExtra("Account",account);//账户
    StartService.weReceive.SetTargetAccount(targetaccount);
//    intent.putExtra("TargetAccount",targetaccount);//目标账户不能依靠服务器传回 服务器还需要这个上传
    PendingIntent pi=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
//    PendingIntent pi1=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT);
    NotificationManager manager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    Notification notification=new NotificationCompat.Builder(this)
            .setFullScreenIntent(pi,true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentTitle("你有未读消息")
            .setWhen(System.currentTimeMillis())
            .setContentText(data)
            .setSmallIcon(R.mipmap.apppicture)
            .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.apppicture))
            .setAutoCancel(true)
            .build();
          manager.notify(1,notification);
}
    @Override
    public void onDestroy() {
        super.onDestroy();
        MyLog.d(TAG,"服务执行完，将被摧毁。");
    }
    public  void onwait()
    {
        while (dataListener==null)
        {
            try {
                synchronized(SysS.WERECEIVE) {
                    MyLog.d(TAG,"线程进入等待，等待被唤醒。");
                    SysS.WERECEIVE.wait();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}


