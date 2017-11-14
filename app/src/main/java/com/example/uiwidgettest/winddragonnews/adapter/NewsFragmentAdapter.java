package com.example.uiwidgettest.winddragonnews.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.uiwidgettest.winddragonnews.fragment.NewsFragment;

import java.util.List;

/**
 * Created by 40774 on 2017/11/7.
 */

public class NewsFragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> newsFragments;
    private String[] titles;
    public NewsFragmentAdapter(FragmentManager fm, List<Fragment> newsFragments,String[] titles) {
        super(fm);
        this.newsFragments=newsFragments;
        this.titles=titles;
    }

    @Override
    public Fragment getItem(int position) {
        return newsFragments.get(position);
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
