package com.example.uiwidgettest;



import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.activity.ActivityIntegration;
import com.example.uiwidgettest.broadcast.BroadcastIntegration;
import com.example.uiwidgettest.broadcast.seivice.ReceiveDatantentService;
import com.example.uiwidgettest.contentprovider.ChatTranscripts;
import com.example.uiwidgettest.contentprovider.ContentProviderIntegration;
import com.example.uiwidgettest.filesave.FileSaveIntegration;
import com.example.uiwidgettest.byinternet.InternetIntergration;
import com.example.uiwidgettest.byservice.ServiceIntergration;
import com.example.uiwidgettest.fragment.FragmentIntergration;
import com.example.uiwidgettest.ui.UIintegration;
import com.example.uiwidgettest.mobilemultimedia.mobilemultimediaIntegration;
public class FunctionIntegration extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent serviceIntent=new Intent(FunctionIntegration.this,ReceiveDatantentService.class);
        startService(serviceIntent);
//初始化应用 一打开应用就初始化内容提供器 的数据库。
        CreatTable();
        setContentView(R.layout.activity_function_integration);
        Button b1=(Button)findViewById(R.id.IntoActivity);
        Button b2=(Button)findViewById(R.id.IntoBroadCast);
        Button b3=(Button)findViewById(R.id.IntoFragment);
        Button b4=(Button)findViewById(R.id.IntoUI);
        Button b5=(Button)findViewById(R.id.IntotheSQL);
        Button b6=(Button)findViewById(R.id.IntoCPI);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,ActivityIntegration.class);
                startActivity(intent);
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,BroadcastIntegration.class);
                startActivity(intent);
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,FragmentIntergration.class);
                startActivity(intent);
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,UIintegration.class);
                startActivity(intent);
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this, FileSaveIntegration.class);
                startActivity(intent);
            }
        });
        b6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,ContentProviderIntegration.class);
                startActivity(intent);
            }
        });
        Button b7=(Button)findViewById(R.id.IntoMultiMedia);
        b7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,mobilemultimediaIntegration.class);
                startActivity(intent);
            }
        });
        Button b8=(Button)findViewById(R.id.IntoByinternet);
        b8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,InternetIntergration.class);
                startActivity(intent);
            }
        });
        Button b9=(Button)findViewById(R.id.IntoService1);
        b9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,ServiceIntergration.class);
                startActivity(intent);
            }
        });
        Button b10=(Button)findViewById(R.id.NextPage);
        b10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(FunctionIntegration.this,NextPage.class);
                startActivity(intent);
            }
        });
    }
    private void CreatTable() {
        ChatTranscripts transcripts = new ChatTranscripts(this, "mychat", null, 2);
        SQLiteDatabase writesqLite = transcripts.getWritableDatabase();
        SQLiteDatabase readsqLite = transcripts.getReadableDatabase();
        if (!readsqLite.query("ChatTranscripts", null, null, null, null, null, null).moveToNext()) {
            //没有数据则对数据库初始化
            ContentValues values = new ContentValues();
           for (int i=0;i<50;i++) {
               values.put("recipent", "陈宏林");
               values.put("account", "66666");
               values.put("addresser", "407748918");
               values.put("message", "日了狗");
               writesqLite.insert("chattranscripts", null, values);
               values.clear();
           }
           MyLog.d("Functiongration:","聊天数据初始化完成");
        }
    }
}
