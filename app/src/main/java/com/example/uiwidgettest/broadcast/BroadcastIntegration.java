package com.example.uiwidgettest.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.broadcast.seivice.ReceiveDatantentService;

public class BroadcastIntegration extends AppCompatActivity {
    EditText editText;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_broadcast_integration);
        Intent serviceIntent=new Intent(BroadcastIntegration.this,ReceiveDatantentService.class);
        startService(serviceIntent);
        Button button=(Button)findViewById(R.id.MonitorChange);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent intent=new Intent(BroadcastIntegration.this,MonitorNetworkStatusChange.class);
                startActivity(intent);
            }
        });
        Button button1=(Button)findViewById(R.id.MonitorNetworkStatus);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {Intent intent=new Intent(BroadcastIntegration.this,JudgeNetworkStatus.class);
                startActivity(intent);
            }
        });
        editText=(EditText)findViewById(R.id.BroadCastInputText);
        Button button2=(Button)findViewById(R.id.DIYbroadcast);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuffer sb=new StringBuffer("");
                sb.append(editText.getText().toString());
                if(!sb.toString().isEmpty()) {
                    Intent intent = new Intent("com.example.UIWidgetTest.MY_BROADCAST");
                    intent.putExtra("sendfromDIY", sb.toString());
                    sendBroadcast(intent);
                    sb.replace(0, sb.length(), "");
                    editText.setText("");
                }else
                {
                    Toast.makeText(getBaseContext(),"你未输入数据，请重新输入。",Toast.LENGTH_SHORT).show();
                }
            }
        });
        Button button3=(Button)findViewById(R.id.intologin);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(BroadcastIntegration.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        Button button4=(Button)findViewById(R.id.disOrderBroadcast);
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent("com.example.uiwidgettest.DISORDERBROADCAST");
                sendBroadcast(intent);
            }
        });
        Button button5=(Button)findViewById(R.id.OrderBroadcast);
        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent("com.example.uiwidgettest.ORDERBROADCAST");
                sendOrderedBroadcast(intent,null);
            }
        });

    }
}
