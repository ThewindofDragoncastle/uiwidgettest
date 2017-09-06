package com.example.uiwidgettest.byinternet;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.Socket;
import java.util.logging.LogRecord;

public class chatfromserver extends AppCompatActivity implements Runnable {
    public EditText input;
    public static TextView display;
    private StringBuffer sb=new StringBuffer("");
    static PrintStream ps;
    static Handler handler=new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 2:
                    Log.d("ChatfromServer",(String)msg.obj);
                    display.setText((String)msg.obj);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatfromserver);
        display=(TextView)findViewById(R.id.chatfromServer1);
        input=(EditText)findViewById(R.id.chatfromServer2);
        Button send=(Button)findViewById(R.id.send231);
        display.setText("没有数据！");
        new Thread(new chatfromserver()).start();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sb.replace(0,sb.length(),input.getText().toString());
                Log.d("ChatfromServer","输入框获取的数据："+sb.toString());
                    ps.println(sb.toString());
                    Log.d("ChatfromServer", "发送成功！");

            }
        });


    }

    @Override
    public void run()
    {
        try {
            Socket socket = new Socket("111.231.56.231",30000);
            Log.d("ChatfromServer","成功连接服务器");
            ps=new PrintStream(socket.getOutputStream());
            String line=null;
            new Thread(new ClientThread(socket)).start();
        }catch (IOException e)
        {
            Log.d("ChatfromServer","不能连接ip");
        }
    }
}
class ClientThread implements Runnable
{
    private  Socket socket;
    private BufferedReader br=null;
    public ClientThread(Socket socket)
    {
        this.socket=socket;
        try {
            br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
        }catch (IOException e)
        {
            Log.d("ChatfromServer","获取Socket输出流失败。");
        }

    }
    @Override
    public void run() {
        try {
            String content=null;
            Log.d("ChatfromServer","客户端线程已进入");
            while ((content=br.readLine())!=null)
            {
                android.os.Message message=new android.os.Message();
                Log.d("ChatfromServer","准备接收！");
                message.what=2;
                message.obj=content;
                chatfromserver.handler.sendMessage(message);
                Log.d("ChatfromServer","接收成功！");
            }
        }catch (IOException e)
        {
            Log.d("ChatfromServer","获取Socket输出流失败。");
        }


    }
}