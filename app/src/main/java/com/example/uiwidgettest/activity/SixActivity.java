package com.example.uiwidgettest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.example.uiwidgettest.R;
public class SixActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_six);
        Log.d("第六个活动","得到的id是："+getTaskId());
        Button b1=(Button)findViewById(R.id.button_61);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(SixActivity.this,SixActivity.class);
                startActivity(it);
            }
        });
        Button b2=(Button)findViewById(R.id.button_62);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(SixActivity.this,FirstActivity.class);
                startActivity(it);
            }
        });
        Button b3=(Button)findViewById(R.id.button_63);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(SixActivity.this,thirdActivity.class);
                startActivity(it);
            }
        });
        Button b4=(Button)findViewById(R.id.button_64);
        b4.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View v)
            {
                ActivityCollector.clearall();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        });
    }
}
