package com.example.uiwidgettest.byservice;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by 40774 on 2017/8/17.
 */

public class MyIntentService extends IntentService {
    public MyIntentService()
    {
        super("MyIntentService heihei");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("MyIntentService:",Thread.currentThread().getName()+"");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("MyIntentService:","被摧毁。");
    }
}
