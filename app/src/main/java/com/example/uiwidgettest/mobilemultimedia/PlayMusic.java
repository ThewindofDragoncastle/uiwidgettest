package com.example.uiwidgettest.mobilemultimedia;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class PlayMusic extends AppCompatActivity implements View.OnClickListener{
    private Timer timer;
    private TimerTask timerTask;
    private ProgressBar progressBar;
    private MediaPlayer mediaPlayer=new MediaPlayer();
    MediaController mediaController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);
        //补充媒体控制器
//        mediaController=(MediaController)findViewById(R.id.media_control);
        progressBar=(ProgressBar)findViewById(R.id.ProBar);
        progressBar.setOnClickListener(this);
        Button play=(Button)findViewById(R.id.Play);
        play.setOnClickListener(this);
        Button Forward=(Button)findViewById(R.id.Forward);
        Forward.setOnClickListener(this);
        Button Backward=(Button)findViewById(R.id.Backward);
        Backward.setOnClickListener(this);
        Button Restart=(Button)findViewById(R.id.Restart);
        Restart.setOnClickListener(this);
        if(ContextCompat.checkSelfPermission(PlayMusic.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)
        {
            Log.d("PlayMusic:","权限检验通过。");
            initMusic();
        }
        else
            ActivityCompat.requestPermissions(PlayMusic.this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
    }
    private void initMusic()
    {
        File file1=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+File.separator+"music");
        Log.d("PlayMusic:","文件路径为："+Environment.getExternalStorageDirectory());
        File file=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+File.separator+"music","刚好遇见你.mp3");
        if(!file1.exists())
            file1.mkdirs();
        if(!file.exists())
        {
                Toast.makeText(getBaseContext(),"目录下没有该音乐",Toast.LENGTH_SHORT).show();
                finish();
                Log.d("PlayMusic:","音乐文件夹创建成功！路径："+file.getPath());
                //不存在则创建
        }
        else
            Log.d("PlayMusic:","音乐文件存在。");
        try {
            Log.d("PlayMusic:",file.getPath());
            mediaPlayer.setDataSource(file.getPath());
            mediaPlayer.prepare();
            progressBar.setMax(mediaPlayer.getDuration());
            timerTask=new TimerTask() {
                @Override
                public void run() {
                        int jindu = mediaPlayer.getCurrentPosition();
                        progressBar.setProgress(jindu);
                }
            };
            timer=new Timer();
            timer.schedule(timerTask,1000,1000);
        }catch (IOException e)
        {
            Log.d("PlayMusic:","音乐播放路径出错！");
        }
        catch (NullPointerException e)
        {
            Log.d("PlayMusic:","空指针异常。");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:if(PackageManager.PERMISSION_GRANTED==grantResults[0]&&grantResults.length>0)
                initMusic();
                else
            {
                Toast.makeText(PlayMusic.this, "你拒绝了访问SD卡权限\n本程序无法使用", Toast.LENGTH_SHORT).show();
                finish();
            }
                default:
        }
    }
    public void onClick(View view)
    {
        int cc;
        switch (view.getId())
        {
            case R.id.Play:
                progressBar.setProgress(mediaPlayer.getCurrentPosition());
                if(!mediaPlayer.isPlaying())
                    mediaPlayer.start();
                else
                    mediaPlayer.pause();
                break;
            case R.id.Forward:
                cc=mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(cc+3000);
                break;
            case R.id.Backward:
                cc=mediaPlayer.getCurrentPosition();
                mediaPlayer.seekTo(cc-3000);
                break;
            case R.id.ProBar:
                Toast.makeText(getBaseContext(),"不要调戏进度条",Toast.LENGTH_SHORT).show();
                break;
            case R.id.Restart:
                if(mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.seekTo(0);
                }
                else
                    mediaPlayer.seekTo(0);
                mediaPlayer.start();
                break;
        }
        progressBar.setProgress(mediaPlayer.getCurrentPosition());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mediaPlayer!=null)
        {
            mediaPlayer.stop();
            mediaPlayer.release();
            timerTask.cancel();
        }
    }
}
