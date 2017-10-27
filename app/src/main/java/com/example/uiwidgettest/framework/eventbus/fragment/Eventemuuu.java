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
import com.example.uiwidgettest.framework.rxjava.useclass.RxBus;
import com.example.uiwidgettest.framework.rxjava.useclass.ToUseRxJava;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by 40774 on 2017/10/11.
 */

public class Eventemuuu extends Fragment implements View.OnClickListener {
    //Rxbus
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        MessageEvent event=new MessageEvent();
        switch (v.getId())
        {
            case R.id.register:
                break;
            case R.id.unregister:
                break;
            case R.id.post:
                RxBus.getInstance().post(new MessageEvent());
                break;
            case R.id.poststicky:
                break;
        }
    }
}
