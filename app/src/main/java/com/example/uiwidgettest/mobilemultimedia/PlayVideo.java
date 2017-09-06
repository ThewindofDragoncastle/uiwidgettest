package com.example.uiwidgettest.mobilemultimedia;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.uiwidgettest.R;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

public class PlayVideo extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar progressBar;
    private VideoView videoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);
        progressBar=(ProgressBar)findViewById(R.id.ProBar1);
        progressBar.setOnClickListener(this);
        Button play=(Button)findViewById(R.id.Play1);
        play.setOnClickListener(this);
        Button Forward=(Button)findViewById(R.id.Forward1);
        Forward.setOnClickListener(this);
        Button Backward=(Button)findViewById(R.id.Backward1);
        Backward.setOnClickListener(this);
        Button Restart=(Button)findViewById(R.id.Restart1);
        Restart.setOnClickListener(this);
        videoView=(VideoView)findViewById(R.id.VideoView);
        if(ContextCompat.checkSelfPermission(PlayVideo.this,Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
        {
            initvideo();
        }
        else
            ActivityCompat.requestPermissions(PlayVideo.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }
private void initvideo()
{
    File fileMKDIR=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+File.separator+"video");
    File videoFile=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+File.separator+"video","钢之炼金术师.mp4");
        if(!fileMKDIR.exists())
        fileMKDIR.mkdirs();
        if (!videoFile.exists()) {
            Toast.makeText(getBaseContext(),"目录下没有该视频",Toast.LENGTH_SHORT).show();
            finish();
        }
    videoView.setVideoPath(videoFile.getPath());
    progressBar.setMax(videoView.getDuration());
    Timer timer=new Timer();
    TimerTask timerTask=new TimerTask() {
        @Override
        public void run() {
            progressBar.setProgress(videoView.getCurrentPosition());
        }
    };
    timer.schedule(timerTask,1000,1000);
    Log.d("PlayVideo:","视频路径初始化成功");
}

    @Override
    public void onClick(View view) {
        int cc=videoView.getCurrentPosition();
        Log.d("PlayVideo:","已进行时长："+videoView.getCurrentPosition()+"\n总时长："+videoView.getDuration());
        switch (view.getId())
        {
            case R.id.Play1:
                if(videoView.isPlaying())
                    videoView.pause();
                else
                    videoView.start();
                break;
            case R.id.Forward1:
                videoView.pause();
                videoView.seekTo(cc+5000);
                videoView.start();
                break;
            case R.id.Backward1:
                videoView.pause();
                videoView.seekTo(cc-5000);
                videoView.start();break;
            case R.id.Restart1:
                videoView.seekTo(0);
                break;
        }
        Log.d("PlayVideo：","缺失关键帧");
        Toast.makeText(getBaseContext(),"缺失关键帧。",Toast.LENGTH_SHORT).show();
        progressBar.setProgress(videoView.getCurrentPosition());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults.length>0)
                {
                    initvideo();
                }
                else
                    Toast.makeText(getBaseContext(),"你拒绝了权限。",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(videoView!=null)
        {
            videoView.suspend();
        }
    }
}
