package com.example.uiwidgettest.ui;

import android.content.Intent;
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

import com.example.uiwidgettest.broadcast.seivice.DataListener;
import com.example.uiwidgettest.broadcast.seivice.StartService;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

public class chatgui extends AppCompatActivity {
    private static List<Message> mmessageList=new ArrayList<Message>();
    private final int CHATGUI=2;
    private final String TAG="chatgui";
    public EditText inputText;
    private Button send;
    private  static RecyclerView recyclerView;
    private StringBuffer sb=new StringBuffer("");
    static MsgAdapter adapter;
    static String currentaccount;
    static String targetaccount;
//    private ReceiveDatantentService.WeReceive weReceive;
    DataListener logindata=new DataListener() {
        @Override
        public void ReturnData(String data) {
        MyLog.d(TAG,"从服务返回的数据："+data);
            handledata(data);
        }

        @Override
        public void Already() {
            MyLog.d("LoginActivity:","已经准备好了，可以向服务输出数据。");
        }
    };
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
//        weReceive=(ReceiveDatantentService.WeReceive)getIntent()
//                .getSerializableExtra("WeReceive");
        String message=null;
        try {
            message=getIntent().getExtras().getString("Message");
        }catch (NullPointerException e)
        {
            MyLog.d(TAG,"未得到传来的消息");
        }

            if (message!=null) {
                handledata(message);
                MyLog.d(TAG,"通知传递的消息："+message);
            }

        StartService.weReceive.SetDataListener(logindata);
        StartService.weReceive.SetCurrentActivity(CHATGUI);
        inputText=(EditText)findViewById(R.id.input_text);
        send=(Button)findViewById(R.id.chatguibutton1);
        recyclerView=(RecyclerView)findViewById(R.id.reclyer2);
        Log.d("此处未发生错误","");
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter =new MsgAdapter(mmessageList);
        recyclerView.setAdapter(adapter);
        currentaccount=StartService.weReceive.getCurrentAccount() ;
        targetaccount=StartService.weReceive.getTargetAccount();
        MyLog.d(TAG,"当前："+currentaccount+"目标："+targetaccount);

        //这里犯了一个小错误 把account清o再传递
//        targetaccount=getIntent().getStringExtra("TargetAccountService");
//       targetaccount=getIntent().getStringExtra("TargetAccount");
        MyLog.i(getLocalClassName(),currentaccount);
        StartService.weReceive.WriteToService(  "账号信息:"+currentaccount+":");
        StartService.weReceive.WriteToService("未读:"+currentaccount+":"+targetaccount);
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
                    StartService.weReceive.WriteToService("消息:"+targetaccount+":"+sb);
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
    private void informdata()
    {
       mmessageList.clear();
    }
    private void handledata(String content)
    {
        android.os.Message message=new android.os.Message();
//        Log.d("ChatfromServer","准备接收！");
        if(!content.split(":")[0].equals("当前连接")) {
            message.what = 2;
            message.obj =content;
            chatgui.handler.sendMessage(message);
            Log.d("ChatfromServer", "接收成功！");
        }
        else if(content.split(":")[0].equals("强制下线"))
        {
            Intent intent=new Intent("com.example.uiwidgettest.GOFORITOUTLINE");
            sendBroadcast(intent);
        }
        else {
            message.what = 3;
            message.obj=content.split(":")[1];
            chatgui.handler.sendMessage(message);
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        StartService.weReceive.SetCurrentActivity(CHATGUI);
    }
}