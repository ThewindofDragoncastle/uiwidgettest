package com.example.uiwidgettest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main5Activity extends AppCompatActivity {
    private List<Hero> al=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        setzerohero();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclyer1);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
//        GridLayoutManager gridLayoutManager=new GridLayoutManager();
        RecycleHeroAdapter recycleHeroAdapter=new RecycleHeroAdapter(al);
        recyclerView.setAdapter(recycleHeroAdapter);
    }
    public String Randname(String name)
    {
        Random random=new Random();
        StringBuffer sb=new StringBuffer(name+" ");
        int cx=random.nextInt(8);
        for(int i=0;i<cx;i++)
        sb.append(name+" ");
        return sb.toString();
    }
    public void setzerohero()
    {

        al.add(new Hero(R.drawable.wjjs2,Randname("无极剑圣")));
        al.add(new Hero(R.drawable.ltpx2,Randname("雷霆咆哮")));
        al.add(new Hero(R.drawable.zyzl2,Randname("赵云子龙")));
        al.add(new Hero(R.drawable.lbfx2,Randname("吕布奉先")));
        al.add(new Hero(R.drawable.kynls2,Randname("狂野女猎手")));
        al.add(new Hero(R.drawable.smtf2,Randname("沙漠屠夫")));
        al.add(new Hero(R.drawable.sjck2,Randname("时间刺客")));
        al.add(new Hero(R.drawable.tkmh2,Randname("铁凯冥魂")));
        al.add(new Hero(R.drawable.nr2,Randname("纳尔")));
        al.add(new Hero(R.drawable.sjlr2,Randname("赏金猎人")));
        al.add(new Hero(R.drawable.xkzy2,Randname("虚空恐惧")));
        al.add(new Hero(R.drawable.ntqz2,Randname("牛头酋长")));
        al.add(new Hero(R.drawable.zznh2,Randname("蜘蛛女皇")));


    }
}

