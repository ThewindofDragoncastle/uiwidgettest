package com.example.uiwidgettest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.uiwidgettest.R;
public class FiveActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
         Button b1=(Button)findViewById(R.id.button_51);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FiveActivity.this,FiveActivity.class);
                startActivity(it);
            }
        });
        Button b2=(Button)findViewById(R.id.button_52);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FiveActivity.this,FirstActivity.class);
                startActivity(it);
            }
        });
    }
}
