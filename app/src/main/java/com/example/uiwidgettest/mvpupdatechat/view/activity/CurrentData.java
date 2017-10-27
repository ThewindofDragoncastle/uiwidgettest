package com.example.uiwidgettest.mvpupdatechat.view.activity;

import android.support.v4.app.Fragment;

/**
 * Created by 40774 on 2017/10/22.
 */
//用数据库可能会干扰
public class CurrentData {
    private static Fragment currentfragment=null;

    public static void setCurrentfragment(Fragment currentfragment) {
        CurrentData.currentfragment = currentfragment;
    }

    public static Fragment getCurrentfragment() {
        return currentfragment;
    }
}
