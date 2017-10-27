package com.example.uiwidgettest.myreview.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.media.TimedText;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.DisplayData;
import com.example.uiwidgettest.myreview.media.SongorMovie;
import com.example.uiwidgettest.thelight.tab.TabActivity;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//播放音乐
public class PlayService extends IntentService implements MusicStatusListener,MediaPlayer.OnCompletionListener,MediaPlayer.OnErrorListener{
    private String TAG="服务：";
    private int songsindex=-1;
    private MediaPlayer player;
    private List<SongorMovie> songorMovies;
    private RemoteViews views;
    private SongorMovie songorMovie;
    NotificationCompat.Builder builder;
    private MusicStatusListener statusListener=this;
    private static String Sys="同步块";
    public PlayService() {
        super("后台播放音乐");
    }
    private MusicBinder binder=new MusicBinder();
    private TimerTask task=new TimerTask() {
        @Override
        public void run() {
            sendNotifycation("",(int)(player.getCurrentPosition()/(double)player.getDuration()));
        }
    };
    private  Timer timer;
    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        MyLog.d(TAG,"服务开始");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        //处理播放任务
        player=new MediaPlayer();
            try {
                while (true)
                {
                    if(songorMovie==null)
                    synchronized (Sys) {
                        MyLog.d(TAG, "播放中线程进入等待。");
                        Sys.wait();
                    }
                    MyLog.d(TAG,"播放线程进入启动。");
                    player.setDataSource(songorMovie.getPath());
                    player.prepare();
                    player.setOnCompletionListener(this);
                    player.start();
                    player.setOnErrorListener(this);
                    timer=new Timer();
                    task=new TimerTask() {
                        @Override
                        public void run() {
                            double f=player.getCurrentPosition()/(double)player.getDuration();
                            if(f>0.99)
                                forward();
                            sendNotifycation(songorMovie.getName(),(int)(f*100));
                        }
                    };
                    timer.schedule(task,0,500);
                    //播放开始每隔0.5秒监视一次歌曲状态
                    sendNotifycation(songorMovie.getName(),1);
                    synchronized (Sys) {
                        MyLog.d(TAG, "播放中线程进入等待。");
                        Sys.wait();
                    }
                    MyLog.d(TAG, "等待被唤醒更新进度取消");
                    if(player.isPlaying())
                        player.stop();
                    player.reset();
                    timer.cancel();
                    task.cancel();
                }
                } catch (IOException e) {
                    MyLog.d(TAG,"文件找不到");
                }
            catch (InterruptedException e) {
                MyLog.d(TAG,"中断异常。");
            }

    }

    @Override
    public void onDestroy() {
        MyLog.d(TAG,"服务被摧毁");
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
//对广播接收。
    @Override
    public void pause() {
        MyLog.d(TAG,"回调成功，暂停播放");
        if(player!=null)
        if(player.isPlaying())
            player.pause();
        else
            player.start();
            changeButton();
    }

    @Override
    public void back() {
        if(songsindex!=-1)
        {
            if(songsindex>=1)
                songsindex--;
           else if(songsindex==0)
                songsindex=songorMovies.size()-1;
            songorMovie = songorMovies.get(songsindex);
            synchronized (Sys)
            {
                Sys.notify();
            }
        }
    }

    @Override
    public void forward() {
        //如果不等于初始值-1 说明有歌曲列表
//        当当前指数在列表的末位时，向后切歌 如果已经到了最后那么就转到第一个
        if(songsindex!=-1)
                {
        if(songsindex<songorMovies.size()-1)
            songsindex++;
        else if(songsindex==songorMovies.size()-1&&songsindex!=0)
            songsindex = 0;
        songorMovie = songorMovies.get(songsindex);
        synchronized (Sys)
        {
            Sys.notify();
        }
    }

    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        //完成回调
        MyLog.d(TAG,"完成播放");
//        forward();
    }
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        sendNotifycation("出错了！！",1);
        stopSelf();
        return true;
    }


    public class MusicBinder extends Binder
    {
        public void setArray(List<SongorMovie> songorMovie)
        {
            songorMovies=songorMovie;
        }
        public void startPlay(SongorMovie movie)
        {
            songorMovie=movie;
            if(songorMovies.size()!=0)
                songsindex=songorMovies.indexOf(songorMovie);
            synchronized(Sys) {
                Sys.notify();
            }
            MyLog.d(TAG,"开始播放");
        }
        public MusicStatusListener getMusicStatusListener()
        {
            return statusListener;
        }
    }
    private void sendNotifycation(String content,int progress)
    {
        if(builder==null) {
            Intent intent1 = new Intent(this, DisplayData.class);
            //启动列表界面
            intent1.putExtra("display",false);
            intent1.putExtra("moviesong",true);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent1, 0);
            builder = new NotificationCompat.Builder(this);
            builder.setWhen(System.currentTimeMillis())
                    .setAutoCancel(false)
                    .setSmallIcon(R.mipmap.apppicture);
            //来源什么地方
            views = new RemoteViews(getPackageName(), R.layout.notifycation);
            views.setOnClickPendingIntent(R.id.pause, PendingIntent.getBroadcast(this, 0,
                    new Intent("com.example.uiwidgettest.PAUSEMUSIC"), 0));
            views.setOnClickPendingIntent(R.id.back, PendingIntent.getBroadcast(this,1,
                    new Intent("com.example.uiwidgettest.BACKMUSIC"), 0));
            views.setOnClickPendingIntent(R.id.Forward, PendingIntent.getBroadcast(this, 2,
                    new Intent("com.example.uiwidgettest.FORWARDMUSIC"), 0));
            views.setImageViewBitmap(R.id.displayimage
                    , BitmapFactory.decodeResource(getResources(), R.mipmap.apppicture));
            views.setImageViewResource(R.id.pause,
                    R.drawable.pause);
            views.setImageViewResource(R.id.Forward, R.drawable.next);
            views.setImageViewResource(R.id.back, R.drawable.last);
            builder.setContentIntent(pendingIntent);
        }
        views.setProgressBar(R.id.progressbar, 100, progress, false);
        if (progress%5==0) {
            if(songsindex<songorMovies.size()-1)
                views.setTextViewText(R.id.displaytext, "下一首：" + songorMovies.get(songsindex + 1).getName());
            else if(songsindex==songorMovies.size()-1&&songsindex!=0)
                views.setTextViewText(R.id.displaytext, "下一首：" + songorMovies.get(0).getName());
        }
            else
        views.setTextViewText(R.id.displaytext,content);
        builder.setContent(views);
        Notification notification=builder.build();
        startForeground(11,notification);
    }
    private void changeButton()
    {
        if (player != null)
            if (player.isPlaying())
                views.setImageViewResource(R.id.pause,
                        R.drawable.pause);
            else
                views.setImageViewResource(R.id.pause, R.drawable.play);
        builder.setContent(views);
        Notification notification=builder.build();
        startForeground(11,notification);
    }
}
