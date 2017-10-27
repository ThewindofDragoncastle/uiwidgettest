package com.example.uiwidgettest.framework.mvp.useclass;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by 40774 on 2017/10/13.
 */

public class ActivityUtils {
    public static void  addFragmentToActivity(FragmentManager fragmentManager,Fragment fragment
    ,int frameId)
    {
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.add(frameId,fragment);
        transaction.commit();
    }
}
