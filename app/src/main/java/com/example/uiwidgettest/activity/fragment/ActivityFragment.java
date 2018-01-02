package com.example.uiwidgettest.activity.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.uiwidgettest.R;
import com.example.uiwidgettest.myreview.ButtonAndText;
import com.example.uiwidgettest.myreview.ButtonRecycleAdapter;
import com.example.uiwidgettest.myreview.OnClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 40774 on 2018/1/1.
 */

public class ActivityFragment extends Fragment implements OnClickListener{
    RecyclerView recyclerView;
    private List<ButtonAndText> textList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.first_layout,container,false);
       recyclerView=(RecyclerView) view.findViewById(R.id.activity_recycler_view);
       return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        textList=new ArrayList<>();
        init();
        recyclerView.setAdapter(new ButtonRecycleAdapter(getContext(),this,textList,null,null));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
private void init()
{
    for (Button button:Button.values()) {
        ButtonAndText text = new ButtonAndText();
        text.setText("\3000\3000"+button.getName());
        text.setButtonName("传送门");
        textList.add(text);
    }
}
    @Override
    public void OnClick(View v, int position) {

    }

    @Override
    public void OnLongClick(View v, int position) {

    }
 private    enum Button
    {
        A1("显式intent进入活动二"),

        A2("隐式intent进入活动二"),

        A3("向活动二传递数据"),

        A4("开启活动二而且写入请求码"),

        A5("standard模式前往本活动"),

        A6("前往活动四singletop"),

        A7("前往活动五singletask"),

        A8("前往活动六singleinstance"),

        A9("退出");
        String Name;
        private Button(String s)
        {
            Name=s;
        }

        public String getName() {
            return Name;
        }
    }
}
