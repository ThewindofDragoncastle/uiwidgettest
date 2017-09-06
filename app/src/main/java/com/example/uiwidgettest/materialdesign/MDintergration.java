package com.example.uiwidgettest.materialdesign;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.usebaidumap.BaiduMapIntergration;

public class MDintergration extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mdintergration);
        Button toolbar = (Button) findViewById(R.id.IntoToolbar);
        toolbar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.IntoToolbar:
                intent = new Intent(this, ToolbarTest.class);
                startActivity(intent);
                break;
        }
    }
}
