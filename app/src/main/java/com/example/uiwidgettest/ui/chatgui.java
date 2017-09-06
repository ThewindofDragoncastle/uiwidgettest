package com.example.uiwidgettest.ui;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.Sendorrecieve.Send;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class chatgui extends AppCompatActivity implements Runnable {
    private static List<Message> mmessageList=new ArrayList<Message>();
    public EditText inputText;
    private Button send;
    private  static RecyclerView recyclerView;
    private int index;
    private StringBuffer sb=new StringBuffer("");
    static MsgAdapter adapter;
//    static PrintStream ps;
    static DataOutputStream dos;
    private Socket socket;
   static String currentaccount;
   static String targetaccount;
    static Handler handler=new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            switch (msg.what)
            {
                case 2:
                    Log.d("ChatfromServer",(String)msg.obj);
                    String data=(String)msg.obj;
                    Message message= new Message(data,0);
                    mmessageList.add(message);
                    adapter.notifyItemInserted(mmessageList.size()-1);
                    recyclerView.scrollToPosition(mmessageList.size()-1);
                    Log.d("chatgui","已经接收到数据："+data);
                    break;
                case 3:
                    String data1=(String)msg.obj;
                    Message message1= new Message("服务器被用户访问。\n当前用户："+data1+"位。",0);
                    mmessageList.add(message1);
                    adapter.notifyItemInserted(mmessageList.size()-1);
                    recyclerView.scrollToPosition(mmessageList.size()-1);
                    Log.d("chatgui","已经接收到数据："+data1);
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("chatgui","本机编码："+System.getProperty("file.encoding"));
        informdata();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatgui);
        inputText=(EditText)findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.chatguibutton1);
        recyclerView=(RecyclerView)findViewById(R.id.reclyer2);
        Log.d("此处未发生错误","");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter =new MsgAdapter(mmessageList);
        recyclerView.setAdapter(adapter);
        currentaccount= getIntent().getStringExtra("currentaccount");
       targetaccount=getIntent().getStringExtra("targetaccount");
        MyLog.i(getLocalClassName(),currentaccount);//这句话不能在线程开启之后啊啊啊啊嗄
        new Thread(new chatgui()).start();//连接网络线程


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data=inputText.getText().toString();
                if(!data.isEmpty())
                {
                    sb.replace(0,sb.length(),data.toString());
                    Log.d("ChatfromServer","输入框获取的数据："+sb.toString());
                    Message message1=new Message(sb.toString(),Message.TYPE_SEND);
                    Log.d("ChatfromServer", "发送成功！");


                    Send.sendtoserver(dos,sb.toString(),"消息:"+targetaccount);
                    MyLog.i("chatgui:","消息目标账户:"+targetaccount);
                    mmessageList.add(message1);
                    adapter.notifyItemInserted(mmessageList.size()-1);
                    recyclerView.scrollToPosition(mmessageList.size()-1);
                    inputText.setText("");
                }
                else
                {
                    Toast.makeText(v.getContext(),"请输入数据。",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    public void run()
    {
        try {
             socket = new Socket("39.108.123.220",30000);
//            socket = new Socket("172.18.242.163",30000);
            Log.d("ChatfromServer","成功连接服务器");
            dos=new DataOutputStream(socket.getOutputStream());
            Send.sendcurrentinfo(dos,currentaccount);
            dos.writeUTF("未读:"+currentaccount+":"+targetaccount);
            dos.flush();
            //初始化账号信息  必须使用静态子线程否则就是null
            String line=null;
            new Thread(new RecieveThread(socket)).start();
        }catch (IOException e)
        {
            Log.d("ChatfromServer","不能连接ip");
        }
    }
    private void informdata()
    {
       mmessageList.clear();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if (socket != null)
                socket.close();
        }catch (IOException e)
        {
            Log.d("ChatfromServer","不能关闭");
        }
    }
}

class RecieveThread implements Runnable
{
    private  Socket socket;
//    private BufferedReader br=null;
    DataInputStream dataInputStream=null;
    public RecieveThread(Socket socket)
    {
        this.socket=socket;
        try {
            dataInputStream=new DataInputStream(new DataInputStream(socket.getInputStream()));
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
            while ((content=dataInputStream.readUTF())!=null)
            {
                android.os.Message message=new android.os.Message();
                Log.d("ChatfromServer","准备接收！");
                if(!content.split(":")[0].equals("当前连接")) {
                    message.what = 2;
                    message.obj =content;
                    chatgui.handler.sendMessage(message);
                    Log.d("ChatfromServer", "接收成功！");
                }
                else {
                    message.what = 3;
                    message.obj=content.split(":")[1];
                    chatgui.handler.sendMessage(message);
                }
            }
        }catch (IOException e)
        {
            Log.d("ChatfromServer","获取Socket输出流失败。");
        }
    }
}