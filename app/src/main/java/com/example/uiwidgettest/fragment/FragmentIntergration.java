package com.example.uiwidgettest.fragment;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.news.MainxxActivity;

public class FragmentIntergration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_intergration);
       Button bfragment1=(Button)findViewById(R.id.fragment1);
        bfragment1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FragmentIntergration.this,DIYfragment.class);
                startActivity(intent);
            }
        });
        Button bNews=(Button)findViewById(R.id.IntoNews);
        bNews.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FragmentIntergration.this,MainxxActivity.class);
                startActivity(intent);
            }
        });
    }
}
