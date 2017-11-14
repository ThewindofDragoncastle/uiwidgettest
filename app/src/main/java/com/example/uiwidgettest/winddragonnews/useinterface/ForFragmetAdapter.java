package com.example.uiwidgettest.winddragonnews.useinterface;

import android.support.v4.app.Fragment;

import com.example.uiwidgettest.winddragonnews.HandleData.Data;

/**
 * Created by 40774 on 2017/11/8.
 */

public interface ForFragmetAdapter {
//    从recyclerview回调至活动两层接口
    interface RecyclerviewToNewsFragment
{
    void ChangeFragmentCallback(Data data);
}
interface NewsFragmentToMainpage
{
    void ChangeFragmentCallback(Data data);
//    获知当前界面
    int getPage();
}
}
