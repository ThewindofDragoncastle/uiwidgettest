package com.example.uiwidgettest.ui;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.uiwidgettest.R;

public class Main2Activity extends AppCompatActivity  {
    int rate=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Button b1=(Button)findViewById(R.id.b1);
        Button b2=(Button)findViewById(R.id.b2);
        final ProgressBar pb1=(ProgressBar)findViewById(R.id.pb1);
        final ProgressBar pb2=(ProgressBar)findViewById(R.id.pb2);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(pb2.getVisibility()==View.INVISIBLE||pb2.getVisibility()==View.GONE)
                pb2.setVisibility(View.VISIBLE);
                else
                pb2.setVisibility(View.GONE);

            }
        });
        Button b4=(Button)findViewById(R.id.b4);
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder di=new AlertDialog.Builder(Main2Activity.this);
                di.setTitle("你误点了重要东西");
                di.setMessage("如果你不明白你在干什么！请点击退出立即离开！");
                di.setCancelable(false);
                di.setPositiveButton("确定继续",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface id,int which)
                    {
                        Intent it=new Intent(Main2Activity.this,Main3Activity.class);
                        startActivity(it);
                    }
                });
                di.setNegativeButton("返回",new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface id,int which)
                    {}
                });
                di.show();
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rate=pb1.getProgress();
//                while (rate!=pb1.getMax())
//                {
//                    try {
//                        Thread.sleep(1000);
//                    }catch (InterruptedException e)
//                    {
//                        Log.d("界面三","中断错误");
//                    }
                    rate=rate+10;
                    pb1.setProgress(rate);
//                }
                if(rate==100) {
                    rate=0;
                    pb1.setProgress(rate);
                }
            }
        });
        Button b5=(Button)findViewById(R.id.b5);
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog pd=new ProgressDialog(Main2Activity.this);
                pd.setTitle("进不去的");
                pd.setMessage("正在加载...");
                pd.setCancelable(true);
                pd.show();
            }
        });
    }
}
