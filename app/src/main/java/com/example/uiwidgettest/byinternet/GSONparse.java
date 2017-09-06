package com.example.uiwidgettest.byinternet;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GSONparse extends AppCompatActivity implements Runnable{
    private TextView display;
    private CheckBox checkBox;
    private boolean PHONE;
    static StringBuffer sb=new StringBuffer("");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gsonparse);
        display=(TextView)findViewById(R.id.GSONData);
        Button button=(Button)findViewById(R.id.GSONparseJSON);
        checkBox=(CheckBox)findViewById(R.id.GSONcheckbox);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkBox.isChecked())
                    PHONE=false;
                else
                    PHONE=true;
               Thread s= new Thread(new GSONparse());
                s.start();
                try
                {
                    s.join();
                }catch (InterruptedException e)
                {
                    Log.d("中断异常","");
                }
                show();
            }
        });
    }
public void show()
{
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            display.setText(sb.toString());
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
            Log.d("GSONparse","");
        }
    }
    private void parseit(String data)
    {
        sb.setLength(0);
        Gson gson=new Gson();
        ArrayList<AppGSON> as=new ArrayList<AppGSON>();
        as=gson.fromJson(data,new TypeToken<ArrayList<AppGSON>>(){}.getType());
        for(AppGSON ap:as) {
            sb.append("id="+ap.getId()+" name="+ap.getName()+" version="+ap.getVersion()+"\n");
        }
    }
}
class AppGSON
{
    private String name;
    private String id;
    private String version;

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getVersion() {
        return version;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

}
