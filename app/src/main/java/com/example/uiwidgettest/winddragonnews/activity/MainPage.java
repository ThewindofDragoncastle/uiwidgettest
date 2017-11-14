package com.example.uiwidgettest.winddragonnews.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.winddragonnews.HandleData.Data;
import com.example.uiwidgettest.winddragonnews.HandleData.DbData;
import com.example.uiwidgettest.winddragonnews.HandleData.News;
import com.example.uiwidgettest.winddragonnews.adapter.NewsFragmentAdapter;
import com.example.uiwidgettest.winddragonnews.databases.NewsSQLDatabasesHelper;
import com.example.uiwidgettest.winddragonnews.fragment.NewsFragment;
import com.example.uiwidgettest.winddragonnews.model.newscontent.DownloadNewsItem;
import com.example.uiwidgettest.winddragonnews.model.newsimage.ImageDownload;
import com.example.uiwidgettest.winddragonnews.presenter.ActivityDataSupport;
import com.example.uiwidgettest.winddragonnews.presenter.MedPresenter;
import com.example.uiwidgettest.winddragonnews.useinterface.ForFragmetAdapter;
import com.example.uiwidgettest.winddragonnews.view.ActivityListener;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPage extends AppCompatActivity implements ForFragmetAdapter.NewsFragmentToMainpage,ActivityListener{
private final String TAG=getClass().getName();
//权限请求码
private final int PERMISSION_WRITE=0;
//活动支持类
private ActivityDataSupport dataSupport;
//判断是否第一次
private boolean isFirst=true;
//中间变量
    MedPresenter presenter=null;
//    侧滑按钮
 @BindView(R.id.DrawerLayout)
    DrawerLayout layout;
//    标题栏 用于旋转按钮
@BindView(R.id.logintoolbar)
Toolbar toolbar;
//抽屉栏
    @BindView(R.id.Nv_view)
    NavigationView navigationView;

 @BindView(R.id.tablayout)
TabLayout tab;
// 分页器
@BindView(R.id.NewsviewPager)
    ViewPager viewPager;
//分页每一页的碎片
    private List<Fragment> fragments=new ArrayList<>();
//    碎片的适配器
    private NewsFragmentAdapter adapter;
//固定的数据源
    private List<DbData> dataList=new ArrayList<>();
    NewsSQLDatabasesHelper sqlDatabasesHelper;
    SQLiteDatabase wdatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.winddragonnews);
        ButterKnife.bind(this);
//        标题
        String[] tabs={
                "焦点","国际","历史","武侠","文化","常识","本地","科技","体育","智慧","文笔","奇人"
        };
        for (int i=0;i<tabs.length;i++) {
//            设置碎片 并且初始化
            tab.newTab().setText(tabs[i]);
            NewsFragment fragment=new NewsFragment();
//            设置接口
            fragment.setMcallback(this);
//            告知碎片当前所处的界面
            fragment.setPage(i);
            fragments.add(fragment);
        }
        adapter=new NewsFragmentAdapter(getSupportFragmentManager(),fragments,tabs);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
//        请求权限
        if(Build.VERSION.SDK_INT>=23) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                start();
            else {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},PERMISSION_WRITE);
            }
        }
        else
            start();
    }

    @Override
    public void ChangeFragmentCallback(Data data) {
//        跳转至新闻细节
        Intent intent=new Intent(this,Newsitem.class);
        intent.putExtra("url",data.getUrl());
        startActivity(intent);
    }

    @Override
    public int getPage() {
        return tab.getSelectedTabPosition();
    }

    @Override
    public void start() {
        initDatabases();
        initDrawerlayout();
        initMvp();
        //      初始化完成  从数据库中加载已经缓存的数据
        loadData();
        try {
            URL url= new URL("http://120.76.205.241:8000/news/qihoo?kw=%E7%99%BD&site=qq.com&apikey=ZxWxoeQ7NftZ3NxLvIZcRYsdgDIctpwFAyTDR8WVhHVeDytWJ96Q7LWYWqXmduQj");
//           请求一次
            presenter.executeNews(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initDatabases() {
        //        数据库初始化
        sqlDatabasesHelper=new NewsSQLDatabasesHelper(this,"",null,1);
        dataSupport=ActivityDataSupport.getInstance();
        dataSupport.setDatabases(sqlDatabasesHelper.getWritableDatabase());
    }

    @Override
    public void initMvp() {
//        下载新闻只设置一次
        DownloadNewsItem item=new DownloadNewsItem();
        ImageDownload download=new ImageDownload();
//        中间变量设置碎片次数（多次）
        for (Fragment fragment:fragments)
        {
//            向媒介层设置各个接口
            presenter = new MedPresenter();
            item.setPresenterInterface(presenter);
            download.setDataPresenter(presenter);
            NewsFragment fragment1=(NewsFragment)fragment;
            presenter.setNewsFragmentListener(fragment1);
            presenter.setDownloadListener(item);
            presenter.setActivityListener(this);
            presenter.setImageDownloadListener(download);
        }

    }

    @Override
    public void initDrawerlayout() {
//        打开侧滑
        layout.openDrawer(Gravity.START);
//        旋转开关
//这是带 Home旋转开关按钮
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, layout,
                toolbar,R.string.accountpassword,R.string.accountpassword);
//        setDrawerListener()过时了
        layout.addDrawerListener(toggle);
        //        设置监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId())
                {
                    case R.id.mysetting:
                        layout.closeDrawers();
                        changeFragment(0);
                        break;
                    case R.id.a12:
                        layout.closeDrawers();
                        changeFragment(1);
                        break;
                    case R.id.clear:
                        layout.closeDrawers();
                        Toast.makeText(getBaseContext(),"某某文件夹下文件已全部清空",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.spear:
                        layout.closeDrawers();
                        Toast.makeText(getBaseContext(),"节省流量开启",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.wantsay:
                        layout.closeDrawers();
                        changeFragment(2);
                        break;
                }
                return true;
            }
        });
        toggle.syncState();
    }

    @Override
    public void loadData() {
//        因为加载涉及适配器 所以数据源应该固定
//        清空
        dataList.clear();
        for (Fragment fragment : fragments) {
            NewsFragment fragment1 = (NewsFragment) fragment;
//                加载数据时首先查看数据库，所有数据都应该已经存放在数据库
            for(DbData data:dataSupport.queryDatalist("content"))
            {
                dataList.add(data);
            }
//            碎片加载数据
            fragment1.setDataList(dataList);
        }
    }

    @Override
    public void changeFragment(int i) {
////        切换碎片
//          NewsContent content=new NewsContent();
//          int index=tab.getSelectedTabPosition();
//          MyLog.d(TAG,"适配器切换碎片,index="+index);
//          fragments.remove(index);
//          fragments.add(index,content);
//          adapter.notifyDataSetChanged();
//          viewPager.setAdapter(adapter);
////          调整适配器后再选择位置
//        //          第一个参数表示当前Tab的位置，第二个参数是偏移值，从文档中看到该值的取值范围是0到1的一个半开区间，
//// 最后一个参数很好理解表示是否置移动后位置所对应的Tab为选中状态.
////          tab.setScrollPosition(0,index,true);
//          content.setTextview(Info);
//          tab.getTabAt(index).select();
//        开启碎片
        Intent intent=new Intent(this,SettingsActivity.class);
        intent.putExtra("fragment",i);
        startActivity(intent);
    }

    @Override
    public void ShowContentView(News news) {
//        MyLog.d(TAG,"数据：");
//        数据下传至碎片
//        这里要返回数次数据 但这些数据是一样的 我们实际不会是这样的
//        所以在这里下载图片仅一次
        if(isFirst) {
            dataSupport.SaveContentData(news.getData());
            for (Data data : news.getData()) {
                try {
//                    下载图片
                    if(data.getImageUrls()!=null)
                    presenter.executeImage(new URL(data.getImageUrls()[0]));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
            loadData();
            isFirst = false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode)
       {
           case PERMISSION_WRITE:
               if(grantResults.length>0&&grantResults[0]==PackageManager.PERMISSION_GRANTED)
                   start();
               else {
                   Toast.makeText(this,"拒绝权限无法使用本应用。",Toast.LENGTH_SHORT).show();
                   finish();
               }
               break;
       }
    }
}
