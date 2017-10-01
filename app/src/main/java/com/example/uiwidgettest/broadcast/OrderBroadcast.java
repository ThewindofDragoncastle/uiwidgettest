package com.example.uiwidgettest.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class OrderBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"有序广播我已收到。\n并且已经截断\n我的优先级最高为：100",Toast.LENGTH_SHORT).show();
        abortBroadcast();
    }
}
