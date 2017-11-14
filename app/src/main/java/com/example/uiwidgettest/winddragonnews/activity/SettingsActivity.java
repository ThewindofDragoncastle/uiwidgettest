package com.example.uiwidgettest.winddragonnews.activity;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.news.Content;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.fragment.NewsContent;
import com.example.uiwidgettest.winddragonnews.fragment.NewsFragment;
import com.example.uiwidgettest.winddragonnews.fragment.SettingFragment;
import com.example.uiwidgettest.winddragonnews.presenter.ActivityDataSupport;

public class SettingsActivity extends AppCompatActivity {
    private final String TAG=getClass().getName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyLog.d(TAG,"活动创建");
//        根据传入的参量来判断应当处于那个碎片
        android.support.v4.app.FragmentTransaction transaction= this.getSupportFragmentManager()
                .beginTransaction();
        if(getIntent().getIntExtra("fragment",-1)==0) {
//            设置界面
            this.getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingFragment())
                    .commit();
        }
        else if(getIntent().getIntExtra("fragment",-1)==1) {
//            我的收藏界面
            NewsFragment fragment=new NewsFragment();
           transaction.replace(android.R.id.content,fragment)
                    .commit();
            fragment.setDataList(ActivityDataSupport.getInstance().queryDatalist("favorite"));
        }
        else if (getIntent().getIntExtra("fragment",-1)==2)
        {
//            关于开发者
            transaction.replace(android.R.id.content,new NewsContent())
                    .commit();
        }
    }
}
