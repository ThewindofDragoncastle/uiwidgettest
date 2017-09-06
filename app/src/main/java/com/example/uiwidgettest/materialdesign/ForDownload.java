package com.example.uiwidgettest.materialdesign;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.example.uiwidgettest.MyApplication;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.materialdesign.ToolbarTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 40774 on 2017/8/27.
 */

public class ForDownload {
    private final int FAILED=0;
    private final int SUCCESS=1;
    public int Download(URL url)
    {
        //此线程于子线程执行
        String filename=url.toString().substring(url.toString().lastIndexOf("/"));
        MyLog.d("ForDownload:","文件名字："+filename);
        InputStream is=null;
        RandomAccessFile accessFile=null;
        File filedir=new File(Environment.getExternalStorageDirectory()+File.separator
                +"开发专用文件夹"+File.separator+"app使用图片");
        File filename1=new File(Environment.getExternalStorageDirectory()+File.separator
                +"开发专用文件夹"+File.separator+"app使用图片"+filename);
        try {
            long downedloadlength=0;
            if(!filedir.exists())
            {
                filedir.mkdirs();
            }
            if (!filename1.exists())
            {
                filename1.createNewFile();
                downedloadlength=filename1.length();
            }
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder().url(url).build();
            Response response=client.newCall(request).execute();
            long totallength=0;
            if(response!=null&&response.isSuccessful())
            totallength=response.body().contentLength();
            if(totallength==0)
                return FAILED;
            if(downedloadlength==totallength)
                return SUCCESS;
             response.body().close();
             request=new Request.Builder().addHeader("RANGE","bytes="+downedloadlength+"-").url(url).build();
       response=client.newCall(request).execute();
            if(response!=null)
            {
                is=response.body().byteStream();
                accessFile=new RandomAccessFile(filename1,"rw");
                accessFile.seek(downedloadlength);
                byte[] b=new byte[1024];
                int lens=0;
                while ((lens=is.read(b))!=-1)
                {
                  accessFile.write(b,0,lens);
                }
                response.body().close();
                return SUCCESS;
            }
        }catch (IOException e)
        {

        }
        finally
        {

    try {
        if(is!=null)
        is.close();
        if(accessFile!=null)
            accessFile.close();
    } catch (IOException e) {
        e.printStackTrace();
    }

        }
        return FAILED;
    }
}
