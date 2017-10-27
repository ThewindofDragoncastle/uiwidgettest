package com.example.uiwidgettest.byservice;
//完全版下载示例 本程序可能是至今为止写过最复杂的书本上的程序
import android.Manifest;
import android.app.Service;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

public class FullDownload extends AppCompatActivity implements View.OnClickListener {
    DownloadService.DownloadBinder1 binder1;
    private ServiceConnection conn=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
             binder1=(DownloadService.DownloadBinder1)service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    TextView displayHTTP;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_download);
        displayHTTP=(TextView)findViewById(R.id.displayHTTP);
        editText=(EditText)findViewById(R.id.inputHTTP);
        editText.setText("http://隐藏IP/"+"mysql-5.6.17-winx64.zip");
        displayHTTP.setText("你只需输入你的地址就可以轻轻松松下载啦！");
        Button StartDownload= (Button)findViewById(R.id.StartDownload);
        StartDownload.setOnClickListener(this);
        Button PauseDownload= (Button)findViewById(R.id.PauseDownload);
        PauseDownload.setOnClickListener(this);
        Button CancelDownload= (Button)findViewById(R.id.CancelDownload);
        CancelDownload.setOnClickListener(this);
        Intent intent=new Intent(this,DownloadService.class);
        startService(intent);
        bindService(intent,conn,BIND_AUTO_CREATE);
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        }
    }

    @Override
    public void onClick(View v) {
        if(binder1==null)
            return;
        switch (v.getId())
        {
            case R.id.StartDownload:
                String path;
                String  url;
//                String url="https://raw.githubusercontent.com/guolindev/eclipse/master/eclipse-inst-win64.exe";
                try {
                    path=editText.getText().toString().split("隐藏IP/")[1];
                    url="http://39.108.123.220/"+path;
                    displayHTTP.setText("下载地址：http://隐藏IP/"+url.split("39.108.123.220/")[1]);
                }catch (ArrayIndexOutOfBoundsException e)
                {
                   url=editText.getText().toString();
                }
//                 url="http://192.168.0.102/mysql-5.6.17-winx64.zip";

                MyLog.i("FullDownload","地址："+url);
                binder1.startdownload(url);
                break;
            case R.id.PauseDownload:
                binder1.pause();
                break;
            case R.id.CancelDownload:
                binder1.cancel();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode)
        {
            case 1:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults.length>0)
                {

                }
                    else
            {
                Toast.makeText(getBaseContext(), "你拒绝了权限！", Toast.LENGTH_SHORT).show();
                finish();
            }
                    break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }
}
