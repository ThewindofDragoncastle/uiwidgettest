package com.example.uiwidgettest.byservice;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class ServiceIntergration extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_intergration);
        Button IntoStartorStopServe=(Button)findViewById(R.id.IntoStartorStopServe);
        IntoStartorStopServe.setOnClickListener(this);
        Button IntoDownload=(Button)findViewById(R.id.IntoDownload);
        IntoDownload.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId())
        {
            case R.id.IntoStartorStopServe:
                intent=new Intent(ServiceIntergration.this,StartServiceAndStop.class);
                startActivity(intent);
                break;
            case R.id.IntoDownload:
                intent=new Intent(ServiceIntergration.this,FullDownload.class);
                startActivity(intent);
                break;
        }
    }
}
