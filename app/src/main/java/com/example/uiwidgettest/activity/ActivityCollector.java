package com.example.uiwidgettest.activity;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/6/30.
 */

public class ActivityCollector {
    public static List<Activity> al=new ArrayList<Activity>();
    //放置所有活动对象
    public static void addActivity(Activity at)
    {
        al.add(at);
    }
 public  static void removeActivity(Activity at)
 {
     al.remove(at);
 }
 public static void clearall()
 {
     for (Activity at:al)
     {
         if(!al.isEmpty())
         {
             at.finish();
         }
     }
       al.clear();
 }
}
