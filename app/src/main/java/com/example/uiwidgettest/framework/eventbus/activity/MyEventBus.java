package com.example.uiwidgettest.framework.eventbus.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.eventbus.fragment.Eventemuuu;
import com.example.uiwidgettest.framework.eventbus.fragment.Eventemuuuu;
import com.example.uiwidgettest.framework.eventbus.useclass.MessageEvent;
import com.example.uiwidgettest.framework.rxjava.adapter.ButtonandTextRecycle;
import com.example.uiwidgettest.framework.rxjava.useclass.RxBus;
import com.example.uiwidgettest.framework.rxjava.useinterface.CallBackButton;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class MyEventBus extends AppCompatActivity implements CallBackButton {
    private final String TAG="MyEventBus事情总线:";
    //重用了framework包中的适配器以及接口
    private TextView mdisplay;
    private List<String> name = new ArrayList<>();
    private StringBuilder mdata = new StringBuilder();
    private FrameLayout frameLayout;
    private Eventemuuuu eventemuuuu;
    private Eventemuuu eventemuuuu2;
    private RecyclerView recyclerView;
   private Disposable disposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_event_bus);
        disposable=RxBus.getInstance().toObservale(MessageEvent.class)
                .subscribe(new Consumer<MessageEvent>() {
                    @Override
                    public void accept(MessageEvent messageEvent) throws Exception {
                        Toast.makeText(getBaseContext(),"日你妹",Toast.LENGTH_SHORT).show();
                    }
                });
        initView();
        eventemuuuu=new Eventemuuuu();
        eventemuuuu2=new Eventemuuu();
    }

    private void initView() {
        mdisplay = (TextView) findViewById(R.id.display);
        recyclerView = (RecyclerView) findViewById(R.id.reclyer);
        frameLayout=(FrameLayout)findViewById(R.id.fragmentplace);
        name.add("黏性事件,简单的事件主线，注意点：碎片更改时不能反复注册（写在碎片最好）");
        name.add("用Rxjava实现RxBus代替EventBus和otto");
        recyclerView.setAdapter(new ButtonandTextRecycle(this, this, name));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onClick(View v, int position) {
        switch (v.getId()) {
            case R.id.implement:
                switch (position) {
                    case 0:
                        if(eventemuuuu.getView()==null)
                            addFragment(eventemuuuu);
                        else {
                            frameLayout.removeAllViews();
//                          恢复原来碎片的样子
                            frameLayout.addView(eventemuuuu.getView());//避免重复添加
                        }
                        break;
                    case 1:
                        if(eventemuuuu2.getView()==null)
                            addFragment(eventemuuuu2);
                        else {
                            frameLayout.removeAllViews();
//                          恢复原来碎片的样子
                            frameLayout.addView(eventemuuuu2.getView());//避免重复添加
                        }
                        break;
                }
                break;
        }
    }
   private void addFragment(Fragment fragment)
    {
        frameLayout.removeAllViews();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentplace,fragment);
        fragmentTransaction.commit();
    }
    private void replaceFragment(Fragment remove,Fragment fragment)
    {
        frameLayout.removeAllViews();
        FragmentTransaction fragmentTransaction=getSupportFragmentManager().beginTransaction();
        if(remove!=null)
        fragmentTransaction.remove(remove);
        fragmentTransaction.add(R.id.fragmentplace,fragment);
        fragmentTransaction.commit();
    }
    @Override
    protected void onDestroy() {
        disposable.dispose();
        super.onDestroy();
    }
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onhandle(MessageEvent event)
    {
        frameLayout.removeAllViews();
        frameLayout.addView(recyclerView);
    }
}
