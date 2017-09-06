package com.example.uiwidgettest.byinternet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.uiwidgettest.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class JSONobjectparse extends AppCompatActivity implements Runnable{

    private TextView display;
    private CheckBox checkBox;
    private boolean PHONE;
    static StringBuilder sb=new StringBuilder();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jsonobjectparse);
        display=(TextView)findViewById(R.id.JSONData);
        display.setText("无数据返回");
        Button button=(Button)findViewById(R.id.jsonobjectparseJSON);
        checkBox=(CheckBox)findViewById(R.id.jsonobjectcheckbox);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                    PHONE=false;
                else
                    PHONE=true;
                Thread s=new Thread(new JSONobjectparse());
                s.start();
                try {
                    s.join();
                }catch (InterruptedException e)
                {
                    Log.d("JSONobjectparse","中断异常");
                }
                show();
            }
        });
    }

    @Override
    public void run() {
        OkHttpClient client=new OkHttpClient();
        Request request;
        if(!PHONE)
                request =new Request.Builder().url("http://192.168.0.102/appinfo.json").build();
            else
                request =new Request.Builder().url("http://10.0.2.2/appinfo.json").build();
        try {
            Response response = client.newCall(request).execute();
            String data=response.body().string();
            parseit(data);
        }catch (IOException e)
        {
            Log.d("JSONobjectparse","");
        }
    }
    private void show()
    {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                display.setText(sb.toString());
            }
        });
    }
    private void parseit(String data)
    {
        try {
            sb.setLength(0);
            JSONArray jsonArray=new JSONArray(data);
            for(int i=0;i<jsonArray.length();i++) {
                JSONObject jsonObject=jsonArray.getJSONObject(i);
                sb.append("id="+jsonObject.getString("id")+" name="+jsonObject.getString("name")+
                        " version="+jsonObject.getString("version")+"\n");
                Log.d("JSONobjectparse",sb.toString());
//                show();
            }
        }catch (JSONException e)
        {
            Log.d("JSONobjectparse","JSON异常");
        }

    }
}
