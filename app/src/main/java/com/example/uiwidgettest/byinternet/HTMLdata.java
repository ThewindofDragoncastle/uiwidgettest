package com.example.uiwidgettest.byinternet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HTMLdata extends AppCompatActivity {
    private TextView DatafromServer;
    private StringBuilder DatafromServerDisplay=new StringBuilder("从服务器获得的数据为：\n");
//    StringBuilder DatafromHTMLDispaly=new StringBuilder("从服务器获得的数据为：\n");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_htmldata);
        Button requestDatafromBaidu=(Button)findViewById(R.id.HTMLdata);
        DatafromServer=(TextView)findViewById(R.id.DatafromServer) ;
//        TextView HTMLData=(TextView)findViewById(R.id.HTMLDataVIEW);
        DatafromServer.setText(DatafromServerDisplay.toString()+"空");
         requestDatafromBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendrequesttoBAiDU();
            }
        });
    }
    public void sendrequesttoBAiDU()
    {
        //线程
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                BufferedReader bufferedReader=null;
                try {
                    URL url = new URL("https://www.baidu.com");
                    httpURLConnection=(HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);
                    InputStream inputStream=httpURLConnection.getInputStream();
                    //对数据处理
                    bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                    String line;
                    DatafromServerDisplay.setLength(0);
                    while ((line=bufferedReader.readLine())!=null)
                    {
                        DatafromServerDisplay.append(line);
                    }
                    showHTML();
                }catch (MalformedURLException e)
                {
                    Log.d(getPackageName()+getLocalClassName(),"URL出错！（变形）");
                }
                catch (IOException e)
                {
                    Log.d(getPackageName()+getLocalClassName(),"文件流出错！");
                }
                finally {
                    if(httpURLConnection!=null)
                        httpURLConnection.disconnect();
                    if(bufferedReader!=null)
                    try{
                        bufferedReader.close();
                    }catch (IOException e)
                    {
                        Log.d(getPackageName()+getLocalClassName(),"关闭文件流出错！");
                    }
                }
            }
        }).start();

    }
    private void showHTML()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DatafromServer.setText(DatafromServerDisplay);
            }
        });
    }
}
