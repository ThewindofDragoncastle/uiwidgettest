package com.example.uiwidgettest.ui.sendorrecieve;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.example.uiwidgettest.broadcast.BaseActivity;
import com.example.uiwidgettest.broadcast.seivice.DataListener;
import com.example.uiwidgettest.broadcast.seivice.StartService;
import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.ui.chatgui;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;



public class intactActivity extends BaseActivity {
private List<Intact> Intactlist;
    private Intact GSONintact;
    private ImageView touxiang;
    private final int INTACTACTIVITY=1;
    private ImageView online;
    RecyclerView recyclerView;
    IntactAdapter adapter;
    static String currentaccount;
    private final String TAG="intactActivity:";
//    private ReceiveDatantentService.WeReceive weReceive;
    DataListener logindata=new DataListener() {
        @Override
        public void ReturnData(String data) {
            MyLog.d(TAG,"从服务中返回的数据:"+data);
//            handledata(data);
            if(!data.split(":")[0].equals("获取联系人")&&!data.split(":")[0].equals("强制下线"))
            hanledatabyGSON(data);
                else if(data.split(":")[0].equals("强制下线"))
            {
                Intent intent=new Intent("com.example.uiwidgettest.GOFORITOUTLINE");
                sendBroadcast(intent);
            }
        }

        @Override
        public void Already() {
            MyLog.d("LoginActivity:","已经准备好了，可以向服务输出数据。");
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intact);
        currentaccount=getIntent().getStringExtra("account");
        touxiang=(ImageView)findViewById(R.id.IntactImage);
        online=(ImageView)findViewById(R.id.online);
        Intactlist=new ArrayList<>();
        Intactlist.clear();
//        StartService.weReceive=(ReceiveDatantentService.WeReceive) getIntent().getSerializableExtra("WeReceive");
//        MyLog.d(TAG,"从上一个活动传递的数据:"+weReceive);
        StartService.weReceive.SetDataListener(logindata);
        StartService.weReceive.SetCurrentActivity(INTACTACTIVITY);
        StartService.weReceive.WriteToService("请求联系人信息:"+currentaccount+":");
        recyclerView=(RecyclerView)findViewById(R.id.intactrecycle);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        setAdapter();
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
                MyLog.d("intactActivity:","传递之前的目标："+targetAccount);
//                intent.putExtra("TargetAccount",targetAccount);
                StartService.weReceive.SetTargetAccount(targetAccount);
                //应该不用改
//                intent.putExtra("Account",Currentaccount);
//                intent.putExtra("WeReceive",weReceive);
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
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    private void hanledatabyGSON(String data)
    {
        MyLog.d(TAG,data);
        Gson  gson=new Gson();
        List<IntactMessage> messages=
                gson.fromJson(data,new TypeToken<List<IntactMessage>>() {}.getType());
             for (IntactMessage message:messages)
             {
                 MyLog.d(TAG,"头文件："+message.getHeadfile()+" 呢称："+message.getName()+" 账号："
                         +message.getAccount()+" 状态:"+message.getStatus()+" online："+message.getOnline()+"\n");
            final Intact intact=new Intact(currentaccount,message.getAccount());
                 if(message.getOnline().equals("在线"))
                     intact.setOnline(R.drawable.online);
                 else
                     intact.setOnline(R.drawable.outline);
                 intact.setName(message.getName());
                 intact.setTime("12:00");
                 intact.setUnread(message.getUnread().equals("有"));
                 Intactlist.add(intact);
                 runOnUiThread(new Runnable() {
                     @Override
                     public void run() {
                         adapter.notifyItemChanged(Intactlist.indexOf(intact));
                     }
                 });

             }
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        MyLog.d("LoginActivity:","Restart重启活动");
        StartService.weReceive.SetCurrentActivity(INTACTACTIVITY);
        StartService.weReceive.SetDataListener(logindata);
    }
//   private void handledata(String content)
//    {
//        if(content.split(":")[0].equals("获取联系人"))
//        {
//
//            if(!content.split(":")[3].equals("空")) {
//                MyLog.d("intactvity:","从云端加载数据成功！");
//                Intact intact = new Intact(currentaccount, content.split(":")[1]);//账户
//                //当前帐户 和 目标账户 服务器获知的账户
//                intact.setName(content.split(":")[2]);//名字
//                Intactlist.add(intact);
//                intact.setUnread(false);//初始化必须接受的信息
//                if (content.split(":")[3].equals("最后一项")) {
//                    loadIntact();
//                }
//            }else
//            {
//                MyLog.d("intactvity:","无联系人！");
//                Intact intact=new Intact("","");
//                intact.setName("无联系人");
////                        intact.setOnline(0);
//                intact.setTime("12:00");
//                intact.setUnread(false);
//                Intactlist.add(intact);
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        setAdapter();
//                    }
//                });
//
//            }
//        }
//        else if(content.split(":")[0].equals("是否未读"))
//        {
//            final String unread=content.split(":")[1];
//            MyLog.i("intactActivity：",unread);
//            final String targetAccount=content.split(":")[2];
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//
//                    for (int i=0;i<Intactlist.size();i++) {
//                        Intact intact=Intactlist.get(i);
//                        MyLog.i("intactActivity：","(数组)当前："+targetAccount+"目标:"+intact.gettargetAccount());
//                        if(targetAccount.equals(intact.gettargetAccount())) {//目标等于目标 当前帐户是唯一的
//                            //两者的目标账户一致说明是同一个intact对象
//                            if (unread.equals("有")) {
//                                intact.setUnread(true);
//                                intact.setTime("12:00");
//                            } else {
//                                intact.setUnread(false);
//                                intact.setTime("12:00");
//                            }
//                            setAdapter();
//                            adapter.notifyItemChanged(i);
//
//                        }
//                    }
//
//                }
//            });
//
//
//        }
//        else if (content.split(":")[0].equals("账号状态"))
//        {
//            //发送本地广播强制下线
//            if(content.split(":")[1].equals("在线"))
//                sendBroad();
//        }
//        else if(content.split(":")[0].equals("状态"))
//        {
//            final String account=content.split(":")[1];
//            final  String status=content.split(":")[2];
//            runOnUiThread(new Runnable() {
//                @Override
//                public void run() {
//                    for (int i=0;i<Intactlist.size();i++) {
//                        Intact intact=Intactlist.get(i);
//                        MyLog.i("intactActivity:",status);
//                        if(account.equals(intact.gettargetAccount())) {//取得的账户必须和目标账户一致
//                            if (status.equals("在线")||account.equals(intact.getCurrentaccount()))
//                                intact.setOnline(R.drawable.online);
//                            else if (status.equals("不在线"))
//                                intact.setOnline(R.drawable.outline);
//                        }
//                        adapter.notifyItemChanged(i);
//                    }
//
//                }
//            }
//            );
//        }
//    }
    //    private  void loadIntact()
//    {
//        StartService.weReceive.WriteToService("账号信息:"+currentaccount+":");

//        StartService.weReceive.WriteToService("当前账号是否在线" +
//                ":");
//        for (Intact intact:Intactlist) {
//            //将所有联系人上传服务器判断
//
//            StartService.weReceive.WriteToService("是否未读:"+intact.getCurrentaccount()+":"+intact.gettargetAccount()+":");
//
//            if(intact.gettargetAccount()!=intact.getCurrentaccount())
//                StartService.weReceive.WriteToService("在线:"+intact.gettargetAccount()+":");
//            else
//                intact.setOnline(R.drawable.online);
//        }
//    }


}
