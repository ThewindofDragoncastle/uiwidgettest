package com.example.uiwidgettest.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.example.uiwidgettest.R;

/**
 * Created by 40774 on 2017/7/21.
 */

public class BaseActivity extends AppCompatActivity {
    ForceOffLine forceOffLine;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Collector.removeActivity(this);
        if(forceOffLine!=null) {
            unregisterReceiver(forceOffLine);
            forceOffLine=null;
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Collector.addActivity(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(forceOffLine!=null) {
            unregisterReceiver(forceOffLine);
            forceOffLine=null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.UIWidgetTest.FORCEOFFLINE");
        forceOffLine=new ForceOffLine();
        registerReceiver(forceOffLine,intentFilter);
    }
    class ForceOffLine extends BroadcastReceiver
    {
        @Override
        public void onReceive(final Context context, Intent intent) {
            AlertDialog.Builder builder=new AlertDialog.Builder(context);
            builder.setCancelable(false);
            builder.setTitle("登录异常");
            builder.setMessage(" 你的账号被迫下线，原因不明。");
            builder.setPositiveButton("重新登录", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Collector.clearAllActivity();
                    Intent intent1=new Intent(context,LoginActivity.class);
                    startActivity(intent1);
                }
            });
            builder.show();
        }
    }
}
