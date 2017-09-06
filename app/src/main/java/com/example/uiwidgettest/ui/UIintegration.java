package com.example.uiwidgettest.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class UIintegration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uiintegration);
        Button b1chat=(Button)findViewById(R.id.chat);
        b1chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIintegration.this,chatgui.class);
                startActivity(intent);
            }
        });
        Button brecycleui=(Button)findViewById(R.id.RecycleViewUI);
       brecycleui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIintegration.this,Main6Activity.class);
                startActivity(intent);
            }
        });
        Button bfallgesi=(Button)findViewById(R.id.fallgesi);
        bfallgesi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIintegration.this,Main5Activity.class);
                startActivity(intent);
            }
        });
        Button bListkongjian=(Button)findViewById(R.id.Listkongjian);
        bListkongjian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIintegration.this,Main4Activity.class);
                startActivity(intent);
            }
        });
        Button bjindutiaoxianshi=(Button)findViewById(R.id.jindutiaoxianshi);
        bjindutiaoxianshi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(UIintegration.this,Main2Activity.class);
                startActivity(intent);
            }
        });

    }
}
