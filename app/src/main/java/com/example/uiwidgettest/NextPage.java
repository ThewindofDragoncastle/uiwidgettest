package com.example.uiwidgettest;


import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.materialdesign.MDintergration;
import com.example.uiwidgettest.usebaidumap.BaiduMapIntergration;
import com.example.uiwidgettest.usebaidumap.BaidumapTextView;
import com.example.uiwidgettest.usebaidumap.CommentaryMap;

public class NextPage extends AppCompatActivity implements View.OnClickListener{

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
        }
    }
}
