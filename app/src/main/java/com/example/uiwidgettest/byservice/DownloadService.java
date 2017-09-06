package com.example.uiwidgettest.byservice;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.io.File;

public class DownloadService extends Service {
    private DownloadTask downloadTask;
    private DownloadListener downloadListener=new DownloadListener() {
        @Override
        public void onProgress(int progress) {
            getNotificationManager().notify(1,getNotification("下载中....",progress));
        }

        @Override
        public void onPause() {
            downloadTask=null;
            Toast.makeText(getBaseContext(),"下载暂停！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            downloadTask=null;
            stopForeground(true);
            Toast.makeText(getBaseContext(),"下载取消！",Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onSuccess() {
            downloadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载完成！",-1));
            Toast.makeText(getBaseContext(),"下载完成！",Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onFailed() {
            downloadTask=null;
            stopForeground(true);
            getNotificationManager().notify(1,getNotification("下载失败！",-1));
            Toast.makeText(getBaseContext(),"下载失败！",Toast.LENGTH_SHORT).show();
        }
    };
    public DownloadService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return downloadBinder1;
    }
    private NotificationManager getNotificationManager()
    {
        return (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
    }
    private Notification getNotification(String title,int progress)
    {
        Intent intent=new Intent(this,FullDownload.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,0);
        NotificationCompat.Builder builder=new NotificationCompat.Builder(this);
                builder.setContentTitle(title).setSmallIcon(R.mipmap.apppicture)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.apppicture))
                .setContentIntent(pendingIntent);

        if(progress>=0)
        {
            builder.setContentText("当前进度："+progress+"%");
            builder.setProgress(100,progress,false);
        }
        return builder.build();
    }
    private DownloadBinder1 downloadBinder1=new DownloadBinder1();
    class DownloadBinder1 extends Binder {
        String dataurl;
        public void startdownload(String url) {
            if (downloadTask == null) {
                dataurl=url;
                downloadTask = new DownloadTask(downloadListener);
                downloadTask.execute(url);
                startForeground(1, getNotification("下载中...", 0));
                Toast.makeText(getBaseContext(), "下载开始！", Toast.LENGTH_SHORT).show();
            }
        }

        public void pause() {
            if (downloadTask != null) {
                stopForeground(true);
                getNotificationManager().notify(1,getNotification("下载暂停！",-1));
                downloadTask.setPausedDownload();
            }
        }

        public void cancel() {
            if (downloadTask != null)
                downloadTask.setcancelDownload();
            else {
                 String filename=dataurl.substring(dataurl.lastIndexOf("/"));
                File file=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+
                        File.separator+"download"+File.separator+filename);
                if(file.exists())
                {
                    file.delete();
                }
                getNotificationManager().cancel(1);
                stopForeground(true);
                Toast.makeText(getBaseContext(), "下载取消！", Toast.LENGTH_SHORT).show();
            }

        }
    }
}
