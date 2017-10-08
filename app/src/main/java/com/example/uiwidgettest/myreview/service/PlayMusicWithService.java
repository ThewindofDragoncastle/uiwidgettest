package com.example.uiwidgettest.myreview.service;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.ConnectionService;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

public class PlayMusicWithService extends AppCompatActivity implements View.OnClickListener,BinderListener{
    private Button play;
    private final String TAG="PlayMusicWithService:";
    private PlayService.MusicBinder binder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.playsongs);
        play=(Button)findViewById(R.id.play);
        play.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.play:
                if(binder!=null)
//                  binder.startPlay(getIntent().getStringExtra("path"));
//                else
                    Toast.makeText(this,"播放器正在准备中",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void Binder(PlayService.MusicBinder binder) {
        this.binder=binder;
        MyLog.d(TAG,"连接成功");
    }

    @Override
    protected void onDestroy() {
        unbindService(GetConnectionService.conn);
        super.onDestroy();
    }
}
