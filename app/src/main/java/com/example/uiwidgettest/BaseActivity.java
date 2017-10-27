package com.example.uiwidgettest;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/10/22.
 */

public class BaseActivity extends AppCompatActivity {
   static List<Activity> activities=new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        MyLog.d("BaseActivity:","活动添加");
        activities.add(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activities.remove(this);
    }
    public void ClearAll()
    {
        for(Activity activity:activities)
        {
            if(!activity.isDestroyed())
                activity.finish();
        }
        activities.clear();
    }
}
