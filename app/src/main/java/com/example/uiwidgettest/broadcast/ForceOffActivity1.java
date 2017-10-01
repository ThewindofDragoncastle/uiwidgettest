package com.example.uiwidgettest.broadcast;

import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.uiwidgettest.broadcast.seivice.ReceiveDatantentService;
import com.example.uiwidgettest.broadcast.seivice.StartService;
import com.example.uiwidgettest.R;

public class ForceOffActivity1 extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_force_off1);
        final Intent intent=new Intent(ForceOffActivity1.this, ReceiveDatantentService.class);
        startService(intent);
        new Thread(new Runnable() {
            @Override
            public void run() {//用不用线程都差不多

                bindService(intent,StartService.connection,BIND_AUTO_CREATE);//依旧会先调用开始服务
            }
        }).start();

//        weReceive.TransportConn(connection);
        //程序要崩溃可能是服务问题
//        weReceive.SetDataListener(this);//把监听器传入//解决办法应该是把东西放在连接的时候
        Button button=(Button)findViewById(R.id.forcetooffline);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LocalBroadcastManager manager=LocalBroadcastManager.getInstance(ForceOffActivity1.this);
                        Intent intent=new Intent("com.example.uiwidgettest.GOFORITOUTLINE");
                        sendBroadcast(intent);
            }
        });
        Button button1=(Button)findViewById(R.id.tosecondactivity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(ForceOffActivity1.this,ForceOffLineActivity2.class);
                unbindService(StartService.connection);
                startActivity(intent);
                finish();
            }
        });
    }

}
