package com.example.uiwidgettest.Broadcast;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2017/7/21.
 */

public class Collector {
   public static List<Activity> list = new ArrayList<>();

    public static void addActivity(Activity activity) {
        list.add(activity);
    }

    public static void removeActivity(Activity activity) {
        list.remove(activity);
    }

    public static void clearAllActivity()
    {
        for (Activity  ac:list
             ) {
            if(ac!=null)
            ac.finish();
        }
    }
}
