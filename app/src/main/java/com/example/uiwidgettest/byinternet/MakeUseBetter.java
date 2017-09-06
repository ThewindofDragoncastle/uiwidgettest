package com.example.uiwidgettest.byinternet;
//大量优化使用 okhttp自带回调函数callback 此代码CODE木有使用
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uiwidgettest.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MakeUseBetter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_use_better);
    }
}
class HttpConnection
{
    public static void SendRequest(final String url,final HttpCallbackListener listener)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection httpURLConnection=null;
                try {
                    URL url1 = new URL(url);
                    httpURLConnection=(HttpURLConnection)url1.openConnection();
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.setConnectTimeout(8000);
                    httpURLConnection.setDoInput(true);
                    httpURLConnection.setRequestMethod("GET");
                    BufferedReader reader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                    StringBuilder sb=new StringBuilder();
                    String line;
                    while((line=reader.readLine())!=null)
                    {
                        sb.append(line);
                    }
                    if(listener!=null)
                    listener.onFinlish(sb.toString());
                }catch (MalformedURLException e)
                {
                    if(listener!=null)
                        listener.onError();
                   }
                catch (IOException e)
                {
                    if(listener!=null)
                        listener.onError();
                }
                finally {
                    if(httpURLConnection!=null)
                        httpURLConnection.disconnect();
                }

            }
        }).start();

    }
}
interface HttpCallbackListener
{
  void onFinlish(String data);
    void onError();
}