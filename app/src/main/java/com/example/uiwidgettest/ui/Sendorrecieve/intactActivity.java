package com.example.uiwidgettest.ui.Sendorrecieve;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
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
    static String currentaccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intact);
        currentaccount=getIntent().getStringExtra("account");
        touxiang=(ImageView)findViewById(R.id.IntactImage);
        online=(ImageView)findViewById(R.id.online);
        Intactlist=new ArrayList<>();
        Intactlist.clear();
        InitList();

        recyclerView=(RecyclerView)findViewById(R.id.intactrecycle);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
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
                    dos.writeUTF("请求联系人信息:"+currentaccount+":");
                    recievefromServer();
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
            public void onLongClick(final View view, final int position) {
                new AlertDialog.Builder(intactActivity.this).setTitle("你确定删除此对话吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                final Intact intact=Intactlist.get(position);
                                Intactlist.remove(position);
                                adapter.notifyItemRemoved(position);
                                Snackbar.make(view,"删除成功",Snackbar.LENGTH_SHORT).setActionTextColor(getResources().getColor(R.color.plum))
                                        .setAction("恢复", new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Intactlist.add(intact);
                                                adapter.notifyItemRemoved(position);
                                            }
                                        }).show();
                            }
                        }).setNegativeButton("取消",null).show();

//                Toast.makeText(getBaseContext(),"你妄图删除我！做梦",Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void InitList()
    {

//        MyLog.i("intactActivity:",currentaccount);
//        Intact intact=new Intact(currentaccount,666666+"");
//        intact.setName("小陈");
//        intact.setUnread(false);//初始都无
//        intact.setTime("13:56");
//        Intactlist.add(intact);
//        intact=new Intact(currentaccount,""+407748918);
//        intact.setName("龙城飞絮");
//        intact.setUnread(false);
//        intact.setTime("00:00");
//        Intactlist.add(intact);
    }
    private void recievefromServer()
    {
        String content;
        try {
            while ((content = inputStream.readUTF()) != null)
            {
                MyLog.i("intactActivity：",content);
                if(content.split(":")[0].equals("获取联系人"))
                {

                    if(!content.split(":")[3].equals("空")) {
                        MyLog.d("intactvity:","从云端加载数据成功！");
                        Intact intact = new Intact(currentaccount, content.split(":")[1]);//账户
                        //当前帐户 和 目标账户 服务器获知的账户
                        intact.setName(content.split(":")[2]);//名字
                        Intactlist.add(intact);
                        intact.setUnread(false);//初始化必须接受的信息
                        if (content.split(":")[3].equals("最后一项")) {
                            loadIntact(dos);
                        }
                    }else
                    {
                        MyLog.d("intactvity:","无联系人！");
                        Intact intact=new Intact("","");
                        intact.setName("无联系人");
//                        intact.setOnline(0);
                        intact.setTime("12:00");
                        intact.setUnread(false);
                        Intactlist.add(intact);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setAdapter();
                            }
                        });

                    }
                }
               else if(content.split(":")[0].equals("是否未读"))
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
                                        intact.setTime("12:00");
                                    } else {
                                        intact.setUnread(false);
                                        intact.setTime("12:00");
                                    }
                                    setAdapter();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  void loadIntact(DataOutputStream dos)
    {
        for (Intact intact:Intactlist) {
            //将所有联系人上传服务器判断
            try {
                dos.writeUTF("是否未读:"+intact.getCurrentaccount()+":"+intact.gettargetAccount()+":");

            if(intact.gettargetAccount()!=intact.getCurrentaccount())
                dos.writeUTF("在线:"+intact.gettargetAccount()+":");
            else
                intact.setOnline(R.drawable.online);
            dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
