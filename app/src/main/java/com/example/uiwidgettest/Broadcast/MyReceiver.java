package com.example.uiwidgettest.Broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String ch=intent.getStringExtra("sendfromDIY");
        Toast.makeText(context,"接收到的数据是："+ch,Toast.LENGTH_SHORT).show();
    }
}
