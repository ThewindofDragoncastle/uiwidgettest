package com.example.uiwidgettest.byservice;

import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 40774 on 2017/8/17.
 */

public class DownloadTask extends AsyncTask <String,Integer,Integer>{
   public static final int SUCESS=1;
    public static final int FAILED=2;
    public static final int PAUSED=3;
    public static final int CANCELED=4;
    private int lastprogress;
    private static boolean IsCancled;
    private static boolean Ispaused;
    private DownloadListener downloadListener;
    public DownloadTask(DownloadListener downloadListener)
    {
        this.downloadListener=downloadListener;
    }
    @Override
    protected void onPreExecute() {
        //预先执行
        super.onPreExecute();
    }

    @Override
    protected Integer doInBackground(String... params) {
        //        子线程执行耗时操作
        IsCancled=false;
        Ispaused=false;
        RandomAccessFile accessFile=null;
        InputStream inputStream=null;
        long downloadlength=0;
        String downurl=params[0];
        String filename=downurl.substring(downurl.lastIndexOf("/"));
        filename=filename.split("/")[1];
        File file=new File(Environment.getExternalStorageDirectory() +File.separator+"开发专用文件夹"+
                File.separator+"download",filename);
        File file1=new File(Environment.getExternalStorageDirectory() +File.separator+"开发专用文件夹"+
                File.separator+"download");
        //filename=/文件名
//        +File.separator+"开发专用文件夹"+
//                File.separator+"download"
//        文件夹：/storage/emulated/0/开发专用文件夹/download/mysql-5.6.17-winx64.zip
        Log.d("DownloadTask:","文件名："+filename+"\n文件夹："+file);
        try {
            if(!file1.exists())
                file1.mkdirs();
            if (file.exists()) {
//                file.mkdirs();
 downloadlength=file.length();
            }


            Log.d("DownloadTask:","文件创建成功与否："+ downloadlength);
            long contentlength=getContentLength(downurl);
            Log.d("DownloadTask:","文件长度："+contentlength);
            if(contentlength==0)
                return FAILED;
            else if (contentlength==downloadlength)
                return SUCESS;
            //内部定义一个方法 让文章看起来清晰
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder().addHeader("RANGE","bytes="+downloadlength+"-").url(downurl).build();
        Response response=client.newCall(request).execute();
            Log.d("DownloadTask:","获取响应成功！");
            if(response!=null)
            {
                inputStream=response.body().byteStream();
                Log.d("DownloadTask:","字节输入流建立完成"+"\n文件："+file);
                accessFile=new RandomAccessFile(file,"rw");
                Log.d("DownloadTask:","随机流建立完成");
                accessFile.seek(downloadlength);
                byte[] b=new byte[1024];
                int total=0;
                int len;
                Log.d("DownloadTask:","文件流建立全部完成");
                while((len=inputStream.read(b))!=-1)
                {
                    if(IsCancled)
                        return CANCELED;
                    else if(Ispaused) {
                        Log.d("DownloadTask:","暂停，返回PAUSED");
                        return PAUSED;
                    }
                    else {
                        total+=len;
                        accessFile.write(b,0,len);
                        int progress=(int)((total+downloadlength)*100/contentlength);
                        publishProgress(progress);
                    }
                }
                Log.d("DownloadTask:","下载完成！");
                return SUCESS;
            }
        }catch (IOException e)
        {
            Log.d("DownloadTask:","文件创建失败");
        }
finally {

                try {
                    if(accessFile!=null)
                    accessFile.close();
                    if (inputStream!=null)
                        inputStream.close();
                    if (IsCancled&&file!=null)
                        file.delete();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return null;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        //        更新进程
//        Log.d("DownloadTask:","以此代码返回值更新进度条成功");
        int progress=values[0];
        if(progress>lastprogress) {
            downloadListener.onProgress(progress);
            lastprogress = progress;
        }
    }

    @Override
    protected void onPostExecute(Integer integer) {
        //        返回结果后调用
        Log.d("DownloadTask:","返回值："+integer);
       switch (integer)
       {
           case CANCELED:downloadListener.onCancel();break;
            case PAUSED:downloadListener.onPause();break;
            case SUCESS:downloadListener.onSuccess();break;
            case FAILED:downloadListener.onFailed();break;
       }
    }
    public void setcancelDownload()
    {
        IsCancled=true;
    }
    public void setPausedDownload()
    {
        Ispaused=true;
    }
    private long getContentLength(String data) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(data).build();
        try {
            Response response = client.newCall(request).execute();
            if (response != null && response.isSuccessful()) {
long contentlength=response.body().contentLength();
                response.body().close();
                return contentlength;
            }
        } catch (IOException e) {
            Log.d("Download:", "；连接网络文件异常");
        }

        return 0;
    }
}
