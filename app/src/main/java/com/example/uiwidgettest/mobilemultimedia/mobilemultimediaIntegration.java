package com.example.uiwidgettest.mobilemultimedia;

import android.app.*;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.R;

public class mobilemultimediaIntegration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobilemultimedia_integration);
        Button button=(Button)findViewById(R.id.IntoNotification);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mobilemultimediaIntegration.this, Notification_1.class);
                startActivity(intent);
            }
        });
        Button IntoTakePhoto=(Button)findViewById(R.id.IntoTakePhoto);
        IntoTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mobilemultimediaIntegration.this, TakePhotograph.class);
                startActivity(intent);
            }
        });
        Button playMusic=(Button)findViewById(R.id.playMusic);
        playMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mobilemultimediaIntegration.this, PlayMusic.class);
                startActivity(intent);
            }
        });
        Button playVideo=(Button)findViewById(R.id.playVideo);
        playVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mobilemultimediaIntegration.this, PlayVideo.class);
                startActivity(intent);
            }
        });
    }
}
