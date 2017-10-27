package com.example.uiwidgettest;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.framework.eventbus.activity.MyEventBus;
import com.example.uiwidgettest.framework.mvp.activity.ViewTaobaoIP;
import com.example.uiwidgettest.framework.rxjava.activity.RxJava;
import com.example.uiwidgettest.mvpupdatechat.view.activity.MainPage;
import com.example.uiwidgettest.myreview.Review;
import com.example.uiwidgettest.thelight.thelightintergration;
import com.example.uiwidgettest.materialdesign.MDintergration;
import com.example.uiwidgettest.usebaidumap.BaiduMapIntergration;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class NextPage extends BaseActivity implements View.OnClickListener{
@OnClick(R.id.IntotheMVP)
public void StartActivity1()
{
    Intent intent=new Intent(this,ViewTaobaoIP.class);
    startActivity(intent);
}
@OnClick(R.id.IntotheUpdataChat)
public void StartActivity2()
{
    Intent intent=new Intent(this,MainPage.class);
    startActivity(intent);
}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_page);
        Button SecondPage=(Button)findViewById(R.id.SecondPage);
        SecondPage.setOnClickListener(this);
        Button IntoBaiduMap=(Button)findViewById(R.id.IntoBaiduMap);
        IntoBaiduMap.setOnClickListener(this);
        Button IntoMD=(Button)findViewById(R.id.IntoMD);
        IntoMD.setOnClickListener(this);
        Button IntotheLight=(Button)findViewById(R.id.IntotheLight);
        IntotheLight.setOnClickListener(this);
        Button IntoReview=(Button)findViewById(R.id.IntoReview);
        IntoReview.setOnClickListener(this);
        Button IntoFrameWork=(Button)findViewById(R.id.IntotheFramework);
        IntoFrameWork.setOnClickListener(this);
        Button IntoEventbus=(Button)findViewById(R.id.IntotheEventbus);
        IntoEventbus.setOnClickListener(this);
        ButterKnife.bind(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.SecondPage:finish();break;
            case R.id.IntoBaiduMap:intent=new Intent(this, BaiduMapIntergration.class);
                startActivity(intent);
                break;
            case R.id.IntoMD:intent=new Intent(this, MDintergration.class);
                startActivity(intent);
                break;
            case R.id.IntotheLight:intent=new Intent(this, thelightintergration.class);
                startActivity(intent);
                break;
            case R.id.IntoReview:intent=new Intent(this, Review.class);
                startActivity(intent);
                break;
            case  R.id.IntotheFramework:intent=new Intent(this,RxJava.class);
                startActivity(intent);
                break;
            case  R.id.IntotheEventbus:intent=new Intent(this,MyEventBus.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        ClearAll();
        super.onDestroy();
    }

}
