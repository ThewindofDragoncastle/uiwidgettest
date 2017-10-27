package com.example.uiwidgettest.framework.eventbus.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.uiwidgettest.MyLog;
import com.example.uiwidgettest.R;
import com.example.uiwidgettest.framework.eventbus.useclass.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by 40774 on 2017/10/11.
 */

public class Eventemuuuu extends Fragment implements View.OnClickListener {
    private final String TAG="Eventemuuu碎片:";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=LayoutInflater.from(getContext()).inflate(R.layout.simpleevent,container,false);
        Button register=(Button)view.findViewById(R.id.register);
        register.setOnClickListener(this);
        Button unregister=(Button)view.findViewById(R.id.unregister);
        unregister.setOnClickListener(this);
        Button post=(Button)view.findViewById(R.id.post);
        post.setOnClickListener(this);
        Button poststicky=(Button)view.findViewById(R.id.poststicky);
        poststicky.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        MessageEvent event=new MessageEvent();
        switch (v.getId())
        {
            case R.id.register:
                Registered();
                break;
            case R.id.unregister:
                unRegistered();
                Toast.makeText(getContext(),"取消成功。",Toast.LENGTH_SHORT).show();
                break;
            case R.id.post:
                event.setView(this.getView());
                EventBus.getDefault().post(event);
                break;
            case R.id.poststicky:
                event.setView(this.getView());
                EventBus.getDefault().postSticky(event);
                break;
        }
    }
    private void Registered()
  {
    if(!EventBus.getDefault().isRegistered(getActivity()))
    {
        MyLog.d(TAG,"注册");
        EventBus.getDefault().register(getActivity());
    }
    else
        Toast.makeText(getContext(),"你已经注册。",Toast.LENGTH_SHORT).show();
  }
    private void unRegistered()
    {
        if(EventBus.getDefault().isRegistered(getActivity()))
        {
            MyLog.d(TAG,"取消注册");
            EventBus.getDefault().unregister(getActivity());
        }
    }
    @Override
    public void onStop() {
        unRegistered();
        super.onStop();
    }
}
