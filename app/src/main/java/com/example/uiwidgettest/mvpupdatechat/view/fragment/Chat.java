package com.example.uiwidgettest.mvpupdatechat.view.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.mvpupdatechat.bufferdata.Bufferdata;
import com.example.uiwidgettest.mvpupdatechat.database.ChatTranscriptHepler;
import com.example.uiwidgettest.mvpupdatechat.json.HandleJson;
import com.example.uiwidgettest.mvpupdatechat.presenter.chat.MedChatPresenter;
import com.example.uiwidgettest.mvpupdatechat.view.activity.CurrentData;
import com.example.uiwidgettest.mvpupdatechat.view.adapter.Message;
import com.example.uiwidgettest.mvpupdatechat.view.adapter.MessageAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.uiwidgettest.mvpupdatechat.presenter.PresenterContractChat.*;

/**
 * Created by 40774 on 2017/10/17.
 */

public class Chat extends Fragment implements chatviewcallback,SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.getmoreData)
    TextView getmoreData;
    @BindView(R.id.refresh)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.reclyer2)
    RecyclerView recyclerView;
    @BindView(R.id. input_text)
    EditText input;
    private final int ALLDATA=-1;
    private List<Message> messages;
    private MessageAdapter adapter;
    private ChatTranscriptHepler hepler;
    private SQLiteDatabase wdatabase;
    private SQLiteDatabase rdatabase;
   @OnClick(R.id.chatguibutton1)
   public void onClick()
   {
       String content=input.getText().toString();
       insertDatetoDB(content,false);
       Message message=new Message();
       message.setMessage(content);
       message.setRepient(true);
       messages.add(message);
       adapter.notifyItemInserted(messages.size()-1);
       recyclerView.smoothScrollToPosition(messages.size()-1);
       MedChatPresenter.getInstance().sendData(content);
       input.setText("");
       MyLog.d("Chat:",content);
   }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_chatgui,container,false);
        ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        messages=new ArrayList<>();
        adapter=new MessageAdapter(getContext(),messages);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        refreshLayout.setOnRefreshListener(this);
        MedChatPresenter.getInstance().setChatviewCallback(this);
        MedChatPresenter.getInstance().start();
    }

    @Override
    public void displayData(Message message) {
        messages.add(message);
        adapter.notifyItemInserted(messages.size()-1);
        recyclerView.smoothScrollToPosition(messages.size()-1);
    }

    @Override
    public void querydatabases(int acount) {
        messages.clear();
        Cursor cursor = rdatabase.query("chattranscript" + Bufferdata.getIntance().getCurrentLoginAccount(), null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String target = cursor.getString(cursor.getColumnIndex("target"));
                if (target.equals(Bufferdata.getTarget())) {
                    //如果当前目标等于数据库中的目标 则添加数据
                    String message = cursor.getString(cursor.getColumnIndex("message"));
                    boolean isrepient;
                    if (cursor.getInt(cursor.getColumnIndex("isrepient")) == 0)
                        isrepient = true;
                    else
                        isrepient = false;
                    Message message1 = new Message();
                    message1.setMessage(message);
                    message1.setRepient(isrepient);
                    messages.add(message1);
                    MyLog.d("Chat:", "target:" + target + " message:" + message);
                }
            } while (cursor.moveToNext());
            int size=messages.size();
            if(acount!=ALLDATA)
            for (int i=0;i<size-acount;i++)
            {
                //移除首项 移除acount次
                messages.remove(0);
            }
            adapter.notifyDataSetChanged();
            if(acount!=ALLDATA&&messages.size()>0)//如果不是查询全部就滚动到末尾
            recyclerView.smoothScrollToPosition(messages.size()-1);
            if(acount==ALLDATA)
            recyclerView.smoothScrollToPosition(0);
        }
    }

    @Override
    public void establishDatabase() {
         hepler=new ChatTranscriptHepler(getContext(),null,null,1);
         wdatabase=hepler.getWritableDatabase();
         rdatabase=hepler.getReadableDatabase();
         wdatabase.execSQL("create table if not exists chattranscript"+ Bufferdata.getIntance().getCurrentLoginAccount()
                 +"(id integer primary key autoincrement,target varchar(20),message text,isrepient boolean)");
    }

    @Override
    public void insertDatetoDB(String data,boolean isrepient) {
        ContentValues values=new ContentValues();
        values.put("message",data);
        values.put("target",Bufferdata.getTarget());
        values.put("isrepient",isrepient);
        wdatabase.insert("chattranscript"+Bufferdata.getIntance().getCurrentLoginAccount(),
        null,values);
    }

    @Override
    public void onRefresh() {
        getmoreData.setText("无更多的消息");
        refreshLayout.setRefreshing(true);
        querydatabases(ALLDATA);
        refreshLayout.setRefreshing(false);
    }
    @Override
    public void onResume() {
        //活动创建时，把碎片加入CurrentData 以方便其他类判断当前处于那个界面
        CurrentData.setCurrentfragment(this);
        super.onResume();
    }

}
