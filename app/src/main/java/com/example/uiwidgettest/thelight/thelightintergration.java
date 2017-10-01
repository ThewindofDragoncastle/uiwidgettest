package com.example.uiwidgettest.thelight;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.thelight.tab.TabActivity;

public class thelightintergration extends AppCompatActivity implements View.OnClickListener{

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_thelightintergration);
            Button SecondPage=(Button)findViewById(R.id.Intoprocard);
            SecondPage.setOnClickListener(this);
            Button icl=(Button)findViewById(R.id.Intocalculator);
            icl.setOnClickListener(this);
            Button tabs=(Button)findViewById(R.id.IntoTabs);
            tabs.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent;
            switch (v.getId())
            {
                case R.id.Intoprocard:intent=new Intent(this, cardview.class);
                    startActivity(intent);
                    break;
                case R.id.Intocalculator:intent=new Intent(this, Calculator.class);
                    startActivity(intent);
                    break;
                case R.id.IntoTabs:intent=new Intent(this, TabActivity.class);
                    startActivity(intent);
                    break;
            }
        }
    }

