package com.example.uiwidgettest.myreview;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.broadcast.SendNotifaciation;
import com.example.uiwidgettest.myreview.contentprovide.DispalyCP;
import com.example.uiwidgettest.myreview.contentprovide.HeroDatabase;
import com.example.uiwidgettest.myreview.contentprovide.SQLhelper;
import com.example.uiwidgettest.myreview.json.Hero;
import com.example.uiwidgettest.myreview.json.JsontoString;
import com.example.uiwidgettest.myreview.json.StringtoJson;
import com.example.uiwidgettest.myreview.mediaplayer.view.activity.BufferMediaplayer;
import com.example.uiwidgettest.myreview.service.PlayService;
import com.example.uiwidgettest.myreview.textbook.activity.Textbook_MainPage;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class Review extends AppCompatActivity implements OnClickListener {
private RecyclerView recyclerView;
    private ButtonRecycleAdapter recycleAdapter;
    private DIYBroadcast diyBroadcast;
    private IntentFilter intentFilter;
    //放置按钮以及文本
    private List<ButtonAndText> buttonAndTexts=new ArrayList<>();
    private LocalBroadcastManager localBroadcastManager;
    //数据库
    private HeroDatabase heroDatabase;
    private SQLiteDatabase databaseR;
    private SQLhelper sqLhelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         initView();
        Requestpermission();
        intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.uiwidgettest.SENDNOTIFICATION");
        localBroadcastManager=LocalBroadcastManager.getInstance(this);
    }
    private void initView()
    {
        setContentView(R.layout.activity_review);
        recyclerView=(RecyclerView)findViewById(R.id.buttonrecyclerview) ;
        initButtonAndTexts();
        recycleAdapter=new ButtonRecycleAdapter(this,this,buttonAndTexts,null,null);
        recyclerView.setAdapter(recycleAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        diyBroadcast=new DIYBroadcast();
        heroDatabase=new HeroDatabase(this,"reviewhero", null,1);
        databaseR=heroDatabase.getReadableDatabase();
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
        addname("获取访问SD卡权限，获取缓存的歌曲或视频信息，加载到屏幕中。用户可直接查找整个SD卡或" +
                "指定目录的MP3、MP4格式的文件，并且分类列出。歌曲电影列表点击播放实现后台服务自动播放，" +
                "并且以静态广播的形式对用户在通知界面的点击做出反应。后台服务可以自动循环播放，播放下一首" +
                "歌曲。");
        addname("网络播放视频");
        addname("table———layout布局方式，日历等等");
        addname("~~~~~~~~~");
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
        ButtonAndText text=new ButtonAndText();
        text.setButtonName("传送门");
        text.setText(introduce);
        buttonAndTexts.add(text);
    }
    private boolean Requestpermission()
    {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            if(Build.VERSION.SDK_INT>=23)
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            return false;
        }
        else
            return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        switch (requestCode)
        {
            case 1:
                if(grantResults.length>0&&grantResults[0]!=PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "拒绝权限将无法使用此功能。", Toast.LENGTH_SHORT).show();
                   finish();
                }
                break;
        }
    }

    @Override
    public void OnClick(View v, int postion) {
        Intent intent;
        JSONArray array;
        ButtonText text=ButtonText.getIndex(postion);
        switch (text)
        {
            case ReturnActivityForResult:
                //给下一个网页活动传递数据 并规定能响应的网页
                intent=new Intent("com.example.uiwidgettest.ACTION_MYWEBVIEW");
                intent.addCategory("android.intent.category.ACTION_MYWEBVIEW");
                intent.setData(Uri.parse("http://www.baidu.com"));
                startActivityForResult(intent,1);
                break;
            case  Broadcast:
                intent=new Intent("com.example.uiwidgettest.SENDNOTIFICATION");
                        localBroadcastManager.sendBroadcast(intent);
                break;
            case  FileSave:
                break;
            case  StringToJson:
                //打开数组转换类
                array=new StringtoJson().Listcat(initArray());
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("stringtojson",array.toString());
                intent.putExtra("display",true);
                startActivity(intent);
                break;
            case  JsonToString:
                array=new StringtoJson().Listcat(initArray());
                JsontoString json=new JsontoString();
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("display",true);
                intent.putExtra("stringtojson",json.toJson(array.toString()));
                startActivity(intent);
                break;
            case  Database:
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("display",false);
                startActivity(intent);
                break;
            case  CP:
                intent=new Intent(this,DispalyCP.class);
                startActivity(intent);
                break;
            case  TakePhoto:
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("display",false);
                intent.putExtra("photo",true);
                startActivity(intent);
                break;
            case  PlayMusic:
                intent=new Intent(this,PlayService.class);
                startService(intent);
                intent=new Intent(this,DisplayData.class);
                intent.putExtra("display",false);
                intent.putExtra("moviesong",true);
                startActivity(intent);
                break;
            case  PlayVideo:
                intent=new Intent(this,BufferMediaplayer.class);
                startActivity(intent);
                break;
            case  TableLayout:
                intent=new Intent(this,Textbook_MainPage.class);
                startActivity(intent);
                break;
            case  Default:
                break;
        }
    }
   public enum ButtonText
   {
       ReturnActivityForResult, Broadcast,FileSave,StringToJson
       ,JsonToString,Database,CP,TakePhoto,PlayMusic,PlayVideo,TableLayout,Default;
       public static ButtonText getIndex(int text)
       {
         for(ButtonText text1:values())
         {
             if (text1.ordinal()==text)
                 return text1;
         }
         return null;
       }
   }
}
