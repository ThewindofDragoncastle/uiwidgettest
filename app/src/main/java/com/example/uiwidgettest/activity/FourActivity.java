package com.example.uiwidgettest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.example.uiwidgettest.R;
public class FourActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_four);
        Button b1=(Button)findViewById(R.id.button_41);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FourActivity.this,FourActivity.class);
                startActivity(it);
            }
        });
        Button b2=(Button)findViewById(R.id.button_42);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(FourActivity.this,FirstActivity.class);
                startActivity(it);
            }
        });
    }
}
