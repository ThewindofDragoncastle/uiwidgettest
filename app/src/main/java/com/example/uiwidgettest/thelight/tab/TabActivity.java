package com.example.uiwidgettest.thelight.tab;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.uiwidgettest.R;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.security.Permission;
import java.util.ArrayList;
import java.util.List;
//滑动页面为了流程加载出当前界面也会加载下一个界面以滑动界面为标准
public class TabActivity extends AppCompatActivity implements FloatActionButtonOnClickListener {
    List<MyPlan> myPlen=new ArrayList<>();
    List<MyFragment> myfragment=new ArrayList<>();
    List<String> titles;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    //用来初始化文章
    //
    private final int WRITETOSD=0;
    private FragmentAdapter adapter;
    private initArticle article=new initArticle();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);
        Toolbar logintoolbar=(Toolbar)findViewById(R.id.logintoolbar);
        setSupportActionBar(logintoolbar);
        getSupportActionBar().setTitle(null);
        viewPager=(ViewPager)findViewById(R.id.viewPager);
        initViewPager();

    }
    private void initViewPager()
    {
        tabLayout=(TabLayout)findViewById(R.id.tabs);
        titles=new ArrayList<>();
        titles.add("洛神赋");
        titles.add("滕王阁序");
        titles.add("出师表");
        titles.add("项脊轩志");
        titles.add("陈情表");
        titles.add("阿房宫赋");
        initFragment();
        adapter=new FragmentAdapter(getSupportFragmentManager(),myfragment,titles);
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager); 
//        tabLayout.setTabsFromPagerAdapter(adapter);
    }

    private void initFragment()
    {
        myfragment.clear();
        MyFragment[] myFragments=new MyFragment[titles.size()];//碎片的个数
        for (int i=0;i<titles.size();i++) {
            //
            switch (titles.get(i)){
                case "洛神赋":
                    article.initLSF(myPlen);
                    break;
                case "出师表":
                    article.initCSB(myPlen);
                    break;
                case "项脊轩志":
                    article.initXJXZ(myPlen);
                    break;
                case "陈情表":
                    article.initCQB(myPlen);
                    break;
                case "阿房宫赋":
                    article.initEPGF(myPlen);
                    break;
                case "滕王阁序":
                    article.initTWGX(myPlen);
                    break;
            }
            myFragments[i]=new MyFragment();
            myFragments[i].ininMyPlan(myPlen,titles.get(i),this);//将myplan的集合传入碎片
            myfragment.add(myFragments[i]);
            myPlen.clear();
        }
    }
    public void requestPermissionsWithWriteSD()
    {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},WRITETOSD);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case WRITETOSD:
                if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(this,"Please do previous things again!",Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(this,"We have to say sorry to your reject!",Toast.LENGTH_SHORT).show();
                break;
        }
    }
    @Override
    public boolean onClick(String title) {
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                PackageManager.PERMISSION_GRANTED) {
            requestPermissionsWithWriteSD();
            return false;
        }
        else {
            Toast.makeText(this, "当前标题:" + title, Toast.LENGTH_SHORT).show();
            switch (title) {
                case "洛神赋":

                    break;
                case "出师表":
//                article.initCSB(myPlen);
                    break;
                case "项脊轩志":
//                article.initXJXZ(myPlen);
                    break;
                case "陈情表":
//                article.initCQB(myPlen);
                    break;
                case "阿房宫赋":
//                article.initEPGF(myPlen);
                    break;
                case "滕王阁序":
//                article.initTWGX(myPlen);
                    break;
            }
            return true;
        }
    }

}

