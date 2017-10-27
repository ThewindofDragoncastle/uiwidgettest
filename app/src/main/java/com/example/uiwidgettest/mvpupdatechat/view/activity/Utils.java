package com.example.uiwidgettest.mvpupdatechat.view.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.broadcast.seivice.StartService;
import com.example.uiwidgettest.ui.chatgui;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by 40774 on 2017/10/14.
 */

public class Utils {
    public static void addFragment(FragmentManager fragmentManager, Fragment fragment,int FrameId)
    {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(FrameId,fragment);
        transaction.commit();
    }
    public static void replament(FragmentManager fragmentManager,Fragment add,Fragment remove)
    {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.remove(remove);
        transaction.replace(R.id.fragmentplace,add);
        transaction.commit();
    }
}
