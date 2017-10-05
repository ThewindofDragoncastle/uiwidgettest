package com.example.uiwidgettest.myreview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.broadcast.SendNotifaciation;
import com.example.uiwidgettest.myreview.contentprovide.DispalyCP;
import com.example.uiwidgettest.myreview.contentprovide.GetDataFromCP;
import com.example.uiwidgettest.myreview.contentprovide.HeroDatabase;
import com.example.uiwidgettest.myreview.contentprovide.SQLhelper;
import com.example.uiwidgettest.myreview.json.Hero;
import com.example.uiwidgettest.myreview.json.JsontoString;
import com.example.uiwidgettest.myreview.json.StringtoJson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Review extends AppCompatActivity implements OnClickListener {
private RecyclerView recyclerView;
    private ButtonRecycleAdapter recycleAdapter;
    private   int i=0;
    private DIYBroadcast diyBroadcast;
    private IntentFilter intentFilter;
    //放置按钮以及文本
    private List<ButtonAndText> buttonAndTexts=new ArrayList<>();
    private LocalBroadcastManager localBroadcastManager;
    //数据库
    private HeroDatabase heroDatabase;
    private SQLiteDatabase databaseR;
    private SQLiteDatabase databaseW;
    private SQLhelper sqLhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       initView();

        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.uiwidgettest.SENDNOTIFICATION");
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
    }
    private void initView()
    {
        setContentView(R.layout.activity_review);
        recyclerView=(RecyclerView)findViewById(R.id.buttonrecyclerview) ;
        initButtonAndTexts();
        recycleAdapter=new ButtonRecycleAdapter(this,this,buttonAndTexts);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diyBroadcast=new DIYBroadcast();
        heroDatabase=new HeroDatabase(this,"reviewhero", null,1);
        databaseR=heroDatabase.getReadableDatabase();
        databaseW=heroDatabase.getWritableDatabase();
        sqLhelper=new SQLhelper();
    }
    private void initButtonAndTexts()
    {
        addname("启动能响应ACTION_MYWEBVIEW的活动,当按下两次退出键，活动被销毁且被销毁活动返回结果 结果：");
        addname("连接电源发送通知（已静态注册），点击按钮本地广播发送通知");
        addname("数据持久化--文件存储 文件名：赠别\n 内容：娉娉袅袅十三馀...");
        addname("String串数组转化为JSON数组");
        addname("JSON数组转换为字符串");
        addname("建立数据库，升级数据库，查询数据库，可删除修改增添");
        addname("查看内容提供器的所有接口，以及说明");
        addname("拍摄照片或者选择照片");
    }

    @Override
    public void OnLongClick(View v, int postion) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //销毁活动时返回请求
     switch (requestCode)
     {
         case 1:
             if(resultCode==1)
             {
                 buttonAndTexts.get(0).setText("启动能响应ACTION_MYWEBVIEW的活动,当按下两次退出键，活动被销毁且被销毁活动返回结果 结果："+
                         data.getStringExtra("result"));
                 recycleAdapter.notifyDataSetChanged();

             }
             break;
     }
    }
//动态广播
    class DIYBroadcast extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        SendNotifaciation sendNotifaciation=new SendNotifaciation();
        sendNotifaciation.send(context,"动态本地广播","哈哈哈哈哈哈,加油啦");
    }
}

    @Override
    protected void onResume() {
        super.onResume();
        MyLog.d("Review:","注册广播");
        localBroadcastManager.registerReceiver(diyBroadcast,intentFilter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyLog.d("Review:","关闭广播");
        localBroadcastManager.unregisterReceiver(diyBroadcast);
    }
    private List<Hero> initArray()
    {
        return sqLhelper.getListHero(databaseR);
    }
    private void addname(String introduce)
    {
        ButtonAndText text=new ButtonAndText(i);
        i++;
        text.setButtonName("传送门");
        text.setText(introduce);
        buttonAndTexts.add(text);
    }

    @Override
    public void OnClick(View v, int postion) {
        Intent intent;
        JSONArray array;
        switch (buttonAndTexts.get(postion).getCODE())
        {
            case  0:
                //给下一个网页活动传递数据 并规定能响应的网页
                intent=new Intent("com.example.uiwidgettest.ACTION_MYWEBVIEW");
                intent.addCategory("android.intent.category.ACTION_MYWEBVIEW");
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivityForResult(intent,1);
                break;
            case  1:
                intent=new Intent("com.example.uiwidgettest.SENDNOTIFICATION");
                        localBroadcastManager.sendBroadcast(intent);
                break;
            case  2:
                break;
            case  3:
                //打开数组转换类
                array=new StringtoJson().Listcat(initArray());
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("stringtojson",array.toString());
                intent.putExtra("display",true);
                startActivity(intent);
                break;
            case  4:
                array=new StringtoJson().Listcat(initArray());
                JsontoString json=new JsontoString();
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("display",true);
                intent.putExtra("stringtojson",json.toJson(array.toString()));
                startActivity(intent);
                break;
            case  5:
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("display",false);
                startActivity(intent);
                break;
            case  6:
                intent=new Intent(this,DispalyCP.class);
                startActivity(intent);
                break;
        }
    }
}
