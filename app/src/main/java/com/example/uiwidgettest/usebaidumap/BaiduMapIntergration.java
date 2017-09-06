package com.example.uiwidgettest.usebaidumap;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class BaiduMapIntergration extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baidu_map_intergration);
        Button IntoBaiduMapTextView=(Button)findViewById(R.id.IntoBaiduMapTextView);
        IntoBaiduMapTextView.setOnClickListener(this);
        Button CommentaryMap=(Button)findViewById(R.id.IntoCommentaryMap);
        CommentaryMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.IntoBaiduMapTextView:
                intent = new Intent(this, BaidumapTextView.class);
                startActivity(intent);
                break;
            case R.id.IntoCommentaryMap:
                intent = new Intent(this, CommentaryMap.class);
                startActivity(intent);
                break;
        }
    }
}
