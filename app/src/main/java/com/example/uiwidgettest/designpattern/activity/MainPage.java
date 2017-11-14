package com.example.uiwidgettest.designpattern.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.ButtonAndText;
import com.example.uiwidgettest.myreview.ButtonRecycleAdapter;
import com.example.uiwidgettest.myreview.DisplayData;
import com.example.uiwidgettest.myreview.OnClickListener;
import com.example.uiwidgettest.myreview.Review;
import com.example.uiwidgettest.myreview.broadcast.SendNotifaciation;
import com.example.uiwidgettest.myreview.contentprovide.DispalyCP;
import com.example.uiwidgettest.myreview.contentprovide.HeroDatabase;
import com.example.uiwidgettest.myreview.contentprovide.SQLhelper;
import com.example.uiwidgettest.myreview.json.Hero;
import com.example.uiwidgettest.myreview.json.JsontoString;
import com.example.uiwidgettest.myreview.json.StringtoJson;
import com.example.uiwidgettest.myreview.mediaplayer.view.activity.BufferMediaplayer;
import com.example.uiwidgettest.myreview.service.PlayService;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainPage extends AppCompatActivity implements OnClickListener {
    // 碎片参数
    private final int SINGLETON=0;
    private final int SIMPLEFACTORY=1;
    private final int FACTORYMETHOD=2;
    private final int BUIILDER_MODE=3;
    @BindView(R.id.buttonrecyclerview)
    RecyclerView recyclerView;
    private ButtonRecycleAdapter recycleAdapter;
    //放置按钮以及文本
    private List<ButtonAndText> buttonAndTexts=new ArrayList<>();
    private int i=0;
//    常量列表
    private final String TAG=getClass().getName();
    private final String DESIGNPATTER_SINGLETON="单例模式";
    private final String DESIGNPATTER_SIMPLEFACTORY="简单工厂模式";
    private final String DESIGNPATTER_FACTORYMETHOD="工厂方法模式";
    private final String DESIGNPATTER_BUILDERMODE="建造者模式";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }
    private void initView()
    {
        setContentView(R.layout.activity_review);
        ButterKnife.bind(this);
        initButtonAndTexts();
        recycleAdapter=new ButtonRecycleAdapter(this,this,buttonAndTexts);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    private void initButtonAndTexts()
    {
        addname(DESIGNPATTER_SINGLETON);
        addname(DESIGNPATTER_SIMPLEFACTORY);
        addname(DESIGNPATTER_FACTORYMETHOD);
        addname(DESIGNPATTER_BUILDERMODE);
    }
    private void addname(String introduce)
    {
        ButtonAndText text=new ButtonAndText(i);
        i++;
        text.setButtonName("任意门");
        text.setText(introduce);
        buttonAndTexts.add(text);
    }

    @Override
    public void OnClick(View v, int postion) {
        Intent intent;
        JSONArray array;
        switch (buttonAndTexts.get(postion).getCODE())
        {
            case  SINGLETON:
                intent=new Intent(this,DesignDisplayData.class);
                intent.putExtra("FragmentParams",SINGLETON);
                startActivity(intent);
                break;
            case  SIMPLEFACTORY:
                intent=new Intent(this,DesignDisplayData.class);
                intent.putExtra("FragmentParams",SIMPLEFACTORY);
                startActivity(intent);
                break;
            case FACTORYMETHOD:
                intent=new Intent(this,DesignDisplayData.class);
                intent.putExtra("FragmentParams",FACTORYMETHOD);
                startActivity(intent);
                break;
            case  BUIILDER_MODE:
                intent=new Intent(this,DesignDisplayData.class);
                intent.putExtra("FragmentParams",BUIILDER_MODE);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OnLongClick(View v, int postion) {

    }
}
