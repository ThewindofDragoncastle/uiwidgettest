package com.example.uiwidgettest.materialdesign;

import android.Manifest;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.Loader;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Adapter;
import android.widget.HeaderViewListAdapter;
import android.widget.Toast;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ToolbarTest extends AppCompatActivity {
    private DrawerLayout layout;
    private int lastX;
    private int lastY;
    private SwipeRefreshLayout refreshLayout;
    private HeroAdapter adapter;
    private RecyclerView recyclerView;
    private final int IMAGEUSEURL=1;
    private final String URL="http://39.108.123.220";
    List<Hero> heroArrayList=new ArrayList<>();
    Hero[] heros= {
            new Hero(new URL( URL+"/picture/无极剑圣.jpg"), "无极剑圣"),
            new Hero(new URL(URL+"/picture/雷霆咆哮.jpg"), "雷霆咆哮"),
            new Hero(new URL(URL+"/picture/赵云子龙.jpg"), "赵云子龙"),
            new Hero(new URL(URL+"/picture/吕布奉先.jpg"), "吕布奉先"),
            new Hero(new URL(URL+"/picture/狂野女猎手.jpg"),"狂野女猎手"),
            new Hero(new URL(URL+"/picture/荒漠屠夫.jpg"), "荒漠屠夫"),
            new Hero(new URL(URL+"/picture/时间刺客.jpg"), "时间刺客"),
            new Hero(new URL(URL+"/picture/铁铠冥魂.jpg"), "铁铠冥魂"),
            new Hero(new URL(URL+"/picture/纳尔.jpg"), "纳尔"),
            new Hero(new URL(URL+"/picture/厄运小姐.jpg"), "赏金猎人"),
            new Hero(new URL(URL+"/picture/虚空恐惧.jpg"), "虚空恐惧"),
            new Hero(new URL(URL+"/picture/牛头酋长.jpg"), "牛头酋长"),
            new Hero(new URL(URL+"/picture/蜘蛛女皇.jpg"), "蜘蛛女皇")
    };

    public ToolbarTest() throws MalformedURLException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolbar_test);
        Toolbar toolbar=(Toolbar)findViewById(R.id.Toolbar);
        setSupportActionBar(toolbar);
        layout=(DrawerLayout)findViewById(R.id.DrawerLayout233);
        NavigationView navigationView=(NavigationView)findViewById(R.id.Nv_view);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar!=null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
//        actionBar.setHomeButtonEnabled(true);
//        actionBar.setHomeAsUpIndicator(R.);
        refreshLayout=(SwipeRefreshLayout)findViewById(R.id.MDrefresh);
        refreshLayout.setColorSchemeResources(R.color.plum);
        layout.openDrawer(Gravity.START);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshHero();
            }
        });
        navigationView.setCheckedItem(R.id.nv_task);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                layout.closeDrawers();
                return true;
            }
        });
        if(ContextCompat.checkSelfPermission(MyApplication.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
        else
        LoadRecyclerView();
    }
 private void refreshHero()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initHero();
                        adapter.notifyDataSetChanged();
                        refreshLayout.setRefreshing(false);
                    }
                });
            }
        }).start();
    }
private void LoadRecyclerView()
{
    initHero();
    recyclerView=(RecyclerView)findViewById(R.id.MDrecycleview);
    GridLayoutManager gridLayoutManager=new GridLayoutManager(this,3);
    recyclerView.setLayoutManager(gridLayoutManager);
   adapter =new HeroAdapter(heroArrayList);
    adapter.setActivity(this);
    recyclerView.setAdapter(adapter);
    FloatingActionButton actionButton=(FloatingActionButton)findViewById(R.id.floatactionbutton);
    actionButton.setOnTouchListener(new View.OnTouchListener() {
        boolean Ismoved=false;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            int x=(int)event.getX();
            int y=(int)event.getY();

            SharedPreferences.Editor sharedPreferences= PreferenceManager.getDefaultSharedPreferences(getBaseContext()).edit();

            switch (event.getAction())
            {
                case MotionEvent.ACTION_DOWN:
                    lastX=x;
                    lastY=y;
                   break;
                case MotionEvent.ACTION_UP:
                    MyLog.d("ToolbarTest:","Ismoved:"+Ismoved);
                    if(Ismoved==true) {//判断移动还是按下按钮
                        Ismoved=false;
                        return true;
                    }
                    else
                        return false;

                case MotionEvent.ACTION_MOVE:
                      Ismoved=true;
                      int offsetX=x-lastX;
                      int offsetY=y-lastY;
                     v.layout(v.getLeft()+offsetX,v.getTop()+offsetY,v.getRight()+offsetX,v.getBottom()+offsetY);
                    break;
            }
            sharedPreferences.putInt("HeroAdapterX",lastX);
            sharedPreferences.putInt("HeroAdapterY",lastY);
            sharedPreferences.apply();
            return true;
        }
    });
    actionButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            MyLog.d("Toolbar:","悬浮按钮监听执行");
          recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),2));
            adapter.notifyDataSetChanged();
            Snackbar.make(v,"分成两列网格成功",Snackbar.LENGTH_SHORT).setAction("恢复",
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            recyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(),3));
                        }
                    }).show();
        }
    });
}
private void initHero()
{
    Random random=new Random();
    heroArrayList.clear();
    Hero inter= null;
    try {
        inter = new Hero(new URL(URL+"/哈哈.png"),"来自电脑的图片");
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    //如果image为1 使用网络图片
    try {
        inter.setUrl(new URL(URL+"/哈哈.png"));
    } catch (MalformedURLException e) {
        e.printStackTrace();
    }
    heroArrayList.add(inter);
    for (int i=0;i<50;i++)
    {
        heroArrayList.add(heros[random.nextInt(13)]);
    }
}
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
               layout.openDrawer(GravityCompat.START); ;break;

        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==1)
        {
            if(grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults.length>0)
            {
                LoadRecyclerView();
            }
            else
            {
                Toast.makeText(getBaseContext(),"拒绝权限本应用无法使用。",Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}
