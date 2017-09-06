package com.example.uiwidgettest.Broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class ForceOffActivity1 extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_off1);
        Button button=(Button)findViewById(R.id.forcetooffline);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.UIWidgetTest.FORCEOFFLINE");
                sendBroadcast(intent);
            }
        });
        Button button1=(Button)findViewById(R.id.tosecondactivity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
    Intent intent=new Intent(ForceOffActivity1.this,ForceOffLineActivity2.class);
                startActivity(intent);
            }
        });
    }
}
