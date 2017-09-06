package com.example.uiwidgettest.byinternet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import java.io.IOException;
import java.io.StringReader;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHTTPGetData extends AppCompatActivity {

    private TextView  DatafromServer;
    private StringBuilder DatafromServerDispaly=new StringBuilder("从服务器获得的数据为：\n");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_okhttpget_data);
        Button requestDatafromBaidu=(Button)findViewById(R.id.HTMLdata_OKHTTP);
        DatafromServer=(TextView)findViewById(R.id.DatafromServer_OKHTTP) ;
        DatafromServer.setText(DatafromServerDispaly.toString()+"空");
        requestDatafromBaidu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                senddata();
            }
        });
    }
    private void senddata()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client=new OkHttpClient();
                    Request request=new Request.Builder().url("https://www.baidu.com").build();
                    Response response = client.newCall(request).execute();
                    String responseData=response.body().string();
                    DatafromServerDispaly.setLength(0);
                    DatafromServerDispaly.append(responseData);
                    showData();
                }catch (IOException e)
                {
                    Log.d(getLocalClassName(),"IO流异常");
                }
            }
        }).start();
    }
    private void showData()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DatafromServer.setText(DatafromServerDispaly.toString());
            }
        });
    }
}
