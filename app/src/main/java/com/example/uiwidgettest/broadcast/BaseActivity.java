package com.example.uiwidgettest.broadcast;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/7/21.
 */

public class BaseActivity extends AppCompatActivity
{
    private static List<Activity> activities=new ArrayList<>();
    final String TAG="BaseActivity";
    private ForceOffLine offLine;
//    private LocalBroadcastManager manager;
    private class ForceOffLine extends BroadcastReceiver
    {
        @Override
        public void onReceive(final Context context, Intent intent) {
            MyLog.d(TAG,"我已经收到广播。");

            AlertDialog.Builder dialogn=new AlertDialog.Builder(context);
            dialogn.setCancelable(false);
            dialogn.setTitle("账号状态异常！");
            dialogn.setMessage("你的账号在别处登录，如果不是本人操作，请修改密码。");
         dialogn.setPositiveButton("确定", new DialogInterface.OnClickListener()
       {
           @Override
           public void onClick(DialogInterface dialog, int which) {
               Intent intent1=new Intent(context,LoginActivity.class);
//               activities.clear();不需要摧毁活动直接清除
               clearAll();
               startActivity(intent1);
           }
        });
            dialogn.setNegativeButton("修改密码", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Toast.makeText(context,"哈哈我们并不接受修改密码！",Toast.LENGTH_SHORT).show();
                    Intent intent1=new Intent(context,LoginActivity.class);
                    clearAll();
//               activities.clear();不需要摧毁活动直接清除
                    startActivity(intent1);
                }
            });
//            Toast.makeText(context,"哈哈我们并不接受修改密码！",Toast.LENGTH_SHORT).show();
                    dialogn.show();

        }

    }
    public void sendBroad()
    {
        Intent intent=new Intent("com.example.uiwidgettest.GOFORITOUTLINE");
//        manager.sendBroadcast(intent);
        sendBroadcast(intent);
        MyLog.d(TAG,"广播发送成功！");
    }
private void clearAll()
{
    for (Activity activity:activities)
    {
        if(!activity.isFinishing())
        activity.finish();
    }
}
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activities.add(this);
        MyLog.d(TAG,"活动被添加");
      offLine=new ForceOffLine();
//        manager=LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.uiwidgettest.GOFORITOUTLINE");
//        manager.registerReceiver(offLine,intentFilter);
        registerReceiver(offLine,intentFilter);
    }

    protected void OnResume()
    {
        super.onResume();
//        manager=LocalBroadcastManager.getInstance(this);
        MyLog.d(TAG,"广播接收器重新开启");
        IntentFilter filter=new IntentFilter();
        offLine=new ForceOffLine();
        filter.addAction("com.example.uiwidgettest.GOFORITOUTLINE");
//        manager.registerReceiver(offLine,filter);
        registerReceiver(offLine,filter);
    }

    @Override
    protected void onPause() {
//        manager=LocalBroadcastManager.getInstance(this);
        MyLog.d(TAG,"广播接收器被摧毁，活动被暂停");
        super.onPause();
        if(offLine!=null)
        {
//            manager.unregisterReceiver(offLine);
            unregisterReceiver(offLine);
            offLine=null;
        }
    }


    @Override
    protected void onDestroy() {
        MyLog.d(TAG,"活动被摧毁");
        super.onDestroy();
        activities.remove(this);
    }
    //    ForceOffLine forceOffLine;
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        Collector.removeActivity(this);
//        if(forceOffLine!=null) {
//            unregisterReceiver(forceOffLine);
//            forceOffLine=null;
//        }
//    }
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Collector.addActivity(this);
//    }
//
//    @Override
//    protected void onPause() {
//        super.onPause();
//        if(forceOffLine!=null) {
//            unregisterReceiver(forceOffLine);
//            forceOffLine=null;
//        }
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        IntentFilter intentFilter=new IntentFilter();
//        intentFilter.addAction("com.example.UIWidgetTest.FORCEOFFLINE");
//        forceOffLine=new ForceOffLine();
//        registerReceiver(forceOffLine,intentFilter);
//    }
//    class ForceOffLine extends BroadcastReceiver
//    {
//        @Override
//        public void onReceive(final Context context, Intent intent) {
//            AlertDialog.Builder builder=new AlertDialog.Builder(context);
//            builder.setCancelable(false);
//            builder.setTitle("登录异常");
//            builder.setMessage(" 你的账号被迫下线，原因不明。");
//            builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int which) {
//                    Collector.clearAllActivity();
//                    Intent intent1=new Intent(context,LoginActivity.class);
//                    startActivity(intent1);
//                }
//            });
//            builder.show();
//        }
//    }
}
