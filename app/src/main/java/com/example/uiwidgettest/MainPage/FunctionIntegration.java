package com.example.uiwidgettest.MainPage;



import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.activity.fragment.ActivityFragment;
import com.example.uiwidgettest.broadcast.BroadcastIntegration;
import com.example.uiwidgettest.contentprovider.ChatTranscripts;
import com.example.uiwidgettest.contentprovider.ContentProviderIntegration;
import com.example.uiwidgettest.filesave.FileSaveIntegration;
import com.example.uiwidgettest.byinternet.InternetIntergration;
import com.example.uiwidgettest.byservice.ServiceIntergration;
import com.example.uiwidgettest.fragment.AnnotherFragment;
import com.example.uiwidgettest.fragment.FragmentIntergration;
import com.example.uiwidgettest.framework.eventbus.activity.MyEventBus;
import com.example.uiwidgettest.framework.mvp.activity.ViewTaobaoIP;
import com.example.uiwidgettest.framework.rxjava.activity.RxJava;
import com.example.uiwidgettest.materialdesign.MDintergration;
import com.example.uiwidgettest.mvpupdatechat.view.activity.MainPage;
import com.example.uiwidgettest.myreview.ButtonAndText;
import com.example.uiwidgettest.myreview.ButtonRecycleAdapter;
import com.example.uiwidgettest.myreview.OnClickListener;
import com.example.uiwidgettest.myreview.Review;
import com.example.uiwidgettest.thelight.thelightintergration;
import com.example.uiwidgettest.ui.UIintegration;
import com.example.uiwidgettest.mobilemultimedia.mobilemultimediaIntegration;
import com.example.uiwidgettest.usebaidumap.BaiduMapIntergration;
import com.example.uiwidgettest.view.activity.ViewActivity;

import java.util.ArrayList;
import java.util.List;

public class FunctionIntegration extends AppCompatActivity implements OnClickListener {
    private List<ButtonAndText> buttonAndTexts;
    private List<Fragment> add_fragments;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//初始化应用 一打开应用就初始化内容提供器 的数据库。
        CreatTable();
        setContentView(R.layout.activity_function_integration);
        RecyclerView recyclerView=(RecyclerView) findViewById(R.id.MainPageRecyclerView);
        buttonAndTexts=new ArrayList<>();
        add_fragments=new ArrayList<>();
        initButton();
        ButtonRecycleAdapter adapter=new ButtonRecycleAdapter(this,this,buttonAndTexts,add_fragments,getSupportFragmentManager());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


    }
    private void CreatTable() {
        ChatTranscripts transcripts = new ChatTranscripts(this, "mychat", null, 2);
        SQLiteDatabase writesqLite = transcripts.getWritableDatabase();
        SQLiteDatabase readsqLite = transcripts.getReadableDatabase();
        if (!readsqLite.query("ChatTranscripts", null, null, null, null, null, null).moveToNext()) {
            //没有数据则对数据库初始化
            ContentValues values = new ContentValues();
           for (int i=0;i<50;i++) {
               values.put("recipent", "陈宏林");
               values.put("account", "66666");
               values.put("addresser", "407748918");
               values.put("message", "日了狗");
               writesqLite.insert("chattranscripts", null, values);
               values.clear();
           }
           MyLog.d("Functiongration:","聊天数据初始化完成");
        }
    }
  private void initButton()
  {
      for (Code code:Code.values())
      {
          add_name(code.getTag());
          add_fragments.add(code.getFragment());
       }
  }
        private void add_name(String name)
        {
            ButtonAndText text=new ButtonAndText();
            text.setButtonName("传送门");
            text.setText(name);
            buttonAndTexts.add(text);
        }

        @Override
        public void OnClick(View v, int postion) {
            Intent intent=new Intent(FunctionIntegration.this,Code.getIndex(postion).getAClass());
            startActivity(intent);
        }

    @Override
        public void OnLongClick(View v, int position) {

        }
    private enum Code {
        ACTIVITY (null,  "活动界面",new ActivityFragment()),
        UI (UIintegration.class,"用户交互界面",null),
        FRAGMENT( FragmentIntergration.class,"碎片界面",null),
        BROADCAST(BroadcastIntegration.class,"广播组件",null),
        SQLITE(FileSaveIntegration.class, "SQLITE数据库面",null),
        CP(ContentProviderIntegration.class,"内容提供器组件",null),
        MEDIA(mobilemultimediaIntegration.class,"多媒体处理界面",null),
        INTERNET(   InternetIntergration.class,"网络处理界面",null),
        SERVICE( ServiceIntergration.class, "服务组件",null),
        BAIDUMAP(  BaiduMapIntergration.class,"使用百度地图",null),
        MATERIAL_DESIGN(  MDintergration.class, "Android5.0 MaterialDesign",null),
        LIGHT( thelightintergration.class, "书籍进化之光",null),
        REVIEW( Review.class, "复习",null),
        SIMPLE_MVP( ViewTaobaoIP.class, "简单MVP",null),
        BUS(  MyEventBus.class,     "事情总线",null),
        VIEW( ViewActivity.class,  "视图操作",null),
        PATTERN( RxJava.class,  "框架",null),
        NEWS( com.example.uiwidgettest.winddragonnews.activity.MainPage.class,   "新闻阅读",null),
        MVPCHAT( MainPage.class,   "MVP架构对聊天升级",null),
        GOOD_PATTERN( com.example.uiwidgettest.designpattern.activity.MainPage.class, "流行的设计模式",null);
        private Class aClass;
        private String tag;
      private Fragment fragment;
        private Code(Class aClass,String tag,  Fragment fragment)
        {
            this.aClass = aClass;
            this.tag = tag;
            this.fragment=fragment;;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public Class getAClass() {
            return aClass;
        }

        public String getTag() {
            return tag;
        }

        public static Code getIndex(int text) {
            for (Code code : values()) {
                if (code.ordinal() == text)
                    return code;
            }
            return null;
        }
    }
}
