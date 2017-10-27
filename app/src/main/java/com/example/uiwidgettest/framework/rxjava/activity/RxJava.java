package com.example.uiwidgettest.framework.rxjava.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.rxjava.adapter.ButtonandTextRecycle;
import com.example.uiwidgettest.framework.rxjava.useclass.ToUseRxJava;
import com.example.uiwidgettest.framework.rxjava.useinterface.CallBackButton;
import com.example.uiwidgettest.framework.rxjava.useinterface.DisplayString;
import com.example.uiwidgettest.myreview.ButtonRecycleAdapter;
import com.example.uiwidgettest.myreview.DisplayData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

public class RxJava extends AppCompatActivity implements CallBackButton,DisplayString{
   private TextView mdisplay;
    private List<String> name=new ArrayList<>();
    private StringBuilder mdata=new StringBuilder();
    ToUseRxJava rxJava=new ToUseRxJava(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx_java);
        initView();
    }
    private void initView()
    {
        mdisplay=(TextView) findViewById(R.id.display);
        RecyclerView recyclerView=(RecyclerView)findViewById(R.id.reclyer);
        name.add("完整写法添加事件到任务队列");
        name.add("just写法添加事件到任务队列");
        name.add("Consume部分回调");
        name.add("Consume处理错误，Action处理完成");
        name.add("创建符Interval（3秒）");
        name.add("用Rxjava以Okhttp访问网络");
        name.add("用Rxjava实现RxBus代替EventBus和otto");
        recyclerView.setAdapter(new ButtonandTextRecycle(this,this,name));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
    @Override
    public void onClick(View v,int position) {
        switch (v.getId())
        {
            case R.id.implement:
                switch (position)
                {
                    case 0:rxJava.useRx0();break;
                    case 1:rxJava.useRx1();break;
                    case 2:rxJava.useRx2();break;
                    case 3:rxJava.useRx3();break;
                    case 4:rxJava.useRx4();break;
                    case 5:rxJava.useRx5("39.108.123.220");break;
                }
                break;
        }
    }
    @Override
    public void display(String data) {
        mdata.append(data+"\n");
        mdisplay.setText(mdata.toString());
    }
}
