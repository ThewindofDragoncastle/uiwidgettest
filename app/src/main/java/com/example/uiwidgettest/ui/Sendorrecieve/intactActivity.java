package com.example.uiwidgettest.ui.Sendorrecieve;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.chatgui;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;



public class intactActivity extends AppCompatActivity {
private List<Intact> Intactlist;
    private ImageView touxiang;
    private ImageView online;
    static DataOutputStream dos;
    private Socket socket;
    private DataInputStream inputStream;
    RecyclerView recyclerView;
    IntactAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intact);
        touxiang=(ImageView)findViewById(R.id.IntactImage);
        online=(ImageView)findViewById(R.id.online);
        Intactlist=new ArrayList<>();
        Intactlist.clear();
        InitList();

        recyclerView=(RecyclerView)findViewById(R.id.intactrecycle);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        setAdapter();
        connServer();

    }
    private void connServer()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    socket = new Socket("39.108.123.220",30000);
                    dos=new DataOutputStream(socket.getOutputStream());
                    inputStream=new DataInputStream(socket.getInputStream());
                    for (Intact intact:Intactlist) {
                        //将所有联系人上传服务器判断
                        dos.writeUTF("是否未读:"+intact.getCurrentaccount()+":"+intact.gettargetAccount()+":");
                        if(intact.gettargetAccount()!=intact.getCurrentaccount())
                      dos.writeUTF("在线:"+intact.gettargetAccount()+":");
                        else
                            intact.setOnline(R.drawable.online);
                        dos.flush();
                    }
                        String content;
                                while ((content = inputStream.readUTF()) != null)
                                {
                                    MyLog.i("intactActivity：",content);
                                    if(content.split(":")[0].equals("是否未读"))
                                {
                                   final String unread=content.split(":")[1];
                                    MyLog.i("intactActivity：",unread);
                                    final String targetAccount=content.split(":")[2];
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {

                                            for (int i=0;i<Intactlist.size();i++) {
                                                Intact intact=Intactlist.get(i);
                                                MyLog.i("intactActivity：","(数组)当前："+targetAccount+"目标:"+intact.gettargetAccount());
                                                if(targetAccount.equals(intact.gettargetAccount())) {//目标等于目标 当前帐户是唯一的
                                                    //两者的目标账户一致说明是同一个intact对象
                                                    if (unread.equals("有")) {
                                                        intact.setUnread(true);
                                                    } else {
                                                        intact.setUnread(false);
                                                    }
                                                    adapter.notifyItemChanged(i);

                                                }
                                            }

                                        }
                                    });


                                }
                                    else if(content.split(":")[0].equals("状态"))
                                    {
                                        final String account=content.split(":")[1];
                                        final  String status=content.split(":")[2];
                                        runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                for (int i=0;i<Intactlist.size();i++) {
                                                    Intact intact=Intactlist.get(i);
                                                    MyLog.i("intactActivity:",status);
                                                    if(account.equals(intact.gettargetAccount())) {//取得的账户必须和目标账户一致
                                                        if (status.equals("在线")||account.equals(intact.getCurrentaccount()))
                                                            intact.setOnline(R.drawable.online);
                                                        else if (status.equals("不在线"))
                                                            intact.setOnline(R.drawable.outline);
                                                    }
                                                    adapter.notifyItemChanged(i);
                                                }

                                            }
                                        });
                                    }
                                }
                }catch (IOException e)
                {
                    Log.d("intaActivity:","不能连接ip");
                }
            }
        }).start();
    }
    private void setAdapter()
    {
        adapter=new IntactAdapter(Intactlist);
        adapter.setOnItemListener(new IntactAdapter.onItemListener()
        {
            @Override
            public void onClick(View view, int position) {
                Intact intact=Intactlist.get(position);
                final String targetAccount=intact.gettargetAccount();
                final String Currentaccount=intact.getCurrentaccount();
                Intent intent=new Intent(intactActivity.this,chatgui.class);
                intent.putExtra("targetaccount",targetAccount);
                intent.putExtra("currentaccount",Currentaccount);
                MyLog.i("IntactAdapter:",Currentaccount);
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, final int position) {
                new AlertDialog.Builder(intactActivity.this).setTitle("你确定删除此对话吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intactlist.remove(position);
                                adapter.notifyItemRemoved(position);
                            }
                        }).setNegativeButton("取消",null).show();

//                Toast.makeText(getBaseContext(),"你妄图删除我！做梦",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void InitList()
    {
        String currentaccount=getIntent().getStringExtra("account");
        MyLog.i("intactActivity:",currentaccount);
        Intact intact=new Intact(currentaccount,666666+"");
        intact.setName("小陈");
        intact.setUnread(false);//初始都无
        intact.setTime("13:56");
        Intactlist.add(intact);
        intact=new Intact(currentaccount,""+407748918);
        intact.setName("龙城飞絮");
        intact.setUnread(false);
        intact.setTime("00:00");
        Intactlist.add(intact);
    }
}
