package com.example.uiwidgettest.ui;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

public class Main6Activity extends AppCompatActivity {
    private List<Hero> al=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main6);
        setzerohero();
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclyer1);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        //当前布局
        linearLayoutManager.setOrientation(linearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        RecycleHeroAdapter recycleHeroAdapter=new RecycleHeroAdapter(al);
        recyclerView.setAdapter(recycleHeroAdapter);
    }
    public void setzerohero()
    {

        al.add(new Hero(R.drawable.wjjs2,"无极剑圣"));
        al.add(new Hero(R.drawable.ltpx2,"雷霆咆哮"));
        al.add(new Hero(R.drawable.zyzl2,"赵云子龙"));
        al.add(new Hero(R.drawable.lbfx2,"吕布奉先"));
        al.add(new Hero(R.drawable.kynls2,"狂野女猎手"));
        al.add(new Hero(R.drawable.smtf2,"沙漠屠夫"));
        al.add(new Hero(R.drawable.sjck2,"时间刺客"));
        al.add(new Hero(R.drawable.tkmh2,"铁凯冥魂"));
        al.add(new Hero(R.drawable.nr2,"纳尔"));
        al.add(new Hero(R.drawable.sjlr2,"赏金猎人"));
        al.add(new Hero(R.drawable.xkzy2,"虚空恐惧"));
        al.add(new Hero(R.drawable.ntqz2,"牛头酋长"));
        al.add(new Hero(R.drawable.zznh2,"蜘蛛女皇"));


    }
}
