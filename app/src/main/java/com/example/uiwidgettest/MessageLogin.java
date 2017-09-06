package com.example.uiwidgettest;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.uiwidgettest.ui.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.security.PrivateKey;

/**
 * Created by 40774 on 2017/8/20.
 */

public class MessageLogin {
    private String Filename;
    private String data;
    public MessageLogin(String Filename,String data)
    {
        this.data=data;
        this.Filename=Filename;
    }
    public void CreateLogin()
    {
        File dir=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+File.separator+"信息记录");
        File file=new File(Environment.getExternalStorageDirectory()+File.separator+"开发专用文件夹"+File.separator+"信息记录"+File.separator+Filename);
        BufferedWriter writer=null;
        BufferedReader reader=null;
        if (dir.isFile())
        {
            dir.delete();
        }
        if (!dir.exists())
        {
            dir.mkdirs();
        }
        if (file.isDirectory())
        {
            file.delete();
        }
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
             reader= new BufferedReader(new StringReader(data));
             writer = new BufferedWriter(new FileWriter(file,true));
            //true代表可追加 但是我们要令相同的就不再追加
             String line;
             StringBuilder buff=new StringBuilder("");
             while ((line=reader.readLine())!=null)
             {
                buff.append(line+"\n");
             }
            Log.d("MessageLogin:",buff.toString());
            writer.write(buff.toString());
            writer.flush();
        }catch (IOException e)
        {
            Log.d("MessageLogin:","文件流异常");
        }
        finally {
            if (reader!=null)
            {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (writer!=null)
            {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
