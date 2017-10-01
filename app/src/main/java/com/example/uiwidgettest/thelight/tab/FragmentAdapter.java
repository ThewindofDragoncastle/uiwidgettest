package com.example.uiwidgettest.thelight.tab;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.uiwidgettest.MyLog;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentPagerAdapter {
    private  List<MyFragment> fragments;
    private Fragment fragment;
    private List<String > title;
    private RecyclerView recyclerView;
    private FragmentManager manager;
    private FragmentTransaction transaction;
    private List<View> viewList=new ArrayList<>();
    private LinearLayout linearLayout;
    public FragmentAdapter(FragmentManager fm, List<MyFragment> fragments,List<String > title) {
        super(fm);
        this.fragments=fragments;
        this.title=title;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

//        //实例化子项 先于getitem执行
//        MyLog.d("碎片适配器：", "初始化父容器");
//        try {
//            View v = ViewList.views.get(position);
//            MyLog.i("ViewPaperAdapter", v.toString());
//            //对前一个进行移除视图
//            ViewGroup parent = (ViewGroup) v.getParent();
//        MyLog.i("ViewPaperAdapter", parent.toString());
//            if (parent != null) {
//                parent.removeAllViews();
//            }
//            container.addView(v);
//            return v;
//        }catch (NullPointerException e)
//        {
//            MyLog.d("碎片适配器：", "未能清除视图");
//        }
        return super.instantiateItem(container,position);
    }

    @Override
    public Fragment getItem(int position) {
        fragment=fragments.get(position);
        return fragment;
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        MyLog.d("碎片适配器：", "标题位置"+position);
        return title.get(position);
    }
}
