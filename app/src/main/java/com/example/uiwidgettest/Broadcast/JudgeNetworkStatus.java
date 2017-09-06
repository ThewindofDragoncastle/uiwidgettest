package com.example.uiwidgettest.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.uiwidgettest.R;

public class JudgeNetworkStatus extends AppCompatActivity {
IntentFilter intentFilter;
    JudgeStatus judgeStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jduge);
        intentFilter=new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        judgeStatus=new JudgeStatus();
        registerReceiver(judgeStatus,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(judgeStatus);
    }

    class JudgeStatus extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager=
                    (ConnectivityManager)getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
            if(networkInfo!=null&&networkInfo.isAvailable())
            {
                Toast.makeText(context,"已经连接网络",Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(context,"未连接网络！",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
