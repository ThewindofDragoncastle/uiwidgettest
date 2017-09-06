package com.example.uiwidgettest.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.example.uiwidgettest.R;
/**
 * Created by 40774 on 2017/6/29.
 */
//这个类作用是为了获得每个活动是什么名字它代替AppCompatActivity成为所有活动的父类
public class BaseActivity extends AppCompatActivity {
    protected  void onCreate(Bundle savedInstance)
    {
        super.onCreate(savedInstance);
        Log.d("BaseActivity",getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }
    protected  void onDestory()
    {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    //如果程序被消除那么集合对象也跟着消除。
    }
}
